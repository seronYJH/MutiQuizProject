package server;

public class RoomManager {
    private static RoomManager instance;

    // 싱글톤 인스턴스 반환
    public static RoomManager getInstance() {
        if (instance == null) {
            instance = new RoomManager();
        }
        return instance;
    }

    // 생성자 (private으로 막음)
    private RoomManager() {
        // 초기화 로직이 있으면 여기 작성
    }

    // 예시 메서드 (나중에 추가하세요)
    public void test() {
        System.out.println("RoomManager 동작 확인됨!");
    }
}
