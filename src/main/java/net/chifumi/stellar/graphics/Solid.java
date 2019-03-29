package net.chifumi.stellar.graphics;

import net.chifumi.stellar.geometry.Sharp;
import net.chifumi.stellar.geometry.Primitive;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Solid implements Drawable {
    private Sharp sharp;
    private Matrix4f model;
    private Vector3f color;

    public Solid(final Sharp sharp) {
        this.sharp = sharp;
        model = new Matrix4f();
        color = new Vector3f();
    }

    public Sharp getSharp() {
        return sharp;
    }

    public void setSharp(final Sharp sharp) {
        this.sharp = sharp;
    }

    public void setColor(final float red, final float green, final float blue) {
        color = new Vector3f(red, green, blue);
    }

    @Override
    public Primitive getPrimitive() {
        return sharp.getPrimitive();
    }

    @Override
    public Matrix4f getModel() {
        return model;
    }

    @Override
    public Vector3f getColor() {
        return color;
    }

    public void setColor(final Vector3fc color) {
        this.color = (Vector3f) color;
    }

    public void setModel(final Matrix4f model) {
        this.model = model;
    }
}
