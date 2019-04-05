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
import org.joml.Vector3f;

/**
 * @author Nattakit Hosapsin
 * @version 1.0.1
 * @since 1.0.0
 */
public class Text {
    private static final float MAX_RGB = 255.0f;
    private int size;
    private float transparency;
    private boolean visible;
    private String text;
    private Vector2f position;
    private Vector3f color;
    private FontFamily family;

    public Text(final String text, final int size, final FontFamily family) {
        this.size = size;
        this.text = text;
        this.family = family;
        visible = true;
        transparency = 100;
        position = new Vector2f();
        color = new Vector3f(MAX_RGB, MAX_RGB, MAX_RGB);
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
        final DrawableCharacter character = new DrawableCharacter(text.charAt(index), size, family); // TODO : Use Map
        character.setColor(color.x, color.y, color.z);
        character.setTransparency(transparency);
        character.setVisible(visible);
        return character;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(final float red, final float green, final float blue) {
        color = new Vector3f(red, green, blue);
    }

    /**
     * Get text transparency in percent
     *
     * @return transparency in percent
     *
     * @since 1.0.1
     */
    public float getTransparency() {
        return transparency;
    }

    /**
     * Set text transparency in percent
     *
     * @since 1.0.1
     */
    public void setTransparency(final float transparency) {
        this.transparency = transparency;
    }

    /**
     * Get object visibility.
     *
     * @return visibility of object
     *
     * @since 1.0.1
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set object visibility.
     *
     * @since 1.0.1
     **/
    public void setVisible(final boolean visible) {
        this.visible = visible;
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
