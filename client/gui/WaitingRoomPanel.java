package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WaitingRoomPanel extends JPanel {

    private DefaultListModel<String> roomListModel;
    private JList<String> roomList;
    private JButton createRoomBtn, joinRoomBtn, exitBtn;
    private JButton shopBtn, rankBtn; // rankBtn 추가

    private JFrame parentFrame;

    public WaitingRoomPanel() {
        setLayout(new BorderLayout());

        roomListModel = new DefaultListModel<>();
        roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(roomList), BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout());
        createRoomBtn = new JButton("방 만들기");
        joinRoomBtn = new JButton("입장하기");
        exitBtn = new JButton("나가기");
        topPanel.add(createRoomBtn);
        topPanel.add(joinRoomBtn);
        topPanel.add(exitBtn);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        shopBtn = new JButton("상점");
        rankBtn = new JButton("랭킹"); // 랭킹 버튼 추가
        bottomPanel.add(shopBtn);
        bottomPanel.add(rankBtn); // 랭킹 버튼 추가
        add(bottomPanel, BorderLayout.SOUTH);

        // 상점 버튼 리스너 추가
        shopBtn.addActionListener(e -> {
            if (parentFrame instanceof MainFrame) {
                MainFrame mainFrame = (MainFrame) parentFrame;
                mainFrame.showShopPanel(100); // 100 코인 예시
            }
        });

        // 랭킹 버튼 클릭 시 RankingPanel로 이동
        rankBtn.addActionListener(e -> {
            if (parentFrame instanceof MainFrame) {
                MainFrame mainFrame = (MainFrame) parentFrame;
                mainFrame.showRankingPanel(new RankingPanel()); // 랭킹 패널로 이동
            }
        });

        // 나가기 버튼 리스너 추가
        exitBtn.addActionListener(e -> {
            System.out.println("나가기 버튼 클릭됨");
            // 나가기 처리 코드 작성 (게임룸으로 돌아가기 등)
        });

        // 방 만들기 버튼 리스너 추가
        createRoomBtn.addActionListener(e -> {
            // 방 만들기 로직 추가
        });

        // 방 입장하기 버튼 리스너 추가
        joinRoomBtn.addActionListener(e -> {
            // 방 입장하기 로직 추가
        });
    }

    public void setParentFrame(JFrame frame) {
        this.parentFrame = frame;
    }

    public void updateRoomList(String[] rooms) {
        roomListModel.clear();
        for (String room : rooms) {
            roomListModel.addElement(room);
        }
    }

    public String getSelectedRoom() {
        return roomList.getSelectedValue();
    }

    public void setCreateRoomListener(ActionListener listener) {
        createRoomBtn.addActionListener(listener);
    }

    public void setJoinRoomListener(ActionListener listener) {
        joinRoomBtn.addActionListener(listener);
    }

    public void setExitListener(ActionListener listener) {
        exitBtn.addActionListener(listener);
    }

    public void setShopListener(ActionListener listener) {
        shopBtn.addActionListener(listener);
    }

    public void setRankListener(ActionListener listener) {
        rankBtn.addActionListener(listener);
    }
}
