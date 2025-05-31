package client.test;

import javax.swing.*;
import client.gui.QuizGamePanel;

public class TestQuizGamePanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("퀴즈 게임 테스트");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            frame.add(new QuizGamePanel("유빈")); // 현재 로그인 유저 닉네임
            frame.setVisible(true);
        });
    }
}
