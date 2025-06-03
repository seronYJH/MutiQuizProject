package client.test;

import client.gui.CoupleRequestFrame;

public class TestCoupleRequestFrame {
    public static void main(String[] args) {
        // 테스트용 프레임 실행
        new CoupleRequestFrame("맹구", new CoupleRequestFrame.CoupleResponseListener() {
            @Override
            public void onAccepted() {
                System.out.println("✅ 수락: 커플 수락 메시지를 서버에 보내야 함");
            }

            @Override
            public void onRejected() {
                System.out.println("❌ 거절: 커플 거절 메시지를 서버에 보내야 함");
            }
        }).setVisible(true);
    }
}
