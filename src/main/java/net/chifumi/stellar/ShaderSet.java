package net.chifumi.stellar;

public enum ShaderSet {
    DEFAULT("/shaders/vs_default.glsl", "/shaders/fs_default.glsl"),
    INVERT("/shaders/vs_default.glsl", "/shaders/fs_invert.glsl");

    private final String vertex;
    private final String fragment;

    ShaderSet(final String vertex, final String fragment) {
        this.vertex = vertex;
        this.fragment = fragment;
    }

    public String getVertex() {
        return vertex;
    }

    public String getFragment() {
        return fragment;
    }
}
