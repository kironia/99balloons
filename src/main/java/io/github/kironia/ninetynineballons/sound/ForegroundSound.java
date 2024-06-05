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
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ForegroundSound {
    private static final int EXTERNAL_BUFFER_SIZE = 524288;
    private final byte[] buffer = new byte[EXTERNAL_BUFFER_SIZE];

    public void playBalloonPoppingSound() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        var soundFile = ForegroundSound.class.getResource("/popballoon.wav");

        if (soundFile != null) {
            playBalloonPoppingSound(soundFile);
        }

    }

    private void playBalloonPoppingSound(URL soundFile) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        var audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        var format = audioInputStream.getFormat();
        var dataLine = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format));
        dataLine.open(format);
        dataLine.start();
        var nBytesRead = 0;

        try {

            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(buffer, 0, buffer.length);

                if (nBytesRead >= 0) {
                    dataLine.write(buffer, 0, nBytesRead);
                }

            }

        } finally {
            dataLine.drain();
            dataLine.close();
        }

    }

}