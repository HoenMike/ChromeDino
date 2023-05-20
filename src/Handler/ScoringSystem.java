package Handler;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class ScoringSystem {
    private int score = 0;
    private int highScore = 0;
    private int nextMilestone = 100;

    private AudioClip scoreUpSound;

    public ScoringSystem() {
        try {
            scoreUpSound = Applet.newAudioClip(new URL("file", "", "data/scoreUp.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getNextMilestone() {
        return nextMilestone;
    }

    public void setNextMilestone(int nextMilestone) {
        this.nextMilestone = nextMilestone;
    }

    public void resetScore() {
        setHighScore(getScore());
        setScore(0);
        setNextMilestone(100);
    }

    public void increaseScore(int score) {
        setScore(getScore() + score);
        if (getScore() >= getNextMilestone()) {
            setNextMilestone(getNextMilestone() + 100);
            if (scoreUpSound != null) {
                scoreUpSound.play();
            }
        }
    }
}
