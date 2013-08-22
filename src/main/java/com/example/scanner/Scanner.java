package com.example.scanner;

import com.example.model.FileType;

import java.io.File;

/**
 * @author Liangshi.cn@gmail.com
 */
public interface Scanner {
    FileType scan(File file);
}
