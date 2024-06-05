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

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.io.IOException;

/**
 * Sound includes MIDI music.
 *
 */
public class BackgroundMusic implements Runnable, AutoCloseable {

    private Sequencer sequencer;

    public BackgroundMusic() throws MidiUnavailableException, InvalidMidiDataException, IOException {
        var midiFile = BackgroundMusic.class.getResource("/99luftballons.mid");

        if (midiFile != null) {
            sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(MidiSystem.getSequence(midiFile));
            sequencer.open();
        }

    }

    /**
     * Close the MidiDevice & free resources
     */
    @Override
    public void close() {

        if (sequencer != null) {
            sequencer.stop();
            sequencer.close();
        }

    }

    @Override
    public void run() {

        if (sequencer != null) {
            sequencer.start();
        }

    }
}
