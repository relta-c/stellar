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

package net.chifumi.stellar.texture;

import static org.lwjgl.opengl.GL33.GL_LINEAR;
import static org.lwjgl.opengl.GL33.GL_NEAREST;

/**
 * Texture filtering mode.
 *
 * @author Nattakit Hosapsin
 * @version 1.0.0
 * @since 1.0.0
 */
public enum FilteringMode {
    /**
     * nearest mode will make texture more crisp, this is default
     * <p>use this for pixelated texture</p>
     */
    NEAREST(GL_NEAREST),
    /**
     * linear mode will make texture more smooth
     * <p>use this for realistic texture</p>
     */
    LINEAR(GL_LINEAR);

    private final int id;

    FilteringMode(final int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
