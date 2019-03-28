package net.chifumi.stellar;

import org.joml.Matrix4f;
import org.joml.Vector2fc;
import org.joml.Vector3f;

public class Circle implements Drawable {
    private Vector2fc origin;
    private float radius;
    private int side;
    private Vector3f color; // TODO : Make color easier to use, may be something like (byte, byte, byte)
    private Matrix4f model;
    private Primitive primitive;

    public Circle(final Vector2fc origin, final float radius, final int side) {
        this.origin = origin;
        this.radius = radius;
        this.side = side;
        color = new Vector3f(1.0f, 1.0f, 1.0f);
        model = new Matrix4f().identity();
        updatePrimitive();
    }

    public Vector2fc getOrigin() {
        return origin;
    }

    public void setOrigin(final Vector2fc origin) {
        this.origin = origin;
        updatePrimitive();
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(final float radius) {
        this.radius = radius;
        updatePrimitive();
    }

    public int getSide() {
        return side;
    }

    public void setSide(final int side) {
        this.side = side;
        updatePrimitive();
    }

    public void setColor(final float red, final float green, final float blue) {
        color = new Vector3f(red, green, blue);
    }

    @Override
    public Primitive getPrimitive() {
        return primitive;
    }

    @Override
    public Matrix4f getModel() {
        return model;
    }

    @Override
    public Vector3f getColor() {
        return color;
    }

    public void setColor(final Vector3f color) {
        this.color = color;
    }

    public void setModel(final Matrix4f model) {
        this.model = model;
    }

    private void updatePrimitive() {
        primitive = new CirclePrimitive(origin, radius, side);
    }
}
