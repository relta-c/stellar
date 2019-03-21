package net.chifumi.stellar;

import org.apache.commons.io.IOUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

enum ResourceLoader {
    ;

    public static Shader loadShader(final String vertexPath, final String fragmentPath) throws FileNotFoundException{
        return loadShaderFile(vertexPath, fragmentPath);
    }

    static Texture LoadTextureFile(final String path) throws FileNotFoundException{
        final ByteBuffer data;
        final int width;
        final int height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
        final IntBuffer rawWidth = stack.mallocInt(1);
        final IntBuffer rawHeight = stack.mallocInt(1);
        final IntBuffer channels = stack.mallocInt(1);
        data = STBImage.stbi_load(path, rawWidth, rawHeight, channels, 0);
        if (data == null) {
            throw new FileNotFoundException("failed to load texture : " + path);
        }
        width = rawWidth.get();
        height = rawHeight.get();
        }
        return new Texture(width, height, data);
    }

    private static Shader loadShaderFile(final String vertexPath, final String fragmentPath) throws FileNotFoundException {
        final String vertexSource = loadTextFile(vertexPath);
        final String fragmentSource = loadTextFile(fragmentPath);
        return new Shader(vertexSource, fragmentSource);
    }

    @SuppressWarnings("unused")
    private static Shader loadShaderFile(final String vertexPath, final String fragmentPath, final String geometryPath) throws FileNotFoundException {
        final String vertexSource = loadTextFile(vertexPath);
        final String fragmentSource = loadTextFile(fragmentPath);
        final String geometrySource = loadTextFile(geometryPath);

        return new Shader(vertexSource, fragmentSource, geometrySource);
    }

    private static String loadTextFile(final String path) throws FileNotFoundException {
        String result = null;
        final InputStream fileStream = loadResourceFile(path);
        try {
            result = IOUtils.toString(fileStream, StandardCharsets.UTF_8);
            fileStream.close();
        } catch (final IOException e) {
            System.err.println("failed to load file : " + path);
        }
        if (result == null) {
            throw new FileNotFoundException("failed to load file : " + path);
        }
//        try (Scanner scanner = new Scanner(file)) {
//            while (scanner.hasNextLine()) {
//                final String line = scanner.nextLine();
//                result.append(line).append('\n');
//            }
//        } catch (final FileNotFoundException e) {
//            System.err.println("failed to load file : " + path);
//        }
        return result;
    }

    private static InputStream loadResourceFile(final String path) throws FileNotFoundException {
        final InputStream fileStream = ResourceLoader.class.getResourceAsStream(path);
        if (fileStream == null) {
            throw new FileNotFoundException("File not found : " + path);
        }
        return fileStream;
    }
}
