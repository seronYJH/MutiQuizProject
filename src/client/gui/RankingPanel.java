package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

public class RankingPanel extends JPanel {

    private JTextField searchField;
    private JButton searchBtn;
    private DefaultListModel<String> rankListModel;
    private JList<String> rankList;

    private DefaultListModel<String> onlineUserModel;
    private JList<String> onlineUserList;

    private JButton backBtn;

    public RankingPanel() {
        setLayout(new BorderLayout());

        // 상단: 검색창 (돋보기 + 텍스트 + 입력 + 버튼)
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftBox = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel iconLabel = new JLabel("\uD83D\uDD0D"); // 돋보기 아이콘
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JLabel textLabel = new JLabel("유저 검색");
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        leftBox.add(iconLabel);
        leftBox.add(textLabel);
        topPanel.add(leftBox, BorderLayout.WEST);

        searchField = new JTextField();
        topPanel.add(searchField, BorderLayout.CENTER);

        searchBtn = new JButton("검색");
        topPanel.add(searchBtn, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // 랭킹 리스트 (왼쪽)
        rankListModel = new DefaultListModel<>();
        rankList = new JList<>(rankListModel);
        rankList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane rankScroll = new JScrollPane(rankList);

        // 접속 중 유저 리스트 (오른쪽)
        onlineUserModel = new DefaultListModel<>();
        onlineUserList = new JList<>(onlineUserModel);
        onlineUserList.setEnabled(false);
        JPanel onlinePanel = new JPanel(new BorderLayout());
        onlinePanel.setPreferredSize(new Dimension(150, 0));
        onlinePanel.setBorder(BorderFactory.createTitledBorder("접속 중인 유저"));
        onlinePanel.add(new JScrollPane(onlineUserList), BorderLayout.CENTER);

        // 중앙 영역: 랭킹 + 접속유저
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(rankScroll, BorderLayout.CENTER);
        centerPanel.add(onlinePanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        // 하단: 뒤로가기
        backBtn = new JButton("돌아가기");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // 돌아가기 버튼 클릭 시 대기실로 돌아가기
        backBtn.addActionListener(e -> {
            System.out.println("돌아가기 버튼 클릭됨");
            if (parentFrame instanceof MainFrame) {
                MainFrame mainFrame = (MainFrame) parentFrame;
                // 대기실로 돌아가기
                mainFrame.returnToWaitingRoom(); // 현재 모드로 돌아가도록 처리
            }
        });
    }

    private JFrame parentFrame;

    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    // === 유저 랭킹 갱신 ===
    public void updateRankingList(Vector<String> userRankings) {
        rankListModel.clear();
        for (String entry : userRankings) {
            rankListModel.addElement(entry);
        }
    }

    // === 접속 중 유저 리스트 갱신 ===
    public void updateOnlineUsers(Vector<String> nicknames) {
        onlineUserModel.clear();
        for (String name : nicknames) {
            onlineUserModel.addElement(name);
        }
    }

    // === 선택된 유저 닉네임 반환 ===
    public String getSelectedUser() {
        String selected = rankList.getSelectedValue();
        if (selected != null) {
            return selected.split(" ")[0];
        }
        return null;
    }

    public String getSearchText() {
        return searchField.getText().trim();
    }

    // === 리스너 등록 ===
    public void setSearchListener(ActionListener listener) {
        searchBtn.addActionListener(listener);
    }

    public void setBackListener(ActionListener listener) {
        backBtn.addActionListener(listener);
    }

    public void setUserClickListener(ActionListener listener) {
        rankList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                listener.actionPerformed(null);
            }
        });
    }
}
