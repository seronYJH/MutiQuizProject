package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Collections;


public class ResultPanel extends JPanel {

    private DefaultListModel<String> resultModel;
    private JList<String> resultList;
    private JButton backButton;

    public ResultPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("🎉 게임 결과", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        resultModel = new DefaultListModel<>();
        resultList = new JList<>(resultModel);
        resultList.setFont(new Font("SansSerif", Font.PLAIN, 16));
        resultList.setFixedCellHeight(30);

        JScrollPane scrollPane = new JScrollPane(resultList);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("🏠 대기방으로 돌아가기");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(backButton, BorderLayout.SOUTH);
    }

    // 점수 목록 받아서 출력
    public void setResults(Map<String, Integer> scoreMap) {
        resultModel.clear();

        // 점수 기준 정렬
        List<Map.Entry<String, Integer>> list = new ArrayList<>(scoreMap.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());

        int rank = 1;
        for (Map.Entry<String, Integer> entry : list) {
            String line = String.format("%2d위  %s  -  %d점", rank++, entry.getKey(), entry.getValue());
            resultModel.addElement(line);
        }
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}
