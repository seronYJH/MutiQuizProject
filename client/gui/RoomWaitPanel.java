package client;
import javax.swing.*;
import java.awt.*;

public class RoomWaitPanel extends JPanel {

    private JLabel roomInfoLabel;
    private JPanel playerListPanel;
    private JButton readyButton;
    private JButton backButton;

    private String roomId;
    private String nickname; // 현재 유저의 닉네임
    private boolean isReady = false;

    public interface RoomActionListener {
        void onBackToRoomList();
        void onToggleReady(String roomId, boolean ready);
    }

    private RoomActionListener listener;

    public RoomWaitPanel(String roomId, String nickname, RoomActionListener listener) {
        this.roomId = roomId;
        this.nickname = nickname;
        this.listener = listener;

        setLayout(new BorderLayout());

        // 상단 방 정보
        roomInfoLabel = new JLabel("방 ID: " + roomId, SwingConstants.CENTER);
        roomInfoLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        add(roomInfoLabel, BorderLayout.NORTH);

        // 가운데 플레이어 목록
        playerListPanel = new JPanel();
        playerListPanel.setLayout(new BoxLayout(playerListPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(playerListPanel), BorderLayout.CENTER);

        // 하단 버튼
        JPanel buttonPanel = new JPanel(new FlowLayout());
        readyButton = new JButton("Ready");
        backButton = new JButton("Leave");

        readyButton.addActionListener(e -> {
            isReady = !isReady;
            readyButton.setText(isReady ? "준비 취소" : "준비"); // 삼항논리로 버튼의 상태를 바꿀 수 있음.
            listener.onToggleReady(roomId, isReady); // 서버에 상태 전달.
        });

        backButton.addActionListener(e -> {
            listener.onBackToRoomList();
        });

        buttonPanel.add(readyButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // 서버로부터 받은 ROOM:INFO:roomId:player1,true;player2,false;... 를 받아서 갱신
    public void updatePlayerList(String roomInfoString) {
        playerListPanel.removeAll();

        // ROOM:INFO:room123:Alice,true;Bob,false;Charlie,true;
        String[] parts = roomInfoString.split(":");
        if (parts.length < 3) return;

        String playersPart = parts[2]; // Alice,true;Bob,false;Charlie,true;
        String[] players = playersPart.split(";");

        for (String playerInfo : players) { // 한 유저씩 구별.
            if (playerInfo.isEmpty()) continue;
            String[] info = playerInfo.split(",");
            String name = info[0]; // Alice
            boolean isReady = Boolean.parseBoolean(info[1]); // true
            JLabel playerLabel = new JLabel(name + (isReady ? " (준비됨)" : " (대기 중)"));
            playerListPanel.add(playerLabel);
            if (name.equals(nickname)) {
                this.isReady = isReady; // 서버 상태로 동기화
                readyButton.setText(isReady ? "준비 취소" : "준비");
            }
        }

        playerListPanel.revalidate();
        playerListPanel.repaint();
    }
}
