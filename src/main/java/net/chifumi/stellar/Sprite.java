package net.chifumi.stellar;

import org.joml.*;

public class Sprite {
    private Vector2f position;
    private Vector2f size;
    private Quaternionf rotation;
    private Vector3f color;
    private Texture texture;
    private Matrix4f model;

    private static final float HALF_LEN = 0.5f;

    public Sprite(final Texture texture) {
        position = new Vector2f();
        size = new Vector2f(texture.getWidth(), texture.getHeight());
        rotation = new Quaternionf();
        color = new Vector3f(1.0f, 1.0f, 1.0f);
        this.texture = texture;
        updateModelMatrix();
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(final float x, final float y) {
        position = new Vector2f(x, y);
        updateModelMatrix();
    }

    public void setPosition(final Vector2f position) {
        this.position = position;
        updateModelMatrix();
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(final float width, final float height) {
        size = new Vector2f(width, height);
        updateModelMatrix();
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public float getEulerRotation() {
        return rotation.angle();
    }

    public void setRotation(final Quaternionf rotation) {
        this.rotation = rotation;
        updateModelMatrix();
    }

    public void setEulerRotation(final float angle) {
        rotation = new Quaternionf().fromAxisAngleDeg(new Vector3f(0.0f, 0.0f, 1.0f), angle);
        updateModelMatrix();
    }

    public void setSize(final Vector2f size) {
        updateModelMatrix();
        this.size = size;
    }

    @SuppressWarnings("WeakerAccess")
    public Vector3f getColor() {
        return color;
    }

    public void setColor(final float red, final float green, final float blue) {
        color = new Vector3f(red, green, blue);
    }

    public void setColor(final Vector3f color) {
        this.color = color;
    }

    Matrix4f getModel() {
        return model;
    }

    @SuppressWarnings("WeakerAccess")
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(final Texture texture) {
        this.texture = texture;
    }

    private void updateModelMatrix() {
        model = new Matrix4f();
        model = model.translate(new Vector3f(position, 0.0f));
        model = model.translate(new Vector3f(HALF_LEN * size.x(), HALF_LEN * size.y(), 0.0F));
        model = model.rotate(rotation);
        model = model.translate(new Vector3f(-HALF_LEN * size.x(), -HALF_LEN * size.y(), 0.0F));
        model.scale(new Vector3f(size, 1.0F));
    }
}
