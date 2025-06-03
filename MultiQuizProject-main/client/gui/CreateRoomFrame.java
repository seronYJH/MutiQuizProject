package client.gui;

import javax.swing.*;
import java.awt.*;

public class CreateRoomFrame extends JFrame {
    public interface RoomCreateListener {
        void onCreate(String roomName, String category, int maxPlayers);
    }

    public CreateRoomFrame(RoomCreateListener listener) {
        setTitle("방 만들기");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        JTextField roomNameField = new JTextField();
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"상식", "과학", "영화", "랜덤"});
        JComboBox<Integer> playerCountBox = new JComboBox<>(new Integer[]{2, 3, 4, 5});

        add(new JLabel("방 제목:"));
        add(roomNameField);
        add(new JLabel("카테고리:"));
        add(categoryBox);
        add(new JLabel("최대 인원:"));
        add(playerCountBox);

        JButton createBtn = new JButton("방 만들기");
        JButton cancelBtn = new JButton("취소");

        createBtn.addActionListener(e -> {
            String roomName = roomNameField.getText().trim();
            String category = (String) categoryBox.getSelectedItem();
            int maxPlayers = (int) playerCountBox.getSelectedItem();

            if (!roomName.isEmpty()) {
                listener.onCreate(roomName, category, maxPlayers);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "방 제목을 입력하세요.");
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        add(createBtn);
        add(cancelBtn);
    }
}
