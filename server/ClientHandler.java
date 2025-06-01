import java.io.*;
import java.net.*;

public class ClientHandler extends Thread{
    private Socket incoming; // 클라이언트 소켓.
    private int counter; // 클라이언트 구분용 변수.
    private GameManager gameManager;
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
        this.gameManager = GameManager.getInstance(); // 객체 참조
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
            getOut().println("COMMAND:MAINPANEL:메인화면");
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

                    case "ROOM": // 방 생성,참가,나가기,시작시 이동
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
}