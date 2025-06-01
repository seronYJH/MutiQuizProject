import client.gui.ShopPanel;

import javax.swing.*;

public class TestShopPanel {
    private static int userCoins = 120; // 예시: 보유 코인 120

    public static void main(String[] args) {
        JFrame frame = new JFrame("상점");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        ShopPanel panel = new ShopPanel(userCoins);

        panel.setBuyRemoveWrongListener(e -> tryBuy(panel, "오답 제거권", 50));
        panel.setBuySkipListener(e -> tryBuy(panel, "문제 스킵권", 70));
        panel.setBuyRingListener(e -> tryBuy(panel, "커플 반지", 100));
        panel.setBuyNicknameListener(e -> tryBuy(panel, "닉네임 변경권", 80));

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void tryBuy(ShopPanel panel, String itemName, int cost) {
        if (userCoins >= cost) {
            userCoins -= cost;
            panel.setCoinDisplay(userCoins);
            JOptionPane.showMessageDialog(null, itemName + "을(를) 구매했습니다!");
        } else {
            JOptionPane.showMessageDialog(null, "코인이 부족합니다!");
        }
    }
}
