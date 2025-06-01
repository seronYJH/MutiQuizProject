package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainMenuPanel extends JPanel {

    private JLabel nicknameLabel;
    private JLabel scoreLabel;
    private JLabel coinLabel;

    public MainMenuPanel(
            String nickname, int score, int coins, List<String> receivedMessages,
            Runnable goToMultiWaitingRoom, Runnable goToCoopWaitingRoom
    ) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 👤 유저 정보 (좌측 상단)
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        nicknameLabel = new JLabel("👤 닉네임: " + nickname);
        scoreLabel = new JLabel("🏆 점수: " + score + "점");
        coinLabel = new JLabel("🪙 코인: " + coins + "개");

        userInfoPanel.add(nicknameLabel);
        userInfoPanel.add(scoreLabel);
        userInfoPanel.add(coinLabel);
        add(userInfoPanel, BorderLayout.NORTH);

        // 🎮 게임 모드 버튼
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        JButton aiButton = new JButton("🤖 AI 대전");
        JButton multiButton = new JButton("🌐 멀티모드");
        JButton coopButton = new JButton("🤝 협동모드");

        aiButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        multiButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        coopButton.setFont(new Font("SansSerif", Font.BOLD, 18));

        centerPanel.add(aiButton);
        centerPanel.add(multiButton);
        centerPanel.add(coopButton);
        add(centerPanel, BorderLayout.CENTER);

        // 💬 받은 채팅 미리보기 → JList로 변경
        DefaultListModel<String> chatListModel = new DefaultListModel<>();
        for (String msg : receivedMessages) {
            chatListModel.addElement(msg);
        }

        JList<String> chatList = new JList<>(chatListModel);
        chatList.setBorder(BorderFactory.createTitledBorder("💬 받은 채팅 (더블클릭 시 열기)"));
        chatList.setBackground(new Color(240, 248, 255));
        chatList.setFont(new Font("SansSerif", Font.PLAIN, 13));

        // 🖱 더블 클릭 시 → ChatFrame 열기
        chatList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = chatList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        String item = chatListModel.getElementAt(index);
                        String senderNickname = item.split(":")[0].trim();
                        SwingUtilities.invokeLater(() -> {
                            new ChatFrame(senderNickname, true, false).setVisible(true);
                        });
                    }
                }
            }
        });

        JScrollPane chatScrollPane = new JScrollPane(chatList);
        chatScrollPane.setPreferredSize(new Dimension(250, 0));
        add(chatScrollPane, BorderLayout.EAST);

        // 버튼 동작 변경
        aiButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "AI 대전 시작!"));
        multiButton.addActionListener(e -> goToMultiWaitingRoom.run());
        coopButton.addActionListener(e -> goToCoopWaitingRoom.run());
    }
}
