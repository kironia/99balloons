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
package io.github.kironia.ninetynineballons.sound;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Support for the game sound
 */
public class GameSound {
    private static final Logger LOG = Logger.getLogger(GameSound.class.getName());

    public static void playMusicInBackground() throws MidiUnavailableException, InvalidMidiDataException, IOException {
        var music = new BackgroundMusic();
        var musicThread = new Thread(music);
        musicThread.start();
    }

    public static void playBalloonPoppingSound() {

        try {
            var sound = new ForegroundSound();
            sound.playBalloonPoppingSound();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
        }

    }

}
