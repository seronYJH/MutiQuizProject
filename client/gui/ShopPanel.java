package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ShopPanel extends JPanel {

    private JLabel coinLabel;
    private JButton buyRemoveWrongBtn, buySkipBtn, buyRingBtn, buyNicknameBtn;

    public ShopPanel(int userCoins) {
        setLayout(new BorderLayout());

        // 상단: 보유 코인
        coinLabel = new JLabel("보유 코인: " + userCoins + " 💰", SwingConstants.CENTER);
        coinLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(coinLabel, BorderLayout.NORTH);

        // 가운데: 아이템 버튼들
        JPanel itemPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        itemPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        itemPanel.add(new JLabel("오답 제거권 (50코인):"));
        buyRemoveWrongBtn = new JButton("구매");
        itemPanel.add(buyRemoveWrongBtn);

        itemPanel.add(new JLabel("문제 스킵권 (70코인):"));
        buySkipBtn = new JButton("구매");
        itemPanel.add(buySkipBtn);

        itemPanel.add(new JLabel("커플 반지 💍 (100코인):"));
        buyRingBtn = new JButton("구매");
        itemPanel.add(buyRingBtn);

        itemPanel.add(new JLabel("닉네임 변경권 (80코인):"));
        buyNicknameBtn = new JButton("구매");
        itemPanel.add(buyNicknameBtn);

        add(itemPanel, BorderLayout.CENTER);
    }

    public void setCoinDisplay(int coins) {
        coinLabel.setText("보유 코인: " + coins + " 💰");
    }

    // === 버튼 리스너 연결 ===
    public void setBuyRemoveWrongListener(ActionListener listener) {
        buyRemoveWrongBtn.addActionListener(listener);
    }

    public void setBuySkipListener(ActionListener listener) {
        buySkipBtn.addActionListener(listener);
    }

    public void setBuyRingListener(ActionListener listener) {
        buyRingBtn.addActionListener(listener);
    }

    public void setBuyNicknameListener(ActionListener listener) {
        buyNicknameBtn.addActionListener(listener);
    }
}