package com.github.cschen1205.imgreg.search;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface ImageSearchEngine {

    void purgeDb();

    ImageSearchEntry index(File file);

    void indexAll(File[] files);

    List<ImageSearchEntry> query(File file, int pageIndex, int pageSize);

    List<ImageSearchEntry> query(File file, int pageIndex, int pageSize, boolean skipPerfectMatch);

    boolean loadIndexDbIfExists();

    void saveIndexDb();

    String classifyImage(File image);

    String classifyImage(BufferedImage image);

    float[] encodeImage(File image);

    float[] encodeImage(BufferedImage image);
}
