package com.example.model;

/**
 * @author Liangshi.cn@gmail.com
 */
public class FileInfo {
    private String name;
    private final FileType type;
    private long length;
    private int width;
    private int height;
    private float compression;

    public FileInfo(FileType type) {
        this.type = type;
    }

    public void plus(FileInfo info) {
        if(info == null) {
            return;
        }
        plusLength(info.getLength());
        plusWidth(info.getWidth());
        plusHeight(info.getHeight());
        plusCompression(info.getCompression());
    }

    public void average(int count) {
        if(count < 1) {
            throw new IllegalArgumentException("Count cannot little than 1");
        }
        this.length /= count;
        this.width /= count;
        this.height /= count;
        this.compression /= count;
    }

    public FileInfo plusName(String name) {
        this.name = name;
        return this;
    }

    public FileInfo plusLength(long length) {
        this.length += length;
        return this;
    }

    public FileInfo plusWidth(int width) {
        this.width += width;
        return this;
    }

    public FileInfo plusHeight(int height) {
        this.height += height;
        return this;
    }

    public FileInfo plusCompression(float compression) {
        this.compression += compression;
        return this;
    }

    public String getName() {
        return name;
    }

    public FileType getType() {
        return type;
    }

    public long getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getCompression() {
        return compression;
    }
}
