package net.chifumi.stellar;

import org.joml.Matrix4fc;
import org.joml.Vector3fc;

public interface Drawable {
    Primitive getPrimitive();
    Matrix4fc getModel();
    Vector3fc getColor();
    Texture getTexture();
}
