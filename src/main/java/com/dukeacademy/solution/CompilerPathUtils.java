package com.dukeacademy.solution;

import com.dukeacademy.solution.exceptions.FileNotClassException;

import java.io.File;

public class CompilerPathUtils {
    public static boolean isClassFile(File file) {
        String name = file.getName();
        String extension = "";

        if(name.lastIndexOf(".") != -1 && name.lastIndexOf(".") != 0) {
            extension = name.substring(name.lastIndexOf(".") + 1);
        }

        return extension.equals("class");
    }

    public static String getClassName(File file) throws FileNotClassException {
        if (!CompilerPathUtils.isClassFile(file)) {
            throw new FileNotClassException();
        }

        String name = file.getPath();
        return name.replace(".class", "");
    }

    public static String getClassPath(File file) throws FileNotClassException {
        if (!CompilerPathUtils.isClassFile(file)) {
            throw new FileNotClassException();
        }

        String path = file.getPath();
        String name = file.getName();
        return path.replace(name, "");
    }
}
