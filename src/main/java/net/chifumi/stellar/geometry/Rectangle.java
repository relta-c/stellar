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

import org.joml.Vector2f;
import org.joml.Vector2fc;

public class Rectangle {
    private Vector2f position;
    private Vector2f size;

    public Rectangle(final Vector2fc position, final Vector2fc size) {
        this.position = (Vector2f) position;
        this.size = (Vector2f) size;
    }

    public Rectangle(final float x, final float y, final float width, final float height) {
        position = new Vector2f(x, y);
        size = new Vector2f(width, height);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(final Vector2fc position) {
        this.position = (Vector2f) position;
    }

    public void setPosition(final float x, final float y) {
        position = new Vector2f(x, y);
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(final Vector2fc size) {
        this.size = (Vector2f) size;
    }

    public void setSize(final float width, final float height) {
        size = new Vector2f(width, height);
    }
}
