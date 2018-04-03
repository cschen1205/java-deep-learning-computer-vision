package com.github.cschen1205.imgreg.search;

import com.github.cschen1205.commons.FileUtils;
import com.github.cschen1205.imgreg.search.models.ImageSearchEngine;
import com.github.cschen1205.imgreg.search.models.ImageSearchEngineInception;
import com.github.cschen1205.imgreg.search.models.ImageSearchEntry;

import java.io.File;
import java.util.List;

public class ImageSearchEngineDemo {
    public static void main(String[] args) {
        ImageSearchEngine searchEngine = new ImageSearchEngineInception();
        if(!searchEngine.loadIndexDbIfExists()) {
            searchEngine.indexAll(FileUtils.getImageFiles());
            searchEngine.saveIndexDb();
        }

        int pageIndex = 0;
        int pageSize = 20;
        boolean skipPerfectMatch = true;
        for(File f : FileUtils.getImageFiles()) {
            System.out.println("querying similar music to " + f.getName());
            List<ImageSearchEntry> result = searchEngine.query(f, pageIndex, pageSize, skipPerfectMatch);
            for(int i=0; i < result.size(); ++i){
                System.out.println("# " + i + ": " + result.get(i).getPath() + " (distSq: " + result.get(i).getDistance() + ")");
            }
        }
    }
}
