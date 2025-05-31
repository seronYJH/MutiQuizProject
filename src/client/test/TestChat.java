package client.test;

import client.gui.ChatPanelOri;

import javax.swing.*;

public class TestChat {
    public static void main(String[] args) {
        JFrame frame = new JFrame("공용 채팅창");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        ChatPanelOri panel = new ChatPanelOri("공용 채팅", true, false);
        frame.add(panel);
        frame.setVisible(true);

        panel.addMessage("나: 준비완료 했어!", true);
        panel.addMessage("상대: ㄱㄱ", false);
    }
}
