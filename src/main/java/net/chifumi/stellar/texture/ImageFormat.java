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

import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL21.GL_SRGB;
import static org.lwjgl.opengl.GL21.GL_SRGB_ALPHA;
import static org.lwjgl.opengl.GL33.GL_RGBA;

public enum ImageFormat {
    RGB(GL_RGB),
    RGBA(GL_RGBA),
    SRGB(GL_SRGB),
    SRGB_ALPHA(GL_SRGB_ALPHA);

    private final int id;

    ImageFormat(final int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
