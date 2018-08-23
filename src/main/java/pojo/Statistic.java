package pojo;

public class Statistic {
    private int userId;
    private String name;
    private int totalGames;
    private int totalAttempts;
    private Double rating;

    public Statistic(int userId, String name, int totalGames, int totalAttempts, Double rating) {
        this.userId = userId;
        this.name = name;
        this.totalGames = totalGames;
        this.totalAttempts = totalAttempts;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public Double getRating() {
        return rating;
    }
}
