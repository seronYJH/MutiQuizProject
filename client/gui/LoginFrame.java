package client.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginBtn, registerBtn, findPwBtn;

    public LoginFrame() {
        setTitle("로그인");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 아이디 입력
        panel.add(new JLabel("아이디:"));
        idField = new JTextField();
        panel.add(idField);

        // 비밀번호 입력
        panel.add(new JLabel("비밀번호:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // 로그인 버튼
        loginBtn = new JButton("로그인");
        loginBtn.addActionListener(e -> login());
        panel.add(loginBtn);

        // 회원가입 버튼
        registerBtn = new JButton("회원가입");
        registerBtn.addActionListener(e -> new RegisterFrame());
        panel.add(registerBtn);

        // 비밀번호 찾기 버튼
        findPwBtn = new JButton("비밀번호 찾기");
        findPwBtn.addActionListener(e -> {
            // TODO: 비밀번호 찾기 프레임 연결 예정
            JOptionPane.showMessageDialog(this, "비밀번호 찾기 기능은 아직 준비 중입니다.");
        });
        panel.add(findPwBtn);

        add(panel);
        setVisible(true);
    }

    private void login() {
        String id = idField.getText().trim();
        String pw = new String(passwordField.getPassword()).trim();

        if (id.isEmpty() || pw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력해주세요.");
            return;
        }

        try (Connection conn = db.DBUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE login_id = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nickname = rs.getString("nickname");
                JOptionPane.showMessageDialog(this, nickname + "님 환영합니다!");
                dispose(); // 로그인 창 닫기
                // TODO: 메인 화면(MainFrame)으로 이동
            } else {
                JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 잘못되었습니다.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "로그인 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
