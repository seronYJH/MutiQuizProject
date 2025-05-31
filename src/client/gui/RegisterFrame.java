package client.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterFrame extends JFrame {

    private JTextField idField, nicknameField;
    private JPasswordField passwordField;
    private JButton registerBtn;

    public RegisterFrame() {
        setTitle("회원가입");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("아이디:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("비밀번호:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("닉네임:"));
        nicknameField = new JTextField();
        panel.add(nicknameField);

        registerBtn = new JButton("회원가입");
        registerBtn.addActionListener(e -> registerUser());
        panel.add(new JLabel()); // 빈칸
        panel.add(registerBtn);

        add(panel);
        setVisible(true);
    }

    private void registerUser() {
        String id = idField.getText().trim();
        String pw = new String(passwordField.getPassword()).trim();
        String nickname = nicknameField.getText().trim();

        if (id.isEmpty() || pw.isEmpty() || nickname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 칸을 입력해주세요.");
            return;
        }

        try (Connection conn = db.DBUtil.getConnection()) {
            PreparedStatement checkId = conn.prepareStatement("SELECT * FROM users WHERE login_id = ?");
            checkId.setString(1, id);
            ResultSet rsId = checkId.executeQuery();
            if (rsId.next()) {
                JOptionPane.showMessageDialog(this, "이미 사용 중인 아이디입니다.");
                return;
            }

            PreparedStatement checkNick = conn.prepareStatement("SELECT * FROM users WHERE nickname = ?");
            checkNick.setString(1, nickname);
            ResultSet rsNick = checkNick.executeQuery();
            if (rsNick.next()) {
                JOptionPane.showMessageDialog(this, "이미 사용 중인 닉네임입니다.");
                return;
            }

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (login_id, password, nickname) VALUES (?, ?, ?)");
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            pstmt.setString(3, nickname);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "회원가입 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
