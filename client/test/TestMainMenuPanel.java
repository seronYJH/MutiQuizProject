package client.test;

import client.gui.MainMenuPanel;

import javax.swing.*;
import java.util.Arrays;

public class TestMainMenuPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("메인 메뉴");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        // 테스트용 콜백 (콘솔 출력만)
        Runnable goToMulti = () -> System.out.println("🌐 멀티모드 이동 테스트");
        Runnable goToCoop = () -> System.out.println("🤝 협동모드 이동 테스트");

        MainMenuPanel panel = new MainMenuPanel(
                "유빈", 1200, 37,
                Arrays.asList("맹구: 밥 먹었어?", "준석: 밤에 ㄱ?", "AI봇: 널 이겨보고 싶어."),
                goToMulti, goToCoop
        );

        frame.add(panel);
        frame.setVisible(true);
    }
}
