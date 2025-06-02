package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoomListPanel extends JPanel {
    private JPanel roomListContainer; // 대기방들을 순서대로 나열하기 위한 패널.
    private JButton createRoomBtn; // 방 생성 버튼.
    private RoomSelectListener listener;

    // 이벤트를 외부에서 처리하도록 도와주는 쿨백 인터페이스
    public interface RoomSelectListener {
        void onRoomSelected(String roomId);
        void onCreateRoom(String roomName, String category, int maxPlayers); // 방 생성 이벤트
    }

    public RoomListPanel(RoomSelectListener listener) { // 생성과 동시에 클릭 이벤트 처리
        setLayout(new BorderLayout());

        JLabel title = new JLabel("대기 중인 방 목록", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // 방 목록이 담길 영역
        roomListContainer = new JPanel();
        roomListContainer.setLayout(new BoxLayout(roomListContainer, BoxLayout.Y_AXIS)); // 세로로 나열
        JScrollPane scrollPane = new JScrollPane(roomListContainer); // 스크롤 추가.
        roomListContainer.setPreferredSize(new Dimension(250, 400));
        scrollPane.setPreferredSize(new Dimension(250, 400));
        add(scrollPane, BorderLayout.CENTER);

        // 하단 버튼
        JPanel bottomPanel = new JPanel(new FlowLayout()); // 가로로 정렬.
        createRoomBtn = new JButton("방 생성");
        bottomPanel.add(createRoomBtn);
        bottomPanel.add(new JButton("상점")); // 추후 구현
        bottomPanel.add(new JButton("랭킹")); // 추후 구현
        createRoomBtn.addActionListener(e -> {
            // 방 생성 프레임 띄우기
            new CreateRoomFrame((roomName, category, maxPlayers) -> {
                listener.onCreateRoom(roomName, category, maxPlayers);
            }).setVisible(true);
        });
        bottomPanel.add(createRoomBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateRoomListFromString(String roomListString) {
        roomListContainer.removeAll(); // 기존 나열방 제거.

        // 예: "ROOM:LIST:abc(1/4);def(2/3);"
        if (roomListString.startsWith("ROOM:LIST:")) {
            String listPart = roomListString.substring("ROOM:LIST:".length());
            String[] rooms = listPart.split(";");

            for (String roomInfo : rooms) {
                if (roomInfo.isEmpty()) continue;

                String[] parts = roomInfo.split("[()]");
                String roomId = parts[0]; // abc
                String[] counts = parts[1].split("/");
                int current = Integer.parseInt(counts[0]); // 1
                int max = Integer.parseInt(counts[1]); //4

                // 이제 이 정보를 가지고 각 방 패널 생성
                JPanel roomPanel = new JPanel(new GridLayout(1, 0));
                roomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                roomPanel.add(new JLabel("방 ID: " + roomId));
                roomPanel.add(new JLabel("인원: " + current + "/" + max));

                roomPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        roomPanel.setBackground(Color.LIGHT_GRAY);
                        roomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        roomPanel.setBackground(Color.WHITE);
                        roomPanel.setCursor(Cursor.getDefaultCursor());
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (current < max) {
                            listener.onRoomSelected(roomId);
                        } else {
                            JOptionPane.showMessageDialog(null, "이 방은 가득 찼습니다.");
                        }
                    }
                });
                //목록 추가.
                roomListContainer.add(roomPanel);
            }
        }
        // 목록 새로고침.
        roomListContainer.revalidate();
        roomListContainer.repaint();
    }

    public JButton getCreateRoomButton() {
        return createRoomBtn;
    }
}