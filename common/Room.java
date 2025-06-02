import java.util.*;

public class Room {
    private String roomId; //방 번호
    private int maxPlayers; // 최대 참가자 수
    private Map<Player, Boolean> playersReady = new HashMap<>();
    private QuizManager quizManager;
    private String roomName;
    private String category;

    public Room(String roomId, int maxPlayers) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.category = category;
        this.maxPlayers = maxPlayers;
        this.quizManager = new QuizManager();
    }

    public void addPlayer(Player player) {
        playersReady.put(player, false); // key: player, value: false를 하나 추가한다.
    }

    public void removePlayer(Player player) {
        playersReady.remove(player);
        broadcast("NOTICE:" + player.getUsername() + "님이 방을 나갔습니다.");
    }

    public boolean isFull() { // 클릭시 방이 가득차면 false 반환.
        return playersReady.size() >= maxPlayers;
    }

    public boolean isEmpty() {
        return playersReady.isEmpty(); // 아무도 없을 경우 true 반환.
    }

    public int getCurrentPlayerCount() { // 몇명 접속중인지 확인
        return playersReady.size();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getCategory() {
        return category;
    }

    public Set<Player> getPlayers() {
        return playersReady.keySet();
    }

    public void setPlayerReady(Player player, boolean ready) {
        playersReady.put(player, ready); // player에 해당하는 value를 ready로 바꾼다.
    }

    public boolean isReady(Player player) {
        return playersReady.getOrDefault(player, false);
    }

    public synchronized boolean allReady() {
        for (Boolean ready : playersReady.values()) {
            if (!ready) return false;
        }
        return !playersReady.isEmpty();
    }

    public void broadcast(String message) {
        for (Player player : playersReady.keySet()) {
            player.getHandler().getOut().println(message);
        }
    }
}