package dataStore;

public class Attempt {
    private int number;
    private int guess;
    private int guessWithoutPosition;

    public Attempt(int number, int guess, int guessWithoutPosition) {
        this.number = number;
        this.guess = guess;
        this.guessWithoutPosition = guessWithoutPosition;
    }

    public int getNumber() {
        return number;
    }

    public int getGuess() {
        return guess;
    }

    public int getGuessWithoutPosition() {
        return guessWithoutPosition;
    }
}
