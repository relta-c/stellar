/*
 * Copyright (C) 2019 Nattakit Hosapsin <delta@chifumi.net>
 *
 * This file is part of Stellar
 * Stellar is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either
 *  version 3 of the License, or (at your option) any later version.
 *
 *  Stellar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Stellar.  If not, see <https://www.gnu.org/licenses/lgpl.html>.
 *
 */

package net.chifumi.stellar.audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Represents wav file data.
 *
 * @author Nattakit Hosapin
 * @version 1.0.0
 * @apiNote
 * @since 1.0.0
 */
final class WaveFile {
    private final int format;
    private final int sampleRate;
    private final int totalBytes;
    private final ByteBuffer data;
    private final AudioInputStream audioStream;
    private final byte[] dataArray;

    /**
     * Create a {@link net.chifumi.stellar.audio.WaveFile} object and load wav file.
     *
     * @param path
     *         path to wav file
     *
     * @throws IOException
     *         something wrong with IO operation (e.g. file not found)
     * @throws UnsupportedAudioFileException
     *         file format is not wav
     */
    @SuppressWarnings({"NumericCastThatLosesPrecision", "OverlyBroadThrowsClause"})
    WaveFile(final CharSequence path) throws IOException, UnsupportedAudioFileException {
        final InputStream stream = new FileInputStream((String) path);
        try (InputStream bufferedStream = new BufferedInputStream(stream)) {
            audioStream = AudioSystem.getAudioInputStream(bufferedStream);
            final AudioFormat audioFormat = audioStream.getFormat();
            format = getOpenAlFormat(audioFormat.getChannels(), audioFormat.getSampleSizeInBits());
            sampleRate = (int) audioFormat.getSampleRate();
            final int bytesPerFrame = audioFormat.getFrameSize();
            totalBytes = (int) (audioStream.getFrameLength() * bytesPerFrame);
            data = BufferUtils.createByteBuffer(totalBytes);
            dataArray = new byte[totalBytes];
            loadData();
        }
    }

    /**
     * Dispose data stream and close stream.
     */
    void dispose() {
        try {
            audioStream.close();
            getData().clear();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get openAL file format.
     *
     * @return openAL file format
     */
    int getFormat() {
        return format;
    }

    /**
     * Get sample rate.
     *
     * @return sample rate
     */
    int getSampleRate() {
        return sampleRate;
    }

    /**
     * Get file data.
     *
     * @return wav file data
     */
    ByteBuffer getData() {
        return data;
    }

    /**
     * Get openAL audio format from file info.
     *
     * @param channels
     *         number of audio channels
     * @param bitsPerSample
     *         bit per sample
     *
     * @return openAL audio format
     */
    private static int getOpenAlFormat(final int channels, final int bitsPerSample) {
        if (channels == 1) {
            return bitsPerSample == 8 ? AL10.AL_FORMAT_MONO8 : AL10.AL_FORMAT_MONO16;
        } else {
            return bitsPerSample == 8 ? AL10.AL_FORMAT_STEREO8 : AL10.AL_FORMAT_STEREO16;
        }
    }

    /**
     * Load file data.
     *
     * @throws IOException
     *         something wrong with IO operation (e.g. file not found)
     */
    private void loadData() throws IOException {
        final int bytesRead = audioStream.read(dataArray, 0, totalBytes);
        getData().clear();
        getData().put(dataArray, 0, bytesRead);
        getData().flip();
    }
}

