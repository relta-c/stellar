package net.chifumi.stellar.enums;

import static org.lwjgl.opengl.GL33.GL_LINEAR;
import static org.lwjgl.opengl.GL33.GL_NEAREST;

public enum FilteringMode {
    NEAREST(GL_NEAREST),
    LINEAR(GL_LINEAR);

    private final int id;

    public int getId() {
        return id;
    }

    FilteringMode(final int id) {
        this.id = id;
    }
}
