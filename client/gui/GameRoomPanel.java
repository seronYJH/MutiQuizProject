package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameRoomPanel extends JPanel {
    private JList<String> userList;
    private JComboBox<String> categoryBox;
    private ChatPanelOri chatPanelOri;
    private JButton readyButton;

    private JFrame parentFrame;  // 🔹 JFrame (MainFrame)
    private String mode;         // 🔹 "multi" 또는 "coop"

    public GameRoomPanel(String roomName, List<String> players, boolean isCouple,
                         JFrame parentFrame, String mode) {
        this.parentFrame = parentFrame;
        this.mode = mode;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 🧾 상단 타이틀
        JLabel titleLabel = new JLabel("🧩 " + roomName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // 👤 참가자 목록
        userList = new JList<>(players.toArray(new String[0]));
        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setBorder(BorderFactory.createTitledBorder("참가자 목록"));
        userScroll.setPreferredSize(new Dimension(150, 0));
        add(userScroll, BorderLayout.WEST);

        // 📚 카테고리 선택 + 준비 버튼
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        categoryBox = new JComboBox<>(new String[]{"일반 상식", "과학", "게임", "영화", "역사"});
        categoryBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        categoryBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        readyButton = new JButton("✅ 준비 완료");
        readyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        readyButton.setMaximumSize(new Dimension(200, 40));

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(new JLabel("문제 카테고리 선택:"));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(categoryBox);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(readyButton);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        // 💬 채팅 영역
        chatPanelOri = new ChatPanelOri("공용채팅", true, isCouple);
        chatPanelOri.setBorder(BorderFactory.createTitledBorder("채팅"));
        chatPanelOri.setPreferredSize(new Dimension(250, 0));
        add(chatPanelOri, BorderLayout.EAST);

        // 🔙 하단 나가기 버튼
        JButton exitButton = new JButton("나가기");
        exitButton.addActionListener(e -> {
            System.out.println("Exit button clicked");  // 버튼 클릭 확인
            if (parentFrame instanceof MainFrame) {
                System.out.println("Parent is MainFrame, calling returnToWaitingRoom");
                MainFrame mainFrame = (MainFrame) parentFrame;  // parentFrame을 MainFrame으로 다운캐스팅
                mainFrame.returnToWaitingRoom(); // 해당 모드에 맞는 대기실로 돌아가도록 호출
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(exitButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // ✅ 준비 버튼 동작
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = (String) categoryBox.getSelectedItem();
                chatPanelOri.addMessage("🟢 [SYSTEM] 카테고리 '" + category + "' 선택 완료!", true);
                // 🔜 서버로 전송 처리 예정
            }
        });
    }

    public void appendChat(String msg, boolean isMe) {
        chatPanelOri.addMessage(msg, isMe);
    }

    public void syncChat(List<String> log) {
        for (String m : log) {
            chatPanelOri.addMessage(m, false);
        }
    }

    public String getSelectedCategory() {
        return (String) categoryBox.getSelectedItem();
    }

    public void setPlayers(List<String> players) {
        userList.setListData(players.toArray(new String[0]));
    }
}
