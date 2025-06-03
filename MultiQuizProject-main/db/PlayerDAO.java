package db;

import common.Player;

import java.sql.*;

public class PlayerDAO {

    public static boolean isIdDuplicated(String loginId) {
        String sql = "SELECT * FROM users WHERE login_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // 있으면 true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isNicknameDuplicated(String nickname) {
        String sql = "SELECT * FROM users WHERE nickname = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nickname);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean register(String loginId, String password, String nickname) {
        String sql = "INSERT INTO users (login_id, password, nickname) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginId);
            pstmt.setString(2, password);
            pstmt.setString(3, nickname);
            int result = pstmt.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Player login(String loginId, String password) {
        String sql = "SELECT * FROM users WHERE login_id = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginId);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Player(
                        rs.getInt("user_id"),
                        rs.getString("login_id"),
                        rs.getString("nickname"),
                        rs.getInt("score"),
                        rs.getInt("coins"),
                        rs.getObject("couple_id") != null ? rs.getInt("couple_id") : null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findPassword(String loginId, String nickname) {
        String sql = "SELECT password FROM users WHERE login_id = ? AND nickname = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginId);
            pstmt.setString(2, nickname);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
