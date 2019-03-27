package net.chifumi.stellar;

import org.joml.Matrix4f;
import org.joml.Vector2fc;
import org.joml.Vector3f;

public class Line implements Drawable {
    private Vector3f color; // TODO : Make color easier to use, may be something like (byte, byte, byte)
    private Texture texture;
    private final Matrix4f model;
    private final Primitive primitive;
    private final boolean haveTexture;

    public Line(final Vector2fc pointA, final Vector2fc pointB) {
        color = new Vector3f(1.0f, 1.0f, 1.0f);
        texture = new Texture();
        primitive = new LinePrimitive(pointA, pointB);
        haveTexture = false;
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

    public boolean isHaveTexture() {
        return haveTexture;
    }

    public Matrix4f getModel() {
        return model;
    }

    public Primitive getPrimitive() {
        return primitive;
    }
}
