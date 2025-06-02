import java.io.*;
import java.net.Socket;

public class SocketManager {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private Player player;
    private QuizPanel quizPanel;

    public boolean connectToServer(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("[클라이언트] 서버 연결 성공");
            return true;
        } catch (IOException e) {
            System.err.println("[클라이언트] 서버 연결 실패: " + e.getMessage());
            return false;
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
            out.flush();
            System.out.println("[전송됨] " + message);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setQuizPanel(QuizPanel quizPanel) {
        this.quizPanel = quizPanel;
    }

    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            System.out.println("[클라이언트] 연결 종료");
        } catch (IOException e) {
            System.err.println("[클라이언트] 연결 종료 실패: " + e.getMessage());
        }
    }

    public void startReceiver() {
        Thread receiverThread = new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("[수신됨] " + message);
                    handleMessage(message);
                }
            } catch (IOException e) {
                System.err.println("[수신 오류] " + e.getMessage());
            } finally {
                close();
            }
        });

        receiverThread.start();
    }

    private void handleMessage(String message) {
        String[] parts = message.split(":", 3); // COMMAND:TYPE:DATA

        if (parts.length < 3) {
            System.out.println("[오류] 잘못된 메시지 형식: " + message);
            return;
        }

        String type = parts[1];
        String data = parts[2];

        switch (type) {
            case "QUIZ":
                if (quizPanel != null) {
                    String[] quizParts = data.split("\\|");
                    if (quizParts.length >= 5) {
                        quizPanel.setQuestion(quizParts[0]);
                        quizPanel.setOptions(new String[]{quizParts[1], quizParts[2], quizParts[3], quizParts[4]});
                    }
                }
                break;

            case "RESULT":
                System.out.println("[결과] " + data);
                // TODO: QuizPanel에 결과 반영 기능 추가
                break;

            case "SCORE":
                int addedScore = Integer.parseInt(data);
                player.increaseScore(addedScore);
                System.out.println("[점수 추가] +" + addedScore + " → 총점: " + player.getScore());
                break;

            default:
                System.out.println("[알 수 없는 명령] " + type);
        }
    }
}