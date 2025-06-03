package client;

import client.gui.MainFrame;
import common.Player;

import javax.swing.*;

public class ClientMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. 플레이어 생성 (임시 로그인 정보)
            Player player = new Player(1, "닉네임임시", "test1234", 0, 100, 1);

            // 2. 메인 프레임 생성 및 실행
            MainFrame frame = new MainFrame();
            frame.setVisible(true);

            // ✅ MainFrame은 내부적으로 login → register → mainMenu → 대기실 흐름을 이미 처리함
            // 그러므로 외부에서 addPanel()이나 showPanel() 같은 건 **할 필요 없음**

            // 3. (선택) 필요한 경우 Player 정보를 LoginPanel 등으로 전달하도록 구조 확장 필요
        });
    }
}
