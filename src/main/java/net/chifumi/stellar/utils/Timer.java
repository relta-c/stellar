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

package net.chifumi.stellar.utils;

import org.lwjgl.glfw.GLFW;

/**
 * Represents timer, can be used to get delta time.
 *
 * @author Nattakit Hosapsin
 * @version 1.0.4
 * @since 1.0.2
 */
public class Timer {
    private boolean started;
    private float startTime;
    private float lastRecordedTime;
    private float stopTime;
    /**
     * Create a new {@link net.chifumi.stellar.utils.Timer}.
     */
    public Timer() {
        started = false;
        startTime = 0;
        lastRecordedTime = 0;
        stopTime = 0;
    }

    @SuppressWarnings("NumericCastThatLosesPrecision")
    public static float getCurrentTime() {
        return (float) GLFW.glfwGetTime();
    }

    public void start() {
        startTime = getCurrentTime();
        lastRecordedTime = getCurrentTime();
        started = true;
    }

    public float getDeltaTime() {
        final float deltaTime;
        if (started) {
            deltaTime = getCurrentTime() - lastRecordedTime;
            lastRecordedTime = getCurrentTime();
        } else {
            throw new IllegalStateException("Timer is not started");
        }

        return deltaTime;
    }

    public float getStartTime() {
        return startTime;
    }

    /**
     * @return recorded time since timer start
     *
     * @since 1.0.4
     */
    public float getRecordedTime() {
        return started ? getCurrentTime() - startTime : stopTime;
    }

    public void reset() {
        lastRecordedTime = getCurrentTime();
    }

    public void stop() {
        startTime = 0;
        lastRecordedTime = 0;
        started = false;
        stopTime = getRecordedTime();
    }

    public boolean isStarted() {
        return started;
    }
}
