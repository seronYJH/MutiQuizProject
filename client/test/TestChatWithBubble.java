package client.test;

import client.gui.ChatPanel;

import javax.swing.*;

public class TestChatWithBubble {
    public static void main(String[] args) {
        JFrame frame = new JFrame("채팅창 감성 버전");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // 예시: 상대방 닉네임, 접속중 여부, 커플 여부
        ChatPanel chatPanel = new ChatPanel("맹구", true, true);
        frame.add(chatPanel);

        frame.setVisible(true);

        // 테스트용 메시지
        chatPanel.addMessage("하이 맹구~", true);
        chatPanel.addMessage("안녕 유빈아", false);
    }
}
