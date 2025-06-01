package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanelOri extends JPanel {

    private JPanel chatArea;
    private JScrollPane scrollPane;
    private JTextField inputField;
    private JButton sendButton;

    public ChatPanelOri(String roomLabel, boolean dummyOnline, boolean coupleIgnored) {
        setLayout(new BorderLayout());
        setBackground(new Color(224, 247, 250)); // 🌈 하늘색

        JLabel header = new JLabel("💬 " + roomLabel);
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));
        chatArea.setBackground(new Color(224, 247, 250)); // 🌈 하늘색

        scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        sendButton = new JButton("전송");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 13));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // 입력 이벤트
        ActionListener sendAction = e -> {
            String msg = inputField.getText().trim();
            if (!msg.isEmpty()) {
                addMessage(msg, true);
                inputField.setText("");
                // 나중에 서버 전송
            }
        };

        sendButton.addActionListener(sendAction);
        inputField.addActionListener(sendAction);
    }

    public void addMessage(String msg, boolean isMe) {
        chatArea.add(new ChatBubble(msg, isMe, false));
        chatArea.revalidate();
        chatArea.repaint();
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }
}
