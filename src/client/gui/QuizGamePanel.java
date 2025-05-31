package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

public class QuizGamePanel extends JPanel {
    private JLabel questionLabel;
    private JButton[] choiceButtons;
    private JLabel scoreLabel;
    private JPanel scoreBoardPanel;
    private JTextArea chatArea;
    private JTextField chatInput;
    private JButton sendButton;
    private JLabel timerLabel;

    private JButton removeWrongButton;
    private JButton skipQuestionButton;

    private boolean usedRemoveWrong = false;
    private boolean usedSkip = false;

    private Map<String, Integer> userScores = new HashMap<>();
    private String currentUser;
    private int currentScore = 0;

    private int timeLeft = 10;
    private Timer timer;

    public QuizGamePanel(String currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout());

        // 전체 왼쪽 영역 (문제 + 선택지 + 아이템 + 점수)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 문제
        questionLabel = new JLabel("문제: 다음 중 포유류가 아닌 것은?");
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(questionLabel);
        leftPanel.add(Box.createVerticalStrut(20));

        // 선택지 버튼
        choiceButtons = new JButton[5];
        for (int i = 0; i < 5; i++) {
            choiceButtons[i] = new JButton("선택지 " + (i + 1));
            choiceButtons[i].setMaximumSize(new Dimension(300, 35));
            choiceButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            int finalI = i;
            choiceButtons[i].addActionListener(e -> handleAnswer(finalI));
            leftPanel.add(choiceButtons[i]);
            leftPanel.add(Box.createVerticalStrut(5));
        }

        leftPanel.add(Box.createVerticalStrut(15));

        // 아이템 버튼
        removeWrongButton = new JButton("❌ 오답 제거 (1회)");
        skipQuestionButton = new JButton("⏭ 문제 스킵 (1회)");
        removeWrongButton.setPreferredSize(new Dimension(130, 30));
        skipQuestionButton.setPreferredSize(new Dimension(130, 30));

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        itemPanel.add(removeWrongButton);
        itemPanel.add(skipQuestionButton);
        leftPanel.add(itemPanel);
        leftPanel.add(Box.createVerticalStrut(20));

        // 점수 + 타이머
        scoreLabel = new JLabel("💠 현재 점수: 0점");
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        timerLabel = new JLabel("⏱ 남은 시간: 10초");
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        bottomPanel.add(scoreLabel);
        bottomPanel.add(timerLabel);
        leftPanel.add(bottomPanel);

        add(leftPanel, BorderLayout.CENTER);

        // 오른쪽 패널 (점수판 + 채팅)
        JPanel rightPanel = new JPanel(new BorderLayout());

        // 실시간 점수판
        scoreBoardPanel = new JPanel();
        scoreBoardPanel.setLayout(new BoxLayout(scoreBoardPanel, BoxLayout.Y_AXIS));
        scoreBoardPanel.setBorder(BorderFactory.createTitledBorder("🏆 실시간 점수판"));
        updateScoreBoard();
        rightPanel.add(new JScrollPane(scoreBoardPanel), BorderLayout.CENTER);

        // 채팅창
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatArea = new JTextArea(10, 20);
        chatArea.setEditable(false);
        chatArea.setBackground(new Color(224, 240, 255));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatInput = new JTextField();
        sendButton = new JButton("⬆");
        sendButton.setBackground(new Color(255, 215, 0));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(chatInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatPanel.setBorder(BorderFactory.createTitledBorder("💬 단체 채팅"));
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);
        rightPanel.add(chatPanel, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.EAST);

        // 채팅 이벤트
        sendButton.addActionListener(e -> sendChat());
        chatInput.addActionListener(e -> sendChat());

        // 아이템 기능
        removeWrongButton.addActionListener(e -> {
            if (!usedRemoveWrong) {
                removeOneWrongAnswer();
                usedRemoveWrong = true;
                removeWrongButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "이미 사용한 아이템이에요!");
            }
        });

        skipQuestionButton.addActionListener(e -> {
            if (!usedSkip) {
                skipQuestion();
                usedSkip = true;
                skipQuestionButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "이미 사용한 아이템이에요!");
            }
        });

        // 테스트 점수 초기화
        userScores.put(currentUser, 0);
        userScores.put("맹구", 20);
        userScores.put("준석", 10);
        updateScoreBoard();

        startTimer();
    }

    private void handleAnswer(int choiceIndex) {
        stopTimer();
        if (choiceIndex == 2) {
            currentScore += 10;
            userScores.put(currentUser, currentScore);
            scoreLabel.setText("💠 현재 점수: " + currentScore + "점");
            updateScoreBoard();
            JOptionPane.showMessageDialog(this, "정답입니다!");
        } else {
            JOptionPane.showMessageDialog(this, "오답입니다!");
        }
        resetQuestion();
    }

    private void updateScoreBoard() {
        scoreBoardPanel.removeAll();
        List<Map.Entry<String, Integer>> sorted = userScores.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .toList();
        for (int i = 0; i < sorted.size(); i++) {
            Map.Entry<String, Integer> entry = sorted.get(i);
            JLabel label = new JLabel((i + 1) + ". " + entry.getKey() + "   " + entry.getValue() + "점");
            label.setFont(new Font("SansSerif", Font.PLAIN, 13));
            scoreBoardPanel.add(label);
        }
        scoreBoardPanel.revalidate();
        scoreBoardPanel.repaint();
    }

    private void sendChat() {
        String msg = chatInput.getText().trim();
        if (!msg.isEmpty()) {
            chatArea.append(currentUser + ": " + msg + "\n");
            chatInput.setText("");
        }
    }

    private void removeOneWrongAnswer() {
        List<Integer> wrongChoices = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i != 2 && choiceButtons[i].isEnabled()) {
                wrongChoices.add(i);
            }
        }
        if (!wrongChoices.isEmpty()) {
            int removeIndex = wrongChoices.get(new Random().nextInt(wrongChoices.size()));
            choiceButtons[removeIndex].setEnabled(false);
        }
    }

    private void skipQuestion() {
        stopTimer();
        questionLabel.setText("문제: 스킵된 다음 문제 예시입니다!");
        for (JButton button : choiceButtons) {
            button.setEnabled(true);
        }
        resetQuestion();
    }

    private void startTimer() {
        timeLeft = 10;
        timerLabel.setText("⏱ 남은 시간: " + timeLeft + "초");
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeLeft--;
                    if (timeLeft >= 0) {
                        timerLabel.setText("⏱ 남은 시간: " + timeLeft + "초");
                    }
                    if (timeLeft <= 0) {
                        stopTimer();
                        JOptionPane.showMessageDialog(QuizGamePanel.this, "시간 초과! 다음 문제로 넘어갑니다.");
                        resetQuestion();
                    }
                });
            }
        }, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) timer.cancel();
    }

    private void resetQuestion() {
        for (JButton button : choiceButtons) {
            button.setEnabled(true);
        }
        startTimer();
    }
}
