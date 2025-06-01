package client.test;

import client.gui.LoginPanel;
import client.gui.RegisterPanel;
import common.Player;

import javax.swing.*;
import java.awt.*;

public class TestLoginRegisterFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    public TestLoginRegisterFrame() {
        setTitle("로그인 & 회원가입 테스트");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        LoginPanel loginPanel = new LoginPanel(
                () -> cardLayout.show(mainPanel, "register"),
                player -> {
                    JOptionPane.showMessageDialog(this, player.getNickname() + "님 로그인 성공!");
                    // 메인 메뉴로 이동할 수 있음!
                });

        RegisterPanel registerPanel = new RegisterPanel(() -> cardLayout.show(mainPanel, "login"));

        mainPanel.add(loginPanel, "login");
        mainPanel.add(registerPanel, "register");
        add(mainPanel);

        cardLayout.show(mainPanel, "login");
        setVisible(true);
    }

    public static void main(String[] args) {
        new TestLoginRegisterFrame();
    }
}
