//    Copyright (C) 2019 Nattakit Hosapsin <delta@chifumi.net>
//
//    This file is part of Stellar
//    Stellar is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as
//    published by the Free Software Foundation, either
//    version 3 of the License, or (at your option) any later version.
//
//    Stellar is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
//
//    You should have received a copy of the GNU Lesser General Public
//    License along with Stellar.  If not, see <https://www.gnu.org/licenses/lgpl.html>.

package net.chifumi.stellar;

import net.chifumi.stellar.enums.Primitive;
import org.joml.Vector2i;
import org.lwjgl.opengl.*;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL33.glClear;

public class Display {
    private final Vector2i resolution;
    private final Queue<Sprite> drawQueue; // TODO : Make draw layer
    private long windowId;
    private int vaoId;
    private String windowTitle;
    private Camera camera;
    private Shader shader;

    public Display(final int width, final int height, final String windowTitle) {
        resolution = new Vector2i(width, height);
        this.windowTitle = windowTitle;
        windowId = 0L;
        vaoId = 0;
        shader = new Shader();
        drawQueue = new LinkedList<>();
        camera = new Camera(new Vector2i(width, height));
        init();
    }

    public Vector2i getResolution() {
        return resolution;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(final Camera camera) {
        this.camera = camera;
    }

    public CharSequence getWindowTitle() {
        return windowTitle;
    }

    public void setWindowTitle(final String windowTitle) {
        glfwSetWindowTitle(windowId, windowTitle);
        this.windowTitle = windowTitle;
    }

    public void update() {
        glfwMakeContextCurrent(windowId);
        glfwPollEvents();

        // Update matrix
        camera.updateViewMatrix();
        camera.updateProjectionMatrix();
        shader.setUniform("view", camera.getView());
        shader.setUniform("projection", camera.getProjection());

        glClear(GL_COLOR_BUFFER_BIT);

        while (!drawQueue.isEmpty()) {
            drawFromQueue(drawQueue.poll());
        }

        glfwSwapBuffers(windowId);
    }

    public void draw(final Sprite sprite) {
        drawQueue.add(sprite);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowId);
    }

    public void close() {
        glfwSetWindowShouldClose(windowId, true);
    }

    long getWindowId() {
        return windowId;
    }

    private void init() {
        // Initialize glfw and create window
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_RESIZABLE, GL11.GL_FALSE);
        windowId = glfwCreateWindow(resolution.x, resolution.y, windowTitle, 0L, 0L);

        glfwMakeContextCurrent(windowId);  // TODO : Use this in every window update

        // Initialize OpenGl
        GL.createCapabilities();
        GL11.glViewport(0, 0, resolution.x, resolution.y);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // TODO : Make clear color customizable

        // Load default shaders
        try {
            shader = ResourceLoader.loadShader("/shaders/vs_default.glsl", "/shaders/fs_default.glsl");
        } catch (final FileNotFoundException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        shader.use();

        final int vboId;

        vaoId = GL30.glGenVertexArrays();
        vboId = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Primitive.RECT.getVertices(), GL15.GL_STATIC_DRAW);

        GL30.glBindVertexArray(vaoId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 << 2, 0L);
        GL20.glEnableVertexAttribArray(1);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 << 2, 0L);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, GL11.GL_NONE);
        GL30.glBindVertexArray(GL11.GL_NONE);
    }

    private void drawFromQueue(final Sprite sprite) {
        shader.setUniform("projection", camera.getProjection());
        shader.setUniform("view", camera.getView());
        shader.setUniform("model", sprite.getModel());
        shader.setUniform("spriteColor", sprite.getColor());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        sprite.getTexture().bind();

        GL30.glBindVertexArray(vaoId);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
    }
}
