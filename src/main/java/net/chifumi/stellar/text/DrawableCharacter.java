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

import net.chifumi.stellar.graphics.Primitive;
import net.chifumi.stellar.graphics.TexturedDrawable;
import net.chifumi.stellar.texture.Texture;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;

public class DrawableCharacter implements TexturedDrawable {
    private int id;
    private int size;
    private Vector2f position;
    private Vector3f color;
    private Matrix4f modelMatrix;
    private CharacterInfo characterInfo;
    private FontFamily family;

    DrawableCharacter(final int id, final int size, final FontFamily family) {
        position = new Vector2f();
        this.size = size;
        this.id = id;
        this.family = family;
        color = new Vector3f(1.0f, 1.0f, 1.0f);
        characterInfo = family.getCharacter(id);
        modelMatrix = new Matrix4f();
        updateModelMatrix();
    }

    public int getID() {
        return id;
    }

    public void setID(final int id) {
        this.id = id;
        characterInfo = family.getCharacter(id);
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

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
        updateModelMatrix();
    }

    public FontFamily getFamily() {
        return family;
    }

    public void setFamily(final FontFamily family) {
        this.family = family;
    }

    @Override
    public Primitive getPrimitive() {
        final Vector2d pos = family.getNormalizeCharacterOffset(characterInfo);
        final Vector2d rect = family.getNormalizeSize(characterInfo);
        return new CharacterPrimitive(pos, rect);

    }

    @Override
    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    @Override
    public void setModelMatrix(final Matrix4fc modelMatrix) {
        this.modelMatrix = (Matrix4f) modelMatrix;
    }

    @Override
    public Vector3f getColor() {
        return color;
    }

    @Override
    public void setColor(final float red, final float green, final float blue) {
        color = new Vector3f(red / RGB_MAX, green / RGB_MAX, blue / RGB_MAX);
    }

    @Override
    public Texture getTexture() {
        return family.getAtlas();
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

    private void updateModelMatrix() {
        modelMatrix = new Matrix4f();
        modelMatrix.translate(new Vector3f(position, 0.0f));
        modelMatrix.scale(new Vector3f(getDrawSize(), 1.0F));
    }

    private Vector2f getDrawSize() {
        final float width = (characterInfo.getWidth() / (float) family.getNativeSize()) * size;
        final float height = (characterInfo.getHeight() / (float) family.getNativeSize()) * size;
        return new Vector2f(width, height);
    }
}
