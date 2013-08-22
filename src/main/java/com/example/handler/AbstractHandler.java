package com.example.handler;

import java.io.File;

/**
 * @author Liangshi.cn@gmail.com
 */
abstract class AbstractHandler implements Handler {

    @Override
    public String getName(File file) {
        return file.getAbsolutePath();
    }

    @Override
    public long getLength(File file) {
        return file.length();
    }
}
