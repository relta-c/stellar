package net.chifumi.stellar;

import org.joml.Vector2fc;

class CirclePrimitive implements Primitive {
    private float[] vertices;
    private int verticesNum;
    private final int drawMode;

    CirclePrimitive(final Vector2fc origin, final float radius, final int side) {
        drawMode = DrawMode.TRIANGLE_FAN.getId();
        createVertices(origin, radius, side);
    }

    @SuppressWarnings("NumericCastThatLosesPrecision")
    private void createVertices(final Vector2fc origin, final float radius, final int side) {
        verticesNum = side + 2;
        final double doublePi = 2 * Math.PI;

        final double[] verticesX = new double[verticesNum];
        final double[] verticesY = new double[verticesNum];

        verticesX[0] = origin.x();
        verticesY[0] = origin.y();

        for (int i = 1; i < verticesNum; i++) {
            verticesX[i] = origin.x() + (radius * StrictMath.cos(i * doublePi / side));
            verticesY[i] = origin.y() - (radius * StrictMath.sin(i * doublePi / side));
        }

        vertices = new float[verticesNum * 4];
        for (int i = 0; i < verticesNum; i++) {
            final int index = i * 4;
            vertices[index] = (float) verticesX[i];
            vertices[index + 1] = (float) verticesY[i];
            vertices[index + 2] = 0;
            vertices[index + 3] = 0;
        }
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
