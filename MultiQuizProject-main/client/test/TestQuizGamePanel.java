package client.test;
import client.test.SocketManager;
import client.gui.QuizGamePanel;

import javax.swing.*;

public class TestQuizGamePanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. 소켓 매니저 생성 및 서버 연결
            SocketManager socketManager = new SocketManager();
            boolean connected = socketManager.connectToServer("127.0.0.1", 12345);
            if (!connected) {
                System.out.println("서버 연결 실패");
                return;
            }

            // 2. 퀴즈 패널 생성 및 소켓 매니저 연결
            QuizGamePanel quizPanel = new QuizGamePanel("테스트유저");
            quizPanel.setSocketManager(socketManager);

            // 3. 소켓 매니저가 퀴즈 패널에게 문제 전달 가능하도록 설정
            socketManager.setQuizPanel(quizPanel);
            socketManager.startReceiver();  // 꼭 있어야 서버 응답 받음

            // 4. 화면 구성
            JFrame frame = new JFrame("퀴즈 테스트");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            frame.add(quizPanel);
            frame.setVisible(true);
        });
    }
}
