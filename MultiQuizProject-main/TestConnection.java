import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/quizdb";
        String user = "root";
        String password = "bini11!!";  // 너가 설정한 비번으로 바꿔줘

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ MySQL 연결 성공!");
        } catch (Exception e) {
            System.out.println("❌ 연결 실패: " + e.getMessage());
        }
    }
}
