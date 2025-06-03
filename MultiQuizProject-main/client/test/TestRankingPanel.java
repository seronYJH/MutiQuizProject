import client.gui.RankingPanel;

import javax.swing.*;
import java.util.Vector;

public class TestRankingPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("랭킹");
        frame.setSize(500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        RankingPanel panel = new RankingPanel();

        // 랭킹 예시
        Vector<String> ranks = new Vector<>();
        ranks.add("빈이 1500");
        ranks.add("맹구 1480");
        ranks.add("짱구 1320");
        ranks.add("철수 1270");
        panel.updateRankingList(ranks);

        // 접속 중 유저 예시
        Vector<String> online = new Vector<>();
        online.add("빈이");
        online.add("유리");
        panel.updateOnlineUsers(online);

        panel.setSearchListener(e -> {
            String keyword = panel.getSearchText();
            JOptionPane.showMessageDialog(null, "‘" + keyword + "’ 유저 검색!");
        });

        panel.setUserClickListener(e -> {
            String selected = panel.getSelectedUser();
            if (selected != null) {
                JOptionPane.showMessageDialog(null, selected + "님의 정보를 확인합니다.");
            }
        });

        panel.setBackListener(e -> System.out.println("메인으로 돌아가기"));

        frame.add(panel);
        frame.setVisible(true);
    }
}
