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
package io.github.kironia.ninetynineballons;

import io.github.kironia.ninetynineballons.graphics.GameArtFrame;
import io.github.kironia.ninetynineballons.sound.GameSound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class NinetyNineBalloonsGame {

    public static void main(String []args) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        showGameFrame();
        GameSound.playMusicInBackground();
    }

    private static void showGameFrame() {
        GameArtFrame artFrame = new GameArtFrame();
        artFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        artFrame.setBackground(Color.WHITE);
        artFrame.setSize(200, 400);
        artFrame.initGameObjects();
        artFrame.setVisible(true);
    }
}
