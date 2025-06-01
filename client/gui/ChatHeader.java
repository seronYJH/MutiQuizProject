package client.gui;

import javax.swing.*;
import java.awt.*;

public class ChatHeader extends JPanel {
    private JLabel nameLabel;
    private JLabel statusLabel;

    public ChatHeader(String nickname, boolean isOnline) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameLabel = new JLabel(nickname);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        statusLabel = new JLabel(isOnline ? "🟢 온라인" : "🔘 오프라인");
        statusLabel.setForeground(isOnline ? new Color(0, 153, 0) : Color.GRAY);

        add(nameLabel, BorderLayout.WEST);
        add(statusLabel, BorderLayout.EAST);
    }

    public void setOnline(boolean isOnline) {
        statusLabel.setText(isOnline ? "🟢 온라인" : "🔘 오프라인");
        statusLabel.setForeground(isOnline ? new Color(0, 153, 0) : Color.GRAY);
    }
}
