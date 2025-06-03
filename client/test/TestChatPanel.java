package client.test;

import client.gui.ChatPanel;

import javax.swing.*;

public class TestChatPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("채팅창 테스트");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        ChatPanel panel = new ChatPanel("맹구", true, true);  // ✔️ 고쳐줌
        frame.add(panel);

        frame.setVisible(true);

        panel.addMessage("하이~!", true);
        panel.addMessage("안녕 유빈아", false);
    }
}
