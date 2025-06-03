package client.gui;

import javax.swing.*;

public class ChatFrame extends JFrame {
    public ChatFrame(String partnerNickname, boolean isSender, boolean isCouple) {
        setTitle("💬 " + partnerNickname + " 님과의 채팅");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ChatPanel chatPanel = new ChatPanel(partnerNickname, isSender, isCouple);
        add(chatPanel);
    }
}
