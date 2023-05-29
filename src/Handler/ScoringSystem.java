/* Name: Group 5
 Member names & IU code:
	Mai Nguyên Hoàng – ITITIU21208
	Nguyễn Minh Duy – ITITIU21186
 Purpose: Chrome Dino game for OOP Lab Project
*/
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
            setScoreUpSound(Applet.newAudioClip(new URL("file", "", "data/scoreUp.wav")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void resetScore() {
        if (getScore() >= getHighScore()) {
            setHighScore(getScore());
        }
        setScore(0);
        setNextMilestone(100);
    }

    public void increaseScore(int score) {
        setScore(getScore() + score);
        if (getScore() >= getNextMilestone()) {
            setNextMilestone(getNextMilestone() + 100);
            if (getScoreUpSound() != null) {
                getScoreUpSound().play();
            }
        }
    }

    // Getters and setters
    
    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    private void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    private int getNextMilestone() {
        return nextMilestone;
    }

    private void setNextMilestone(int nextMilestone) {
        this.nextMilestone = nextMilestone;
    }

    private AudioClip getScoreUpSound() {
        return scoreUpSound;
    }

    private void setScoreUpSound(AudioClip scoreUpSound) {
        this.scoreUpSound = scoreUpSound;
    }
}