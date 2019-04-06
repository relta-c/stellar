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

import net.chifumi.stellar.math.MutableVector2;
import net.chifumi.stellar.math.MutableVector3;
import net.chifumi.stellar.math.Vector2;
import net.chifumi.stellar.math.Vector3;
import org.joml.Vector2f;

/**
 * @author Nattakit Hosapsin
 * @version 1.0.1
 * @since 1.0.0
 */
public class Text {
    private static final float MAX_RGB = 255.0f;
    private final MutableVector2<Float> position;
    private final MutableVector3<Float> color;
    private int fontSize;
    private float transparency;
    private boolean visible;
    private String text;
    private FontFamily family;

    public Text(final String text, final int fontSize, final FontFamily family) {
        this.fontSize = fontSize;
        this.text = text;
        this.family = family;
        visible = true;
        transparency = 100;
        position = new MutableVector2<>(0.0f, 0.0f);
        color = new MutableVector3<>(MAX_RGB, MAX_RGB, MAX_RGB);
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(final int fontSize) {
        this.fontSize = fontSize;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Vector2<Float> getPosition() {
        return position.toImmutable();
    }

    public void setPosition(final Vector2<Float> position) {
        this.position.set(position);
    }

    public void setPosition(final float x, final float y) {
        position.set(x, y);
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
        final DrawableCharacter character = new DrawableCharacter(text.charAt(index), fontSize,
                                                                  family); // TODO : Use Map
        character.setColor(color);
        character.setTransparency(transparency);
        character.setVisible(visible);
        return character;
    }

    public Vector3<Float> getColor() {
        return color.toImmutable();
    }

    public void setColor(final float red, final float green, final float blue) {
        color.set(red, green, blue);
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

    public Vector2f getSize() {
        float width = 0;
        float height = 0;
        for (int i = 0; i < getLength(); i++) {
            final DrawableCharacter character = getCharacterAt(i);
            width += (character.getAdvance() / (float) family.getNativeSize()) * fontSize;
            final float charHeight = (character.getYOffset() / (float) family.getNativeSize()) * fontSize;
            if (charHeight > height) {
                height = charHeight;
            }
        }
        return new Vector2f(width, height);
    }

    public Vector2f getCursorAt(final int index) {
        final Vector2f cursor = new Vector2f(position.getX(), position.getY());
        for (int i = 0; i <= index; i++) {
            final DrawableCharacter character = getCharacterAt(i);
            if (i == index) {
                cursor.x += (character.getXOffset() / (float) family.getNativeSize()) * fontSize;
                cursor.y += (character.getYOffset() / (float) family.getNativeSize()) * fontSize;
            } else {
                cursor.x += (character.getAdvance() / (float) family.getNativeSize()) * fontSize;
            }
        }
        return cursor;
    }
}
