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

import net.chifumi.stellar.graphics.DrawableObject;
import net.chifumi.stellar.graphics.Primitive;
import net.chifumi.stellar.graphics.TexturedDrawable;
import net.chifumi.stellar.texture.Texture;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;

public class DrawableCharacter extends DrawableObject implements TexturedDrawable {
    private final int id;
    private final CharacterInfo characterInfo;
    private final FontFamily family;
    private int fontSize;
    private Vector2f position;
    private Texture texture;
    private boolean solidFilled;

    DrawableCharacter(final int id, final int fontSize, final FontFamily family) {
        super(new CharacterPrimitive(family.getNormalizeCharacterOffset(family.getCharacter(id)),
                                     family.getNormalizeSize(family.getCharacter(id))));
        position = new Vector2f();
        this.fontSize = fontSize;
        this.id = id;
        this.family = family;
        texture = family.getAtlas();
        characterInfo = family.getCharacter(id);
        updateModelMatrix();
    }

    public int getID() {
        return id;
    }

    public CharacterInfo getCharacterInfo() {
        return characterInfo;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(final Vector2fc position) {
        this.position = (Vector2f) position;
        updateModelMatrix();
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(final int fontSize) {
        this.fontSize = fontSize;
        updateModelMatrix();
    }

    public FontFamily getFamily() {
        return family;
    }

    @Override
    public Primitive getPrimitive() {
        final Vector2d pos = family.getNormalizeCharacterOffset(characterInfo);
        final Vector2d rect = family.getNormalizeSize(characterInfo);
        return new CharacterPrimitive(pos, rect);

    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void setTexture(final Texture texture) {
        this.texture = texture;
    }

    @Override
    public boolean isSolidFilled() {
        return solidFilled;
    }

    @Override
    public void setSolidFilled(final boolean solidFilled) {
        this.solidFilled = solidFilled;
    }

    protected void updateModelMatrix() {
        final Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.translate(new Vector3f(position, 0.0f));
        modelMatrix.scale(new Vector3f(getDrawSize(), 1.0F));
        setModelMatrix(modelMatrix);
    }

    int getXOffset() {
        return characterInfo.getXOffset();
    }

    int getYOffset() {
        return characterInfo.getYOffset();
    }

    int getAdvance() {
        return characterInfo.getAdvance();
    }

    private Vector2f getDrawSize() {
        final float width = (characterInfo.getWidth() / (float) family.getNativeSize()) * fontSize;
        final float height = (characterInfo.getHeight() / (float) family.getNativeSize()) * fontSize;
        return new Vector2f(width, height);
    }
}
