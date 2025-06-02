import java.awt.event.ActionListener;
import javax.swing.*;

public class ClientMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. 프레임 생성
            MainFrame frame = new MainFrame();

            // 2. 플레이어 생성 (로그인 후 대체 예정)
            Player player = new Player("닉네임임시");

            // 3. 통신 매니저 생성 및 플레이어 연결
            SocketManager socketManager = new SocketManager();
            socketManager.setPlayer(player);

            // 5. 서버 연결
            boolean connected = socketManager.connectToServer("127.0.0.1", 12345);
            if (connected) {
                socketManager.sendMessage("COMMAND:LOGIN:" + player.getNickname());
                socketManager.startReceiver();
            } else {
                System.out.println("서버 연결 실패");
            }

            // 6. 메인 화면 (게임 시작 버튼 화면)
            MainPanel mainPanel = new MainPanel(e -> {
                frame.showPanel("choice");
            });
            frame.addPanel("main", mainPanel);

            // 7. 퀴즈 화면 등록
            QuizPanel quizPanel = new QuizPanel(new ActionListener[]{
                    e -> socketManager.sendMessage("COMMAND:ANSWER:0"),
                    e -> socketManager.sendMessage("COMMAND:ANSWER:1"),
                    e -> socketManager.sendMessage("COMMAND:ANSWER:2"),
                    e -> socketManager.sendMessage("COMMAND:ANSWER:3"),
            });
            quizPanel.setPlayer(player);
            socketManager.setQuizPanel(quizPanel);
            frame.addPanel("quiz", quizPanel);

            // 8. 싱글/멀티 선택 화면
            ChoicePanel choicePanel = new ChoicePanel(
                    e -> {
                        System.out.println("싱글 모드 선택됨");
                        frame.showPanel("quiz"); //  싱글 버튼 누르면 퀴즈 화면으로 전환
                    },
                    e -> {
                        System.out.println("멀티 모드 선택됨");
                        // TODO: 로그인 또는 대기방으로 이동
                    }
            );
            frame.addPanel("choice", choicePanel);
            // 9. 초기 화면 출력
            frame.setVisible(true);
            frame.showPanel("main");
        });
    }
}
