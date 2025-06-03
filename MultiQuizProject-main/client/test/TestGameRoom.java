import client.gui.GameRoomPanel;
import client.gui.WaitingRoomPanel;

import javax.swing.*;
import java.util.Arrays;

public class TestGameRoom {
    public static void main(String[] args) {
        JFrame frame = new JFrame("게임방 테스트");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // ✅ 임시 대기실 패널 생성
        WaitingRoomPanel dummyWaitingRoom = new WaitingRoomPanel();
        dummyWaitingRoom.setParentFrame(frame);  // 혹시 내부에서 사용하는 경우 대비

        // ✅ GameRoomPanel 생성자에 맞게 수정
        GameRoomPanel panel = new GameRoomPanel(
                "빈이네 퀴즈방",
                Arrays.asList("유빈", "맹구", "도도"),
                true,
                frame,
                "multi" // 수정된 부분: String 타입으로 mode 전달
        );

        frame.add(panel);
        frame.setVisible(true);

        // 테스트용 채팅 추가
        panel.appendChat("도도: 준비됐어?", false);
        panel.appendChat("유빈: ㄱㄱㄱㄱ", true);
    }
}
