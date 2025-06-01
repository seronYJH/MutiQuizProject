package client.gui;

import javax.swing.*;
import java.awt.*;

public class ChatBubbleOri extends JPanel {

    public ChatBubbleOri(String message, boolean isMe, boolean isCoupleIgnored) {
        setLayout(new BorderLayout());
        setOpaque(false); // 투명 처리

        JLabel label = new JLabel("<html><div style='padding:8px;'>" + message + "</div></html>");
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        label.setForeground(Color.BLACK);

        if (isMe) {
            label.setBackground(new Color(255, 241, 118)); // 💛 노란색
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            setLayout(new FlowLayout(FlowLayout.RIGHT));
        } else {
            label.setBackground(Color.WHITE); // 🤍 하얀색
            label.setHorizontalAlignment(SwingConstants.LEFT);
            setLayout(new FlowLayout(FlowLayout.LEFT));
        }

        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        add(label);
    }
}
