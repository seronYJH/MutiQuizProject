package client.test;

import client.gui.ResultPanel;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class TestResultPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("결과 화면");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        ResultPanel panel = new ResultPanel();

        // 예시 점수 데이터
        Map<String, Integer> scores = new HashMap<>();
        scores.put("유빈", 80);
        scores.put("맹구", 30);
        scores.put("짱구", 100);
        scores.put("철수", 70);
        scores.put("유리", 95);

        panel.setResults(scores);

        panel.setBackButtonListener(e -> JOptionPane.showMessageDialog(null, "대기방으로 돌아갑니다."));

        frame.add(panel);
        frame.setVisible(true);
    }
}
