import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class RoomManager {
    // 방 ID를 키로 방 객체 저장
    private static Map<String, Room> rooms = new HashMap<>();
    // 대기방 목록을 담아두는 변수.
    private static String cachedRoomList = "";

    // 방 목록을 문자열로 반환
    
    // 대기방 목록을 업데이트 하고 반환하는 메서드
    public static String RoomListString() {
        StringBuilder sb = new StringBuilder("ROOM:LIST:");
        for (Room room : rooms.values()) {
            sb.append(room.getRoomId()).append("(").append(room.getCurrentPlayerCount()).append("/").append(room.getMaxPlayers()).append(")").append(";");
        }
        cachedRoomList = sb.toString();
        return cachedRoomList;
    }



    // 방 생성 메서드 (client -> Player 객체, data -> 방 최대 인원수)
    public static synchronized void createRoom(Player client, int maxPlayers) {
        // 고유한 방 ID 생성 (UUID 사용)
        String roomId = UUID.randomUUID().toString();
        client.setRoomId(roomId); // 유저에 방 정보 저장.
        Room room = new Room(roomId, maxPlayers);
        room.addPlayer(client);
        rooms.put(roomId, room);

        // 클라이언트에게 방 생성 완료 및 방 정보 전달 (여기선 간단히 출력 예시)
        client.getHandler().getOut.println("ROOM:CREATED:");
        updateRoomListForAllClients();
    }

    // 방 참가 메서드 (data -> roomId)
    public static synchronized String joinRoom(Player client, String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            return "ROOM:ERROR:Room does not exist";
        }
        if (room.isFull()) {
            return "ROOM:ERROR:Room is full";
        }
        room.addPlayer(client);
        updateRoomInfo(room);
        return "ROOM:JOINED:" + roomId;
    }

    // 방 나가기 메서드
    public static synchronized String leaveRoom(Player client) {
        Room room = rooms.get(client.roomId);
        room.removePlayer(client);
        if (room.isEmpty()) {
            rooms.remove(roomId);  // 빈 방은 삭제
            updateRoomListForAllClients();
        } else {
            updateRoomInfo(room);
        }
        return "ROOM:LEFT:" + roomId;

    }

    // 준비 상태 변경 (ready = true/false)
    public static synchronized void setReady(Player client, String roomId, boolean ready) {
        Room room = rooms.get(roomId); // ID를 통해 생성된 방 주소를 호출.
        if (room == null) {
            client.getHandler().getOut().println("ROOM:ERROR:Room does not exist");
            return ;
        }

        room.setPlayerReady(client, ready); // 유저의 value 상태를 바꾼다.
        updateRoomInfo(room); //모든 유저의 상태를 대기방 접속자들에게 알림.

        // 모든 유저가 준비됐는지 확인 //////////////////////////////////
        if (room.allReady()) {
            client.getHandler().getOut().println("All players ready in room " + roomId + ". Starting game in 5 seconds...");


            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(() -> {
                for (Player p : room.getPlayers()) {
                    p.getHandler().out.println("GAME_START");
                }

                room.quizManager.startQuiz();
                scheduler.shutdown();
            }, 5, TimeUnit.SECONDS);
        }
    }

    // 대기방 목록을 보는 클라이언트에 갱신해줌
    private static String updateRoomListForAllClients() {
        StringBuilder sb = new StringBuilder("ROOM:LIST:");
        for (Room room : rooms.values()) { // 존재하는 모든 room의 정보를 순차적으로 sb에 넣어서 저장.
            sb.append(room.getRoomId()).append(",").append(room.getCurrentPlayerCount()).append(",").append(room.getMaxPlayers()).append(";");
        }
        return sb.toString();
    }

    // 특정 방의 정보를 대기방 참가자들에게 갱신
    private static void updateRoomInfo(Room room) {
        StringBuilder sb = new StringBuilder("ROOM:INFO:");
        sb.append(room.getRoomId()).append(":");
        for (Player p : room.getPlayers()) {
            sb.append(p.getUsername()).append(",").append(room.isReady(p)).append(";");
        }
        String message = sb.toString();
        room.broadcast(message);
    }

    // 게임 시작 처리
    private static void startGame(Room room) {
        // 게임 시작 신호 전송, 게임 로직 연결 등
        room.broadcast("GAME:START");
        // QuizManager 연동 등 필요한 코드 작성
    }

}