package client.test;

import client.gui.MainFrame;
import client.gui.WaitingRoomPanel;

import javax.swing.*;

public class TestWaitingRoom {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame testFrame = new JFrame("대기실 테스트");
            testFrame.setSize(800, 600);
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setLocationRelativeTo(null);

            WaitingRoomPanel waitingRoomPanel = new WaitingRoomPanel();
            waitingRoomPanel.setParentFrame(testFrame); // ❗필수: parentFrame 설정

            testFrame.add(waitingRoomPanel);
            testFrame.setVisible(true);
        });
    }
}
