package net.chifumi.stellar.enums;

import static org.lwjgl.opengl.GL33.*;

public enum ShaderType {
    VERTEX(GL_VERTEX_SHADER, "Vertex Shader"),
    FRAGMENT(GL_FRAGMENT_SHADER, "Fragment Shader"),
    GEOMETRY(GL_GEOMETRY_SHADER, "Geometry Shader"),
    PROGRAM(GL_NONE, "Shader Program");

    private final int id;
    private final String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    ShaderType(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
}
