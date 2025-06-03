import java.io.*;
import java.net.*;

public class ClientHandler extends Thread{
    private Socket incoming; // 클라이언트 소켓.
    private int counter; // 클라이언트 구분용 변수.
    private PrintWriter out;
    // 외부로 정보를 보낼 변수.
    private BufferedReader in;
    private Player player; // player 정보 저장.
    //
    String message; // 외부의 정보를 저장할 변수.
    String[] parts; // 외부의 정보를 분할하여 담아둘 배열.
    String sendMessage; //외부로 보낼 정보를 저장할 변수.


    public ClientHandler(Socket i, int c){
        this.incoming = i;
        this.counter = c;
        try {
            this.out = new PrintWriter(incoming.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
        } catch (IOException e) {
            System.out.println("스트림 초기화 실패");
        }
    }

    public PrintWriter getOut() {
        // 정보를 보내기 위한 메서드.
        return out;
    }

    public void run(){
        try{
            while(true){
                message = in.readLine(); // 입력값 받기
                parts = message.split(":", 3); // :을 기준으로 입력값 등분
                if (parts.length < 3) {
                    getOut().println("ERROR: Invalid message format.");
                    continue;
                }
                String mainCommand = parts[1];  //구분용 명령.
                String subCommand = parts[2]; // 구체적 명령.

                switch (mainCommand){
                    // 메인화면, 로그인화면, 선택화면은 클라이언트가 알아서 실행하도록 함.
                    case "SINGLE":
                        // 개인 코인만 서버에서 받고 나머지는 클라이언트가 함.
                        break;

                    case "MULTI":
                        // 생성된 대기방들 표시, 상점아이콘, 랭킹아이콘,
                        // 유저들이 생성한 모든 방을 불러와서 클라이언트에게 전달.
                        // 상점, 랭킹 아이콘은 클라이언트가 알아서.
                        break;

                    case "SHOP": // 상점 클릭시 이동
                        //상점 최신 업데이트
                        break;

                    case "RANK": // 랭크 클릭시 이동
                        // 랭킹 최신 업데이트
                        break;

                    case "ROOM":
                        handleRoom(subCommand);
                        break;
                    default:
                        break;
                }
                getOut().println(sendMessage);
                if (command.equals("EXIT")) {
                    getOut().println("BYE");
                    break;
                }
            }
            incoming.close();
        } catch (Exception e){
            getOut().println("클라이언트 연결 실패. 재접속해주세요.");
        }
    }

    // 대기방 관련 명령 처리 메서드.
    void handleRoom(String subCommand) {
        if (subCommand.startsWith("LEAVE")){ // 나갈시 대기방 최신정보 얻어야 함.
            RoomManager.leaveRoom(player);
            sendMessage = RoomManager.updateRoomListForAllClients();
            getOut().println(sendMessage);
            return;
        }
        if (subCommand.startsWith("CREATE")){
            parts = subCommand.split(":",2);
            RoomManager.createRoom(player, Integer.parseInt(parts[1]));
            getOut().println("ROOM:CREATED:" + player.getRoomId());
            return;
        }
        if (subCommand.startsWith("JOIN")){
            parts = subCommand.split(":",2);
            RoomManager.joinRoom(player, parts[1]);
            return;
        }
        if (subCommand.startsWith("READY")){
            parts = subCommand.split(":",2);
            RoomManager.setReady(player, parts[1], true);
            return;
        }
        if (subCommand.startsWith("UNREADY")) {
            parts = subCommand.split(":", 2);
            RoomManager.setReady(player, parts[1], false);
            return;
        }
        if (subCommand.startsWith("REFRESH")) {
            RoomManager.RoomListString(); // 최신화
            sendMessage = RoomManager.getRoomList(); // 캐시에서 꺼내옴
            getOut().println(sendMessage);
        }
    }
}