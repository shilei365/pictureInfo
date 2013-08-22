package com.example.util;

import com.example.model.FileInfo;

/**
 * @author Liangshi.cn@gmail.com
 */
public class Printer {

    public static void printFileInfo(final FileInfo fileInfo) {
        p(getNameAndType(fileInfo));
        p(getLength(fileInfo));
        p(getSize(fileInfo));
        p(getCompression(fileInfo));
    }

    private static String getNameAndType(final FileInfo fileInfo) {
        return String.format("name: %s, type: %s", fileInfo.getName(), fileInfo.getType());
    }

    private static String getLength(final FileInfo fileInfo) {
        return String.format("length: %s", fileInfo.getLength());
    }

    private static String getSize(final FileInfo fileInfo) {
        return String.format("size: %s X %s", fileInfo.getWidth(), fileInfo.getHeight());
    }

    private static String getCompression(final FileInfo fileInfo) {
        return String.format("compression: %s%", fileInfo.getCompression());
    }

    public static void p(String s) {
        System.out.println(s);
    }
}
