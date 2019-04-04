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

public class Track {
    private static final float MAX_VOLUME = 100.0f;
    private final int id;
    private final int buffer;

    public Track(final String path) throws IOException, UnsupportedAudioFileException {
        id = alGenSources();
        alSourcef(id, AL_GAIN, 1);
        alSourcef(id, AL_PITCH, 1);
        alSource3f(id, AL_POSITION, 0, 0, 0);

        buffer = alGenBuffers();
        final WaveFile waveFile = new WaveFile(path);
        alBufferData(buffer, waveFile.getFormat(), waveFile.getData(), waveFile.getSampleRate());
        waveFile.dispose();

        alSourcei(id, AL_BUFFER, buffer);
    }

    public void play() {
        alSourcePlay(id);
    }

    public void pause() {
        alSourcePause(id);
    }

    public void stop() {
        alSourceStop(id);
    }

    public TrackState getState() {
        final int state = alGetSourcei(id, AL_SOURCE_STATE);
        final TrackState trackState;
        switch (state) {
            case AL_INITIAL:
                trackState = TrackState.INITIAL;
                break;
            case AL_PLAYING:
                trackState = TrackState.PLAYING;
                break;
            case AL_PAUSED:
                trackState = TrackState.PAUSED;
                break;
            case AL_STOPPED:
                trackState = TrackState.STOPPED;
                break;
            default:
                throw new IllegalStateException("Invalid track state");
        }
        return trackState;
    }

    public void setVolume(final float volume) {
        float realVolume = volume;
        if (realVolume > MAX_VOLUME) {
            realVolume = MAX_VOLUME;
        } else if (realVolume < 0) {
            realVolume = 0;
        }
        alSourcef(id, AL_GAIN, realVolume / MAX_VOLUME);
    }

    public float getVolume() {
        return alGetSourcef(id, AL_GAIN) * MAX_VOLUME;
    }

    public boolean isLooping() {
        return alGetSourcei(id, AL_LOOPING) == AL_TRUE;
    }

    public void setLooping(final boolean looping) {
        final int setValue = looping ? AL_TRUE : AL_FALSE;
        alSourcei(id, AL_LOOPING, setValue);
    }

    public void delete() {
        alDeleteBuffers(buffer);
        alDeleteSources(id);
    }
}
