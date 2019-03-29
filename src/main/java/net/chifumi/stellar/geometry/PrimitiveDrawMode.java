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

package net.chifumi.stellar.geometry;

import static org.lwjgl.opengl.GL33.*;

enum PrimitiveDrawMode {
    LINE(GL_LINES),
    TRIANGLES(GL_TRIANGLES),
    TRIANGLE_FAN(GL_TRIANGLE_FAN);

    private final int id;

    PrimitiveDrawMode(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
