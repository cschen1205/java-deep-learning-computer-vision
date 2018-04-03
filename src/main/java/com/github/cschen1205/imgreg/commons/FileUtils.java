package com.github.cschen1205.tensorflow.commons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static File[] getImageFiles() {
        return getImageFiles("image_samples", ".png");
    }


    public static File[] getImageFiles(String dirPath, String extension) {
        List<File> result = new ArrayList<>();
        File dir = new File(dirPath);
        System.out.println(dir.getAbsolutePath());

        getImageFiles(dir, result, extension);

        File[] files = new File[result.size()];
        for(int i=0; i < files.length;++i) {
            files[i] = result.get(i);
        }
        return files;
    }

    private static void getImageFiles(File dir, List<File> result, String extension) {
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if(f.isDirectory()){
                    getImageFiles(f, result);
                } else {
                    String file_path = f.getAbsolutePath();
                    if (file_path.endsWith(extension)) {
                        result.add(f);
                    }
                }
            }
        }
    }

    public static List<String> getImageFilePaths() {
        List<String> result = new ArrayList<>();
        File[] files = getImageFiles();
        for(File f : files) {
            result.add(f.getAbsolutePath());
        }
        return result;
    }

}
