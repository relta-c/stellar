package net.chifumi.stellar;

import net.chifumi.stellar.enums.FilteringMode;
import net.chifumi.stellar.enums.ImageFormat;
import net.chifumi.stellar.enums.WrapMode;

import java.io.FileNotFoundException;

public class TextureLoader {
    private ImageFormat _rawImageFormat;
    private ImageFormat _internalImageFormat;
    private WrapMode _wrapS;
    private WrapMode _wrapT;
    private FilteringMode _filterMin;
    private FilteringMode _filterMax;

    public TextureLoader() {
        _rawImageFormat = ImageFormat.RGBA;
        _internalImageFormat = ImageFormat.RGBA;
        _wrapS = WrapMode.REPEAT;
        _wrapT = WrapMode.REPEAT;
        _filterMin = FilteringMode.NEAREST;
        _filterMax = FilteringMode.NEAREST;
    }

    public Texture load(final String path) throws FileNotFoundException{
        final Texture loadTextureFile = ResourceLoader.LoadTextureFile(path);
        loadTextureFile.setRawImageFormat(_rawImageFormat);
        loadTextureFile.setInternalImageFormat(_internalImageFormat);
        loadTextureFile.setWrapS(_wrapS);
        loadTextureFile.setWrapT(_wrapT);
        loadTextureFile.setFilterMin(_filterMin);
        loadTextureFile.setFilterMax(_filterMax);
        loadTextureFile.generate();
        return loadTextureFile;
    }

    public TextureLoader rawImageFormat(final ImageFormat format) {
        _rawImageFormat = format;
        return this;
    }

    public TextureLoader internalImageFormat(final ImageFormat format) {
        _internalImageFormat = format;
        return this;
    }

    public TextureLoader imageFormat(final ImageFormat format) {
        _rawImageFormat = format;
        _internalImageFormat = format;
        return this;
    }

    public TextureLoader wrapT(final WrapMode wrapMode) {
        _wrapS = wrapMode;
        return this;
    }

    public TextureLoader wrapS(final WrapMode wrapMode) {
        _wrapT = wrapMode;
        return this;
    }

    public TextureLoader wrap(final WrapMode wrapMode) {
        _wrapS = wrapMode;
        _wrapT = wrapMode;
        return this;
    }

    public TextureLoader filterMin(final FilteringMode filteringMode) {
        _filterMin = filteringMode;
        return this;
    }

    public TextureLoader filterMax(final FilteringMode filteringMode) {
        _filterMax = filteringMode;
        return this;
    }

    public TextureLoader filter(final FilteringMode filteringMode) {
        _filterMin = filteringMode;
        _filterMax = filteringMode;
        return this;
    }
}
