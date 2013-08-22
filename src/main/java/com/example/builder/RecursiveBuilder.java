package com.example.builder;

import com.example.scanner.DefaultScanner;
import com.example.scanner.Scanner;
import com.example.util.Printer;
import com.example.handler.Handler;
import com.example.handler.HandlerFactory;
import com.example.model.FileInfo;
import com.example.model.FileType;

import java.io.File;

/**
 * @author Liangshi.cn@gmail.com
 */
public class RecursiveBuilder implements Builder {

    private static final Scanner SCANNER = new DefaultScanner();

    @Override
    public void build(final File file) {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }
        extract(file);
    }

    private FileInfo extract(final File file) {
        final FileType fileType = SCANNER.scan(file);
        final FileInfo info = new FileInfo(fileType);
        switch (fileType) {
            case JPEG:
                info.plus(build(file, HandlerFactory.getJPEGHandler(), info));
                break;
            case GIF:
                info.plus(build(file, HandlerFactory.getGIFHandler(), info));
                break;
            case DIRECTORY:
                info.plus(buildDir(file, info));
                break;
            case UNKNOWN:
            default:
                info.plus(build(file, null, info));
                break;
        }

        return info;
    }

    private FileInfo buildDir(final File file, final FileInfo info) {
        int count = 1;
        for (File f : file.listFiles()) {
            info.plus(extract(f));
            count++;
        }
        info.average(count);

        Printer.printFileInfo(info);
        return info;
    }

    private FileInfo build(final File file, final Handler handler, final FileInfo info) {
        if(handler == null) {
            info.plusName("Unknown file")
                    .plusLength(0l)
                    .plusWidth(0)
                    .plusHeight(0)
                    .plusCompression(0f);
        } else {
            info.plusName(handler.getName(file))
                    .plusLength(handler.getLength(file))
                    .plusWidth(handler.getWidth(file))
                    .plusHeight(handler.getHeight(file))
                    .plusCompression(handler.getCompression(file));
        }

        Printer.printFileInfo(info);
        return info;
    }
}
