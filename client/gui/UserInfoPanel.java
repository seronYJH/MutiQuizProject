package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserInfoPanel extends JPanel {

    private JLabel nicknameLabel;
    private JLabel scoreLabel;
    private JLabel coinLabel;
    private JLabel coupleLabel;

    private JButton coupleBtn;
    private JButton chatBtn;
    private JButton backBtn;

    private boolean isSelf;

    public UserInfoPanel(String nickname, int score, int coins, String coupleNickname, boolean isSelf) {
        this.isSelf = isSelf;

        setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // 정보 라벨들
        centerPanel.add(new JLabel("닉네임:"));
        nicknameLabel = new JLabel(nickname);
        centerPanel.add(nicknameLabel);

        centerPanel.add(new JLabel("점수:"));
        scoreLabel = new JLabel(String.valueOf(score));
        centerPanel.add(scoreLabel);

        centerPanel.add(new JLabel("코인:"));
        coinLabel = new JLabel(String.valueOf(coins));
        centerPanel.add(coinLabel);

        centerPanel.add(new JLabel("커플:"));
        coupleLabel = new JLabel(coupleNickname != null ? coupleNickname : "없음");
        centerPanel.add(coupleLabel);

        add(centerPanel, BorderLayout.CENTER);

        // 하단 버튼
        JPanel buttonPanel = new JPanel();

        if (isSelf) {
            coupleBtn = new JButton("커플 해제");
        } else {
            coupleBtn = new JButton("커플 신청");
        }

        chatBtn = new JButton("채팅 보내기");
        backBtn = new JButton("뒤로가기");

        if (!isSelf) buttonPanel.add(chatBtn);
        buttonPanel.add(coupleBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // === 리스너 등록 ===
    public void setCoupleButtonListener(ActionListener listener) {
        coupleBtn.addActionListener(listener);
    }

    public void setChatButtonListener(ActionListener listener) {
        if (!isSelf) {
            chatBtn.addActionListener(listener);
        }
    }

    public void setBackButtonListener(ActionListener listener) {
        backBtn.addActionListener(listener);
    }

    // === 커플 상태 갱신 ===
    public void updateCouple(String newCouple) {
        coupleLabel.setText(newCouple != null ? newCouple : "없음");
    }
}
