package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChatPanel extends JPanel {

    private JPanel chatArea;
    private JScrollPane scrollPane;
    private JTextField inputField;
    private JButton sendButton;

    private boolean isCouple;

    public ChatPanel(String otherNickname, boolean otherOnline, boolean isCouple) {
        this.isCouple = isCouple;

        setLayout(new BorderLayout());

        // 상단 닉네임 + 상태
        ChatHeader header = new ChatHeader(otherNickname, otherOnline);
        add(header, BorderLayout.NORTH);

        // 채팅 로그 영역
        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));
        chatArea.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // 입력창 + 버튼
        inputField = new JTextField();
        sendButton = new JButton(isCouple ? "💌" : "전송");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        sendButton.setBackground(isCouple ? new Color(255, 192, 203) : Color.WHITE);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // 전송 이벤트
        ActionListener sendAction = e -> {
            String msg = inputField.getText().trim();
            if (!msg.isEmpty()) {
                addMessage(msg, true); // 내 메시지
                inputField.setText("");
                // 🔜 나중에 서버로 전송
            }
        };

        sendButton.addActionListener(sendAction);
        inputField.addActionListener(sendAction);
    }

    public void addMessage(String msg, boolean isMe) {
        chatArea.add(new ChatBubble(msg, isMe, isCouple));
        chatArea.revalidate();
        chatArea.repaint();
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }
}
