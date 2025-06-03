package client.gui;

import javax.swing.*;
import java.awt.*;

import common.Player;
import db.PlayerDAO;


public class LoginPanel extends JPanel {
    private JTextField idField;
    private JPasswordField pwField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton findPwButton;

    public interface LoginSuccessListener {
        void onLoginSuccess(Player player);
    }

    public LoginPanel(Runnable goToRegisterPanel, LoginSuccessListener listener) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("로그인", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        formPanel.add(new JLabel("아이디:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("비밀번호:"));
        pwField = new JPasswordField();
        formPanel.add(pwField);

        loginButton = new JButton("로그인");
        registerButton = new JButton("회원가입");
        findPwButton = new JButton("비밀번호 찾기");

        formPanel.add(loginButton);
        formPanel.add(registerButton);

        add(formPanel, BorderLayout.CENTER);
        add(findPwButton, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String pw = new String(pwField.getPassword()).trim();

            Player player = PlayerDAO.login(id, pw);
            if (player != null) {
                JOptionPane.showMessageDialog(this, "환영합니다, " + player.getNickname() + "님!");
                listener.onLoginSuccess(player);
            } else {
                JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 틀렸습니다.");
            }
        });

        registerButton.addActionListener(e -> goToRegisterPanel.run());

        findPwButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "아이디 입력:");
            String nickname = JOptionPane.showInputDialog(this, "닉네임 입력:");
            if (id != null && nickname != null) {
                String pw = PlayerDAO.findPassword(id, nickname);
                if (pw != null) {
                    JOptionPane.showMessageDialog(this, "비밀번호: " + pw);
                } else {
                    JOptionPane.showMessageDialog(this, "일치하는 정보가 없습니다.");
                }
            }
        });
    }
}
