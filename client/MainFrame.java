package client;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private SocketManager socketManager;

    public MainFrame() {
        // 기본 세팅
        setTitle("게임 클라이언트");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        socketManager = new SocketManager();

        // 메시지 수신 시 처리
        socketManager.setMessageListener(message -> {
            SwingUtilities.invokeLater(() -> {
                if (message.startsWith("ROOM:LIST:")) {

                    RoomListPanel listPanel = new RoomListPanel(new RoomListPanel.RoomSelectListener() {
                        public void onRoomSelected(String roomId) {
                            socketManager.sendMessage("COMMAND:ROOM:JOIN:" + roomId);
                        }

                        public void onCreateRoom(String name, String cat, int max) {
                            socketManager.sendMessage("COMMAND:CREATE_ROOM:" + name + ":" + cat + ":" + max);
                        }
                    });
                    listPanel.updateRoomListFromString(message.substring("ROOM:LIST:".length()));
                    setPanel("ROOM_LIST", listPanel);
                } else if (message.startsWith("ROOM:INFO:")) {
                    String[] parts = message.split(":", 3);
                    String RoomID = parts[2];
                    RoomWaitPanel waitPanel = new RoomWaitPanel(RoomID, new RoomWaitPanel.RoomActionListener() {
                        public void onBackToRoomList() {
                            socketManager.sendMessage("COMMAND:LEAVE:");
                        }

                        public void onToggleReady(String roomId, boolean ready) {
                            socketManager.sendMessage("COMMAND:READY:" + roomId + ":" + ready);
                        }
                    });
                    setPanel("ROOM_WAIT", waitPanel);
                }


                // 추가 구현


            });
        });

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createHomePanel(), "HOME");

        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("게임 클라이언트 메인 화면", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JButton connectBtn = new JButton("대기방 목록 보기");
        connectBtn.addActionListener(e -> connectToServer());

        JPanel center = new JPanel();
        center.add(connectBtn);
        panel.add(center, BorderLayout.CENTER);

        return panel;
    }

    private void connectToServer() {
        new Thread(() -> {
            boolean connected = socketManager.connectToServer("127.0.0.1", 12345);
            if (connected) {
                socketManager.startReceiver();
                socketManager.sendMessage("COMMAND:LOGIN:");
            } else {
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(this, "서버 연결 실패")
                );
            }
        }).start();
    }

    public void setPanel(String name, JPanel panel) {
        mainPanel.add(panel, name);
        cardLayout.show(mainPanel, name);
    }
}


