//    Copyright (C) 2019 Nattakit Hosapsin <delta@chifumi.net>
//
//    This file is part of Stellar
//    Stellar is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as
//    published by the Free Software Foundation, either
//    version 3 of the License, or (at your option) any later version.
//
//    Stellar is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with Stellar.  If not, see <https://www.gnu.org/licenses/lgpl.html>.

package net.chifumi.stellar;

import org.joml.Matrix4f;
import org.joml.Vector2fc;
import org.joml.Vector3f;

public class Line implements Drawable {
    private Vector3f color; // TODO : Make color easier to use, may be something like (byte, byte, byte)
    private Texture texture;
    private final Matrix4f model;
    private final Primitive primitive;

    public Line(final Vector2fc pointA, final Vector2fc pointB) {
        color = new Vector3f(1.0f, 1.0f, 1.0f);
        texture = new Texture();
        primitive = new LinePrimitive(pointA, pointB);
        model = new Matrix4f().identity();
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(final Vector3f color) {
        this.color = color;
    }

    public void setColor(final float red, final float green, final float blue) {
        color = new Vector3f(red, green, blue);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(final Texture texture) {
        this.texture = texture;
    }

    public Matrix4f getModel() {
        return model;
    }

    public Primitive getPrimitive() {
        return primitive;
    }
}