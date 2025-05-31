package client.gui;

import common.Player;  // 추가된 import

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private MainMenuPanel mainMenuPanel;
    private WaitingRoomPanel multiWaitingRoomPanel;
    private WaitingRoomPanel coopWaitingRoomPanel;
    private RankingPanel rankingPanel;  // 여기에 RankingPanel을 선언

    private GameRoomPanel gameRoomPanel; // 게임방 패널을 추가
    private String currentMode = ""; // 현재 모드를 저장할 변수

    public MainFrame() {
        setTitle("퀴즈 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        registerPanel = new RegisterPanel(() -> cardLayout.show(mainPanel, "login"));

        loginPanel = new LoginPanel(
                () -> cardLayout.show(mainPanel, "register"),
                (Player player) -> {
                    List<String> receivedMessages = new ArrayList<>();

                    // 대기실 패널 생성 및 초기화
                    multiWaitingRoomPanel = new WaitingRoomPanel();
                    coopWaitingRoomPanel = new WaitingRoomPanel();
                    multiWaitingRoomPanel.setParentFrame(this);
                    coopWaitingRoomPanel.setParentFrame(this);

                    // 대기실 패널을 mainPanel에 추가
                    mainPanel.add(multiWaitingRoomPanel, "multiWaiting");
                    mainPanel.add(coopWaitingRoomPanel, "coopWaiting");

                    // 메인 메뉴 생성
                    mainMenuPanel = new MainMenuPanel(
                            player.getNickname(),
                            player.getScore(),
                            player.getCoins(),
                            receivedMessages,
                            () -> {
                                currentMode = "multi";  // multi 대기실로 돌아갈 때 모드 기억
                                cardLayout.show(mainPanel, "multiWaiting");
                            },
                            () -> {
                                currentMode = "coop";  // coop 대기실로 돌아갈 때 모드 기억
                                cardLayout.show(mainPanel, "coopWaiting");
                            }
                    );

                    // 메인 메뉴를 mainPanel에 추가
                    mainPanel.add(mainMenuPanel, "mainMenu");

                    // 메인 메뉴 화면으로 전환
                    cardLayout.show(mainPanel, "mainMenu");
                });

        mainPanel.add(loginPanel, "login");
        mainPanel.add(registerPanel, "register");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
    }

    // 🔙 게임방 -> 대기실로 돌아가기
    public void returnToWaitingRoom() {
        System.out.println("Trying to switch to: " + currentMode);  // 로그 추가

        if ("multi".equals(currentMode)) {
            System.out.println("Switching to multiWaiting");  // 카드 전환 로그
            cardLayout.show(mainPanel, "multiWaiting");
        } else if ("coop".equals(currentMode)) {
            System.out.println("Switching to coopWaiting");  // 카드 전환 로그
            cardLayout.show(mainPanel, "coopWaiting");
        }
    }

    // 게임방으로 돌아가기
    public void returnToGameRoom(String mode) {
        System.out.println("Returning to game room with mode: " + mode);  // 로그 추가

        // currentMode 값을 기억하고 있다면 그 값을 기준으로 게임방에 들어가도록 처리
        if ("multi".equals(mode)) {
            gameRoomPanel = new GameRoomPanel("게임방 (멀티)", List.of("유빈"), false, this, "multi");
            mainPanel.add(gameRoomPanel, "gameRoom");
            cardLayout.show(mainPanel, "gameRoom");
        } else if ("coop".equals(mode)) {
            gameRoomPanel = new GameRoomPanel("게임방 (협동)", List.of("유빈"), false, this, "coop");
            mainPanel.add(gameRoomPanel, "gameRoom");
            cardLayout.show(mainPanel, "gameRoom");
        }
    }

    // RankingPanel 표시하기
    public void showRankingPanel(RankingPanel rankingPanel) {
        this.rankingPanel = rankingPanel;
        mainPanel.add(rankingPanel, "rankingPanel");
        cardLayout.show(mainPanel, "rankingPanel");
    }

    // Getter for coop waiting room
    public WaitingRoomPanel getCoopWaitingRoomPanel() {
        return coopWaitingRoomPanel;
    }

    // Getter for multi waiting room ⬅️ 추가한 메서드
    public WaitingRoomPanel getMultiWaitingRoomPanel() {
        return multiWaitingRoomPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
