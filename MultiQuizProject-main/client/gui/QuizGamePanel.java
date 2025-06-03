package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;
import client.test.SocketManager;


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

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private Question currentQuestion;

    public QuizGamePanel(String currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout());

        // 퀴즈 문제 리스트 초기화
        questionList = new ArrayList<>();
        questionList.add(new Question("다음 중 포유류가 아닌 것은?", new String[]{"고래", "박쥐", "참새", "사자", "호랑이"}, 2));
        questionList.add(new Question("한국의 수도는?", new String[]{"서울", "부산", "인천", "대전", "광주"}, 0));
        questionList.add(new Question("1 + 1 = ?", new String[]{"1", "2", "3", "4", "5"}, 1));
        questionList.add(new Question("바다와 관련 없는 것은?", new String[]{"조개", "산호", "고래", "호수", "문어"}, 3));
        questionList.add(new Question("빨간색은?", new String[]{"Red", "Blue", "Green", "Yellow", "Purple"}, 0));
        questionList.add(new Question("사과는 어떤 과일?", new String[]{"단맛", "짠맛", "신맛", "쓴맛", "매운맛"}, 0));
        questionList.add(new Question("3 x 3 = ?", new String[]{"6", "7", "8", "9", "10"}, 3));
        questionList.add(new Question("한국의 전통 의상은?", new String[]{"기모노", "치파오", "한복", "아오자이", "사리"}, 2));
        questionList.add(new Question("동물원에 없는 것은?", new String[]{"사자", "호랑이", "기린", "코끼리", "고등어"}, 4));
        questionList.add(new Question("겨울에 어울리는 음식?", new String[]{"빙수", "아이스크림", "삼계탕", "팥죽", "냉면"}, 3));

        currentQuestionIndex = 0;

        // 전체 왼쪽 영역 (문제 + 선택지 + 아이템 + 점수)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 문제
        questionLabel = new JLabel("문제: ");
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

        userScores.put(currentUser, 0);
        updateScoreBoard();

        // ✅ 모든 컴포넌트 초기화 이후에 호출
        resetQuestion();
    }

    void handleAnswer(int choiceIndex) {
        stopTimer();

        if (choiceIndex == currentQuestion.getCorrectIndex()) {
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

    public void setQuestion(String questionText) {
        questionLabel.setText("문제: " + questionText);
    }

    public void setOptions(String[] options) {
        for (int i = 0; i < choiceButtons.length && i < options.length; i++) {
            choiceButtons[i].setText(options[i]);
            choiceButtons[i].setEnabled(true);
        }
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
            if (i != currentQuestion.getCorrectIndex() && choiceButtons[i].isEnabled()) {
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
        JOptionPane.showMessageDialog(this, "문제를 스킵합니다!");
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

    void resetQuestion() {
        System.out.println("✅ resetQuestion 호출됨"); // ← 추가
        // 서버에 문제 요청 (멀티모드일 경우)
        if (socketManager != null) {
            socketManager.sendMessage("COMMAND:QUIZ:REQUEST");
        }

        if (currentQuestionIndex >= questionList.size()) {
            JOptionPane.showMessageDialog(this, "🎉 모든 문제를 완료했습니다!");
            for (JButton button : choiceButtons) button.setEnabled(false);
            return;
        }

        currentQuestion = questionList.get(currentQuestionIndex++);
        setQuestion(currentQuestion.getQuestionText());
        setOptions(currentQuestion.getChoices());
        startTimer();
    }



    public class Question {
        private String questionText;
        private String[] choices;
        private int correctIndex;

        public Question(String questionText, String[] choices, int correctIndex) {
            this.questionText = questionText;
            this.choices = choices;
            this.correctIndex = correctIndex;
        }

        public String getQuestionText() { return questionText; }
        public String[] getChoices() { return choices; }
        public int getCorrectIndex() { return correctIndex; }
    }

    private SocketManager socketManager;

    public void setSocketManager(SocketManager socketManager) {
        this.socketManager = socketManager;
    }

}
