package net.chifumi.stellar.enums;

import static org.lwjgl.opengl.GL33.GL_RGB;
import static org.lwjgl.opengl.GL33.GL_RGBA;

public enum ImageFormat {
    RGB(GL_RGB),
    RGBA(GL_RGBA);

    private final int id;

    public int getId() {
        return id;
    }

    ImageFormat(int id) {
        this.id = id;
    }
}
