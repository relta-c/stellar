package net.chifumi.stellar;

import org.joml.Vector2fc;

class LinePrimitive implements Primitive {
    private final float[] vertices;
    private final int verticesNum;
    private final int drawMode;

    LinePrimitive(final Vector2fc pointA, final Vector2fc pointB) {
        vertices = new float[]{
                pointA.x(), pointA.y(), 0.0f, 0.0f,
                pointB.x(), pointB.y(), 0.0f, 0.0f};
        verticesNum = 2;
        drawMode = DrawMode.LINE.getId();
    }

    @Override
    public float[] getVertices() {
        return vertices.clone();
    }

    @Override
    public int getVerticesNum() {
        return verticesNum;
    }

    @Override
    public int getDrawMode() {
        return drawMode;
    }
}
