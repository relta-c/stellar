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

package net.chifumi.stellar.input;

import net.chifumi.stellar.graphic.Display;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Represent keyboard, use this class to get user keyboard input.
 *
 * @author Nattakit Hosapsin
 * @version 1.0.0
 * @since 1.0.0
 */
public class Keyboard {
    private final Map<Key, KeyState> state;
    private final Map<Integer, Key> keySet;

    /**
     * Create a new {@link net.chifumi.stellar.input.Keyboard} object.
     *
     * @param display
     *         window to get input from
     */
    public Keyboard(final Display display) {
        final long window = display.getID();
        glfwMakeContextCurrent(window);
        glfwSetKeyCallback(window, this::keyCallback);
        state = new EnumMap<>(Key.class);
        keySet = new HashMap<>(GLFW_KEY_LAST);
        final Key[] keyValue = Key.values();
        for (final Key key : keyValue) {
            keySet.put(key.getID(), key);
        }
    }

    /**
     * Get key status.
     *
     * @param key
     *         {@link net.chifumi.stellar.input.Key} to check
     *
     * @return {@link net.chifumi.stellar.input.KeyState}
     *
     * @since 1.0.0
     */
    public KeyState getKey(final Key key) {
        return state.get(key);
    }

    private Key keyFromID(final int id) {
        return keySet.get(id);
    }

    @SuppressWarnings("unused")
    private void keyCallback(final long window, final int key, final int scancode, final int action, final int mode) {
        final Key keyEnum = keyFromID(key);
        if (action == GLFW_PRESS) {
            state.put(keyEnum, KeyState.PRESS);
        } else if (action == GLFW_RELEASE) {
            state.put(keyEnum, KeyState.RELEASE);
        }
    }
}
