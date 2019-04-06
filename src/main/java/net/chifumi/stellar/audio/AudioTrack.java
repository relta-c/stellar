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

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static org.lwjgl.openal.AL10.*;

/**
 * Represents audio track that can be play.
 *
 * @version 1.0.2
 * @since 1.0.2
 */
public class AudioTrack {
    /**
     * max volume level
     */
    private static final float MAX_VOLUME = 100.0f;
    /**
     * audio source id
     */
    private final int source;
    /**
     * audio buffer id
     */
    private final int buffer;

    /**
     * Create a new {@link net.chifumi.stellar.audio.AudioTrack} and load audio file.
     * <p>{@link net.chifumi.stellar.audio.AudioPlayer} must be created before using this class.</p>
     *
     * @param path
     *         path to audio file
     *
     * @throws IOException
     *         something wrong with IO operation (e.g. file not found)
     * @throws UnsupportedAudioFileException
     *         file format is not wav
     * @since 1.0.2
     */
    public AudioTrack(final String path) throws IOException, UnsupportedAudioFileException {
        source = alGenSources();
        alSourcef(source, AL_GAIN, 1.0f);
        alSourcef(source, AL_PITCH, 1.0f);
        alSource3f(source, AL_POSITION, 0.0f, 0.0f, 0.0f);

        buffer = alGenBuffers();
        final WaveFile waveFile = new WaveFile(path);
        alBufferData(buffer, waveFile.getFormat(), waveFile.getData(), waveFile.getSampleRate());
        waveFile.dispose();

        alSourcei(source, AL_BUFFER, buffer);
    }

    /**
     * Play track.
     * <p>If already playing then play again from start.</p>
     *
     * @since 1.0.2
     */
    public void play() {
        alSourcePlay(source);
    }

    /**
     * Pause track.
     * <p>Can be resume with {@link AudioTrack#play()}.</p>
     *
     * @since 1.0.2
     */
    public void pause() {
        alSourcePause(source);
    }

    /**
     * Stop playing track.
     *
     * @since 1.0.2
     */
    public void stop() {
        alSourceStop(source);
    }

    /**
     * Get current track status.
     *
     * @return track status
     *
     * @since 1.0.2
     */
    public TrackStatus getState() {
        final int state = alGetSourcei(source, AL_SOURCE_STATE);
        final TrackStatus trackStatus;
        switch (state) {
            case AL_INITIAL:
                trackStatus = TrackStatus.INITIAL;
                break;
            case AL_PLAYING:
                trackStatus = TrackStatus.PLAYING;
                break;
            case AL_PAUSED:
                trackStatus = TrackStatus.PAUSED;
                break;
            case AL_STOPPED:
                trackStatus = TrackStatus.STOPPED;
                break;
            default:
                throw new IllegalStateException("Invalid track state");
        }
        return trackStatus;
    }

    /**
     * Get track volume. (0 - {@value #MAX_VOLUME})
     *
     * @return track volume
     *
     * @since 1.0.2
     */
    public float getVolume() {
        return alGetSourcef(source, AL_GAIN) * MAX_VOLUME;
    }

    /**
     * Set track volume.
     *
     * @param volume
     *         track volume (0 - {@value #MAX_VOLUME})
     *
     * @since 1.0.2
     */
    public void setVolume(final float volume) {
        float realVolume = volume;
        if (realVolume > MAX_VOLUME) {
            realVolume = MAX_VOLUME;
        } else if (realVolume < 0) {
            realVolume = 0;
        }
        alSourcef(source, AL_GAIN, realVolume / MAX_VOLUME);
    }

    /**
     * Check if track is looping.
     *
     * @return looping
     *
     * @since 1.0.2
     */
    public boolean isLooping() {
        return alGetSourcei(source, AL_LOOPING) == AL_TRUE;
    }

    /**
     * Set looping mode.
     *
     * @param looping
     *         looping
     */
    public void setLooping(final boolean looping) {
        final int value = looping ? AL_TRUE : AL_FALSE;
        alSourcei(source, AL_LOOPING, value);
    }

    /**
     * Delete track and clear free memory.
     *
     * @since 1.0.2
     */
    public void delete() {
        alDeleteBuffers(buffer);
        alDeleteSources(source);
    }
}
