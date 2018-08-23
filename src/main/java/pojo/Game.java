package pojo;

import java.util.Date;

public class Game {
    private int userId;
    private Date timeBegin;
    private Date timeTotal;
    private int attempts;

    public Game(int userId, Date timeBegin, Date timeTotal, int attempts) {
        this.userId = userId;
        this.timeBegin = timeBegin;
        this.timeTotal = timeTotal;
        this.attempts = attempts;
    }

    public int getUserId() {
        return userId;
    }

    public Date getTimeBegin() {
        return timeBegin;
    }

    public Date getTimeTotal() {
        return timeTotal;
    }

    public int getAttempts() {
        return attempts;
    }
}
