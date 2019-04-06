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

import net.chifumi.stellar.math.MutableVector2;
import net.chifumi.stellar.math.Vector2;

public class MutableRectangle implements Rectangle {
    private final MutableVector2<Float> position;
    private final MutableVector2<Float> size;

    public MutableRectangle(final Vector2<Float> position, final Vector2<Float> size) {
        this.position = new MutableVector2<>(position);
        this.size = new MutableVector2<>(size);
    }

    public MutableRectangle(final float x, final float y, final float width, final float height) {
        position = new MutableVector2<>(x, y);
        size = new MutableVector2<>(width, height);
    }

    @Override
    public Vector2<Float> getPosition() {
        return position;
    }

    public void setPosition(final Vector2<Float> position) {
        this.position.set(position);
    }

    public void setPosition(final float x, final float y) {
        position.set(x, y);
    }

    @Override
    public Vector2<Float> getSize() {
        return size;
    }

    public void setSize(final Vector2<Float> size) {
        this.size.set(size);
    }

    public void setSize(final float width, final float height) {
        size.set(width, height);
    }
}
