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

import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.github.kironia.ninetynineballons.graphics.BalloonDrawing;
import io.github.kironia.ninetynineballons.graphics.BalloonState;
import io.github.kironia.ninetynineballons.sound.GameSound;

public class GameObjects {
    private static final Logger LOG = Logger.getLogger("GameObjects");
    /**
     * the total number of the balloons per single game level
     */
    public static final int NUMBER_OF_BALLOONS = 99;

    /**
     * the top margin before the first balloon
     */
    public static final int MARGIN_TOP = 150;
    /**
     * the height of the panel
     */
    private final int height;
    /**
     * the width of the panel
     */
    private final int width;
    /**
     * the colors of the balloons on screen
     */
    private final List<Color> colors = Arrays.asList(Color.LIGHT_GRAY,
            Color.GRAY,
            Color.DARK_GRAY,
            Color.BLACK,
            Color.RED,
            Color.PINK,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.MAGENTA,
            Color.CYAN,
            Color.BLUE);

    private final List<BalloonDrawing> balloons = new ArrayList<>();

    /**
     * the game score
     */
    private final Score score = new Score();

    /**
     * the space for a single balloon
     */
    private static final int Y_WIDTH = 50;

    public GameObjects(int width, int height) {
        this.width = width;
        this.height = height;
        fillBalloons();
    }

    private void fillBalloons() {
        // shuffle the colors so they can vary between games
        Collections.shuffle(colors);
        // fill all the balloons
        Random random = new SecureRandom();
        Iterator<Color> colorsIterator = colors.iterator();

        for (int y = 0; y < NUMBER_OF_BALLOONS * Y_WIDTH; y += Y_WIDTH) {
            // the balloon must fit by width
            int randomX = random.nextInt(width - 60) + 20;
            // top margin is 150

            if (!colorsIterator.hasNext()) {
                colorsIterator = colors.iterator();
            }

            Color currentColor = colorsIterator.next();
            balloons.add(new BalloonDrawing(randomX, y + MARGIN_TOP, currentColor, height));
        }

    }

    /**
     * Gets the popped balloon color. Alternatively, it returns the background color
     * when no balloon is popped.
     *
     * @return the color
     * @throws AWTException thrown on robot creation error
     */
    private static Color getPoppedBalloonColor() throws AWTException {
        // Use the current mouse coordinates from the MouseInfo.
        Robot robot = new Robot();
        PointerInfo pi = MouseInfo.getPointerInfo();
        Point p = pi.getLocation();
        return robot.getPixelColor(p.x, p.y);
    }

    /**
     * Pops the balloon and checks whether the level is done.
     *
     * @return true, if the level is done
     * @throws AWTException thrown on robot creation error
     */
    public boolean popBalloon() throws AWTException {
        Color poppedColor = getPoppedBalloonColor();
        balloons.stream()
                .filter(x -> x.isHit(poppedColor))
                .findFirst()
                .ifPresentOrElse(this::popBalloon, score::miss);
        // Not popping any balloon decreases the total score.
        LOG.log(Level.FINEST, "level score: " + score.getLevelScore());
        return balloons.stream()
                .noneMatch(BalloonDrawing::isPlayable);
    }

    /**
     * Makes the balloon pop sound and set popped state.
     */
    private void popBalloon(BalloonDrawing balloon) {
        new Thread(GameSound::playBalloonPoppingSound).start();
        balloon.setState(BalloonState.POPPED);
        score.hit();
    }

    public void nextLevel() {
        score.nextLevel();
        LOG.log(Level.FINEST, "total score: " + score.getTotalScore());
        // FIXME the last balloon remains on screen; clear the graphics

        if (!score.isGameOver()) {
            // advance to the next level
            LOG.log(Level.FINEST, "Welcome to level " + score.getLevel() + ".");
            // Reset balloons.
            balloons.clear();
            fillBalloons();
        }

    }

    public void paintAll(Graphics g) {
        balloons.forEach(x -> x.paint(g));
    }

    public void tick() {
        // fly each balloon
        balloons.stream()
                .filter(x -> x.getState() == BalloonState.HOLD)
                .forEach(x -> x.setState(BalloonState.FLY));
    }

    public Score getScore() {
        return score;
    }
}
