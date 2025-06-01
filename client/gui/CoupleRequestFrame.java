package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoupleRequestFrame extends JFrame {

    public interface CoupleResponseListener {
        void onAccepted();
        void onRejected();
    }

    public CoupleRequestFrame(String fromNickname, CoupleResponseListener listener) {
        setTitle("💌 커플 신청");
        setSize(350, 160);
        setLocationRelativeTo(null);
        setResizable(false);

        // 💗 배경색 설정
        Color pinkBackground = new Color(0xFDE2E4);  // 연분홍
        Color pinkAccent = new Color(0xF78DA7);       // 진한 분홍
        Color textColor = new Color(0xC2185B);        // 진분홍 텍스트

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(pinkBackground);

        JLabel msgLabel = new JLabel("💌 " + fromNickname + " 님이 커플 신청을 보냈습니다.");
        msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        msgLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        msgLabel.setForeground(textColor);
        msgLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JButton acceptBtn = new JButton("💖 수락");
        JButton rejectBtn = new JButton("🙅 거절");

        acceptBtn.setBackground(pinkAccent);
        acceptBtn.setForeground(Color.WHITE);
        rejectBtn.setBackground(pinkAccent);
        rejectBtn.setForeground(Color.WHITE);

        acceptBtn.setFocusPainted(false);
        rejectBtn.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(pinkBackground);
        buttonPanel.add(acceptBtn);
        buttonPanel.add(rejectBtn);

        content.add(msgLabel, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(content);

        // 버튼 리스너
        acceptBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listener.onAccepted();
                dispose();
            }
        });

        rejectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listener.onRejected();
                dispose();
            }
        });
    }
}
