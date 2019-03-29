package net.chifumi.stellar.graphics;

import net.chifumi.stellar.geometry.Primitive;
import net.chifumi.stellar.geometry.Sharp;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Solid implements Drawable {
    private Sharp sharp;
    private Primitive primitive;
    private Vector3f color;
    private Matrix4f modelMatrix;

    public Solid(final Sharp sharp) {
        this.sharp = sharp;
        primitive = sharp.getPrimitive();
        modelMatrix = new Matrix4f();
        color = new Vector3f();
    }

    public Sharp getSharp() {
        return sharp;
    }

    public void setSharp(final Sharp sharp) {
        this.sharp = sharp;
        primitive = sharp.getPrimitive();
    }

    public void setColor(final float red, final float green, final float blue) {
        color = new Vector3f(red, green, blue);
    }

    @Override
    public Vector3f getColor() {
        return color;
    }

    public void setColor(final Vector3fc color) {
        this.color = (Vector3f) color;
    }

    @Override
    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(final Matrix4fc modelMatrix) {
        this.modelMatrix = (Matrix4f) modelMatrix;
    }

    @Override
    public Primitive getPrimitive() {
        return primitive;
    }
}
