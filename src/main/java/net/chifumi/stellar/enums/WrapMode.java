package net.chifumi.stellar.enums;

import static org.lwjgl.opengl.GL33.*;

public enum WrapMode {
    REPEAT(GL_REPEAT),
    MIRROR_REPEAT(GL_MIRRORED_REPEAT),
    CLAMP_TO_EDGE(GL_CLAMP_TO_EDGE),
    CLAMP_TO_BORDER(GL_CLAMP_TO_BORDER);

    private final int id;

    public int getId() {
        return id;
    }

    WrapMode(int id) {
        this.id = id;
    }
}
