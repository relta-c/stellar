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

package net.chifumi.stellar.text;

import org.joml.Vector2f;
import org.joml.Vector2fc;

public class Text {
    private int size;
    private String text;
    private Vector2f position;
    private FontFamily family;

    public Text(final String text, final int size, final FontFamily family) {
        this.size = size;
        this.text = text;
        this.family = family;
        position = new Vector2f();
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(final Vector2fc position) {
        this.position = (Vector2f) position;
    }

    public FontFamily getFamily() {
        return family;
    }

    public void setFamily(final FontFamily family) {
        this.family = family;
    }

    public int getLength() {
        return text.length();
    }

    public DrawableCharacter getCharacterAt(final int index) {
        return new DrawableCharacter(text.charAt(index), size, family);
    }

    public Vector2f getCursorAt(final int index) {
        final Vector2f cursor = new Vector2f(position);
        for (int i = 0; i <= index; i++) {
            final DrawableCharacter character = getCharacterAt(i);
            if (i == index) {
                cursor.x += (character.getXOffset() / (float) family.getNativeSize()) * size;
                cursor.y += (character.getYOffset() / (float) family.getNativeSize()) * size;
            } else {
                cursor.x += (character.getAdvance() / (float) family.getNativeSize()) * size;
            }
        }
        return cursor;
    }
}
