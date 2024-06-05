/*
 * Copyright (c) 2021 Maciej Matiaszowski
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */
package io.github.kironia.ninetynineballons.logic;

import java.text.NumberFormat;

public class Score {

    /**
     * the total score
     */
    private int totalScore;

    /**
     * the current level
     */
    private int level = 1;

    /**
     * the current level score
     */
    private int levelScore;
    private int levelHit;
    private int levelMiss;
    private int totalHit;
    private int totalMiss;

    public int getTotalScore() {
        return totalScore;
    }

    public int getLevel() {
        return level;
    }

    public int getLevelScore() {
        return levelScore;
    }

    public void miss() {
        levelScore--;
        totalScore--;
        levelMiss++;
        totalMiss++;
    }

    public void hit() {
        levelScore++;
        totalScore++;
        levelHit++;
        totalHit++;
    }

    public void nextLevel() {

        if (!isGameOver()) {
            // advance to the next level
            levelScore = 0;
            level++;
        }

    }

    public boolean isGameOver() {
        return level > 5;
    }

    public int getLevelHit() {
        return levelHit;
    }

    public int getLevelMiss() {
        return levelMiss;
    }

    public int getTotalHit() {
        return totalHit;
    }

    public int getTotalMiss() {
        return totalMiss;
    }

    public String getLevelAccuracy() {
        return percentage(levelHit, levelMiss);
    }

    public String getTotalAccuracy() {
        return percentage(totalHit, totalMiss);
    }

    /**
     * What's the percentage of total number of shots are the hits.
     *
     * @param hits   the hits
     * @param misses the misses
     * @return the percentage value
     */
    private static String percentage(int hits, int misses) {
        NumberFormat formatter = NumberFormat.getPercentInstance();
        formatter.setMaximumFractionDigits(0);
        return formatter.format(((double) hits) / (hits + misses));
    }

}
