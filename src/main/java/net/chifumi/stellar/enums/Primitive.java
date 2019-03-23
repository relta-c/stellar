package net.chifumi.stellar.enums;

public enum Primitive {
    RECT(new float[]{
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 0.0f});

    private final float[] vertices;

    Primitive(final float[] vertices) {
        this.vertices = vertices;
    }

    public float[] getVertices() {
        return vertices.clone();
    }
}
