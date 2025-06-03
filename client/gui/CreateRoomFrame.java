package client;

import javax.swing.*;
import java.awt.*;

public class CreateRoomFrame extends JFrame {
    public interface RoomCreateListener {
        void onCreate(String roomName, String category, int maxPlayers);
    }

    public CreateRoomFrame(RoomCreateListener listener) {
        setTitle("Create Room");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextField roomNameField = new JTextField();
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"상식", "과학", "영화", "랜덤"});
        JComboBox<Integer> playerCountBox = new JComboBox<>(new Integer[]{2, 3, 4, 5});

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Name:"));
        formPanel.add(roomNameField);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryBox);
        formPanel.add(new JLabel("Max Player:"));
        formPanel.add(playerCountBox);

        add(formPanel, BorderLayout.CENTER);

        JButton createBtn = new JButton("Make Room");
        JButton cancelBtn = new JButton("Cancel");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        createBtn.addActionListener(e -> {
            String roomName = roomNameField.getText().trim();
            String category = (String) categoryBox.getSelectedItem();
            int maxPlayers = (int) playerCountBox.getSelectedItem();

            if (!roomName.isEmpty()) {
                listener.onCreate(roomName, category, maxPlayers);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Write Room Name");
            }
        });

        cancelBtn.addActionListener(e -> dispose());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createBtn.setEnabled(false);
    }
}
