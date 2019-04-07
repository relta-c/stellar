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

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.*;

/**
 * Represents audio player.
 * <p>This class have to be create before loading or playing any audio.</p>
 *
 * @author Nattakit Hosapin
 * @version 1.0.2
 * @since 1.0.2
 */
public class AudioPlayer {
    /**
     * audio device id
     */
    private final long device;

    /**
     * Create a new {@link net.chifumi.stellar.audio.AudioPlayer}.
     *
     * @since 1.0.2
     */
    public AudioPlayer() {
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        final ALCCapabilities deviceCaps = ALC.createCapabilities(device);

        final long context = ALC10.alcCreateContext(device, (IntBuffer) null);
        ALC10.alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);

        alListener3f(AL_POSITION, 0, 0, 0);
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }

    /**
     * Terminate audio player and close audio device.
     * <p>This method should be called after all audio operation have finished.</p>
     *
     * @since 1.0.2
     */
    public void terminate() {
        ALC10.alcCloseDevice(device);
    }
}
