package vtc.game.app.vcoin.vtcpay.model;

import java.io.Serializable;

/**
 * Created by ThuyChi on 9/21/2016.
 */
public class HistoryItem implements Serializable {
    private String date;
    private String game;
    private String user;

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String reward;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

}
