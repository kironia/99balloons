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
package io.github.kironia.ninetynineballons.graphics;

import static java.lang.String.valueOf;

import javax.swing.*;

import io.github.kironia.ninetynineballons.logic.GameObjects;
import io.github.kironia.ninetynineballons.logic.Score;

public class GameSummaryFrame {
    private JButton OKButton;
    private JLabel level;
    private JLabel levelScore;
    private JLabel levelHit;
    private JLabel levelMiss;
    private JLabel levelAccuracy;
    private JLabel totalScore;
    private JLabel totalHit;
    private JLabel totalMiss;
    private JLabel totalAccuracy;
    private JPanel gameSummaryPanel;
    /**
     * the game art frame (where the balloons are)
     */
    private final JFrame gameArtFrame;
    /**
     * the game summary frame (de facto dialog)
     */
    private final JDialog gameSummaryFrame;

    public GameSummaryFrame(GameObjects gameObjects, JFrame gameArtFrame) {
        this.gameArtFrame = gameArtFrame;
        gameSummaryFrame = new JDialog(gameArtFrame, "Summary", true);
        gameSummaryFrame.getContentPane().add(getGameSummaryPanel());
        gameSummaryFrame.pack();
        OKButton.addActionListener((e) -> {
            // Progress to the next level after used is done with her score.
            gameSummaryFrame.setVisible(false);
            gameObjects.nextLevel();
        });
    }

    public JPanel getGameSummaryPanel() {
        return gameSummaryPanel;
    }

    public void updateWithScore(Score score) {
        level.setText(valueOf(score.getLevel()));
        levelScore.setText(valueOf(score.getLevelScore()));
        levelHit.setText(valueOf(score.getLevelHit()));
        levelMiss.setText(valueOf(score.getLevelMiss()));
        levelAccuracy.setText(valueOf(score.getLevelAccuracy()));
        totalScore.setText(valueOf(score.getTotalScore()));
        totalHit.setText(valueOf(score.getTotalHit()));
        totalMiss.setText(valueOf(score.getTotalMiss()));
        totalAccuracy.setText(valueOf(score.getTotalAccuracy()));
    }

    /**
     * Shows the frame. Disables the OK button when the game is over.
     *
     * @param gameOver true, if the game is over
     */
    public void displaySummary(boolean gameOver) {

        if (gameOver) {
            OKButton.setText("Game over");
            OKButton.setEnabled(false);
            gameArtFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        gameSummaryFrame.setVisible(true);
    }
}
