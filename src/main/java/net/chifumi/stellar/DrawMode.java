package net.chifumi.stellar;

import static org.lwjgl.opengl.GL33.*;

enum DrawMode {
    RECTANGLE(GL_TRIANGLES),
    LINE(GL_LINES);

    private final int id;

    DrawMode(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
