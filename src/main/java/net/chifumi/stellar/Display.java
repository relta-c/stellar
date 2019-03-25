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

import org.joml.Vector2i;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL15;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

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

        while (!drawQueue.isEmpty()) {
            drawFromQueue(drawQueue.poll());
        }

        glfwSwapBuffers(windowId);
        glClear(GL_COLOR_BUFFER_BIT);
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

    Shader getShader() {
        return shader;
    }

    private void init() {
        // Initialize glfw and create window
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        windowId = glfwCreateWindow(resolution.x, resolution.y, windowTitle, 0L, 0L);

        glfwMakeContextCurrent(windowId);  // TODO : Use this in every window update

        // Initialize OpenGl
        GL.createCapabilities();
        glViewport(0, 0, resolution.x, resolution.y);
        glEnable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // TODO : Make clear color customizable

        // Load default shaders
        try {
            shader = ResourceLoader.loadShader("/shaders/vs_default.glsl", "/shaders/fs_default.glsl");
        } catch (final FileNotFoundException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        shader.use();

        final int vboId;

        vaoId = glGenVertexArrays();
        vboId = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, StaticPrimitive.RECT.getVertices(), GL15.GL_STATIC_DRAW);

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 << 2, 0L);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 << 2, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, GL_NONE);
        glBindVertexArray(GL_NONE);
    }

    private void drawFromQueue(final Sprite sprite) {
        shader.setUniform("projection", camera.getProjection());
        shader.setUniform("view", camera.getView());
        shader.setUniform("model", sprite.getModel());
        shader.setUniform("spriteColor", sprite.getColor());

        glActiveTexture(GL_TEXTURE0);
        sprite.getTexture().bind();

        glBindVertexArray(vaoId);
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
}
