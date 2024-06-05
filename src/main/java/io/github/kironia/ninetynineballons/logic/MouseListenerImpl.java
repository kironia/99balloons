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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.github.kironia.ninetynineballons.graphics.GameSummaryFrame;

public class MouseListenerImpl implements MouseListener {
    private static final Logger LOG = Logger.getLogger(MouseListenerImpl.class.getName());

    private final GameObjects gameObjects;

    private final GameSummaryFrame gameSummary;

    /**
     * the panel with the balloons
     */
    private final Container panel;

    public MouseListenerImpl(Container panel, GameObjects gameObjects, GameSummaryFrame gameSummary) {
        this.gameObjects = gameObjects;
        this.gameSummary = gameSummary;
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        try {

            if (gameObjects.popBalloon()) {
                Score score = gameObjects.getScore();
                gameSummary.updateWithScore(score);
                gameSummary.displaySummary(score.isGameOver());
            }

        } catch (AWTException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (panel.equals(e.getSource())) {
            // Use the crosshair cursor for popping up the balloons.
            Cursor cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
            panel.setCursor(cursor);
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // User had left the panel.
        Cursor cursor = Cursor.getDefaultCursor();
        panel.setCursor(cursor);
    }


}
