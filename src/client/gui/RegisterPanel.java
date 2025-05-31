package client.gui;

import db.PlayerDAO;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private JTextField idField;
    private JTextField nicknameField;
    private JPasswordField pwField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterPanel(Runnable goToLoginPanel) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("회원가입", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        formPanel.add(new JLabel("아이디:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("비밀번호:"));
        pwField = new JPasswordField();
        formPanel.add(pwField);

        formPanel.add(new JLabel("닉네임:"));
        nicknameField = new JTextField();
        formPanel.add(nicknameField);

        registerButton = new JButton("가입하기");
        backButton = new JButton("뒤로가기");

        formPanel.add(registerButton);
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);

        registerButton.addActionListener(e -> handleRegister());
        backButton.addActionListener(e -> goToLoginPanel.run());
    }

    private void handleRegister() {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword()).trim();
        String nickname = nicknameField.getText().trim();

        if (id.isEmpty() || pw.isEmpty() || nickname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 칸을 입력해주세요!");
            return;
        }

        if (PlayerDAO.isIdDuplicated(id)) {
            JOptionPane.showMessageDialog(this, "이미 존재하는 아이디입니다!");
            return;
        }

        if (PlayerDAO.isNicknameDuplicated(nickname)) {
            JOptionPane.showMessageDialog(this, "이미 존재하는 닉네임입니다!");
            return;
        }

        boolean success = PlayerDAO.register(id, pw, nickname);
        if (success) {
            JOptionPane.showMessageDialog(this, "회원가입 성공! 로그인 해주세요.");
            idField.setText("");
            pwField.setText("");
            nicknameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "회원가입 실패... 다시 시도해주세요.");
        }
    }
}
