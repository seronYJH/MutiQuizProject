package client.gui;

import javax.swing.*;
import java.awt.*;

public class ChatBubble extends JPanel {

    public ChatBubble(String message, boolean isMe, boolean isCouple) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("<html><p style='width: 200px;'>" + message + "</p></html>");
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));

        if (isMe) {
            label.setBackground(isCouple ? new Color(255, 204, 229) : Color.WHITE);
            label.setForeground(Color.BLACK);
            setLayout(new FlowLayout(FlowLayout.RIGHT));
        } else {
            label.setBackground(isCouple ? new Color(255, 235, 245) : new Color(230, 230, 230));
            label.setForeground(Color.BLACK);
            setLayout(new FlowLayout(FlowLayout.LEFT));
        }

        add(label);
    }
}