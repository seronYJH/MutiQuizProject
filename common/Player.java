package common;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;
    private String loginId;
    private String nickname;
    private int score;
    private int coins;
    private Integer coupleId; // 커플이 없을 수도 있음

    public Player(int userId, String loginId, String nickname, int score, int coins, Integer coupleId) {
        this.userId = userId;
        this.loginId = loginId;
        this.nickname = nickname;
        this.score = score;
        this.coins = coins;
        this.coupleId = coupleId;
    }

    // Getter
    public int getUserId() {
        return userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }

    public int getCoins() {
        return coins;
    }

    public Integer getCoupleId() {
        return coupleId;
    }

    // Setter
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setCoupleId(Integer coupleId) {
        this.coupleId = coupleId;
    }
}
