import client.gui.UserInfoPanel;

import javax.swing.*;

public class TestUserInfoPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("유저 정보");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // 예: 타인 정보
        UserInfoPanel panel = new UserInfoPanel("맹구", 1430, 72, "빈이", false);

        panel.setCoupleButtonListener(e -> JOptionPane.showMessageDialog(null, "커플 신청/해제 클릭"));
        panel.setChatButtonListener(e -> JOptionPane.showMessageDialog(null, "채팅창 열기"));
        panel.setBackButtonListener(e -> System.out.println("뒤로 가기"));

        frame.add(panel);
        frame.setVisible(true);
    }
}
