package com.example.handler;

/**
 * @author Liangshi.cn@gmail.com
 */
public class HandlerFactory {

    private static final JPEGHandler jpegHandler = new JPEGHandler();
    private static final GIFHandler gifHandler = new GIFHandler();

    public static Handler getJPEGHandler() {
        return jpegHandler;
    }

    public static Handler getGIFHandler() {
        return gifHandler;
    }

}
