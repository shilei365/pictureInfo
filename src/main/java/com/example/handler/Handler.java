package com.example.handler;

import java.io.File;

/**
 * @author Liangshi.cn@gmail.com
 */
public interface Handler {

    String getName(final File file);

    long getLength(final File file);

    int getWidth(final File file);

    int getHeight(final File file);

    float getCompression(final File file);

}
