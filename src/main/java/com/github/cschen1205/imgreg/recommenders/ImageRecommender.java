package com.github.cschen1205.imgreg.recommenders;

import com.github.cschen1205.imgreg.search.ImageSearchEngine;
import com.github.cschen1205.imgreg.search.ImageSearchEntry;

import java.util.List;

public interface ImageRecommender extends ImageSearchEngine {
    List<ImageSearchEntry> recommends(List<ImageMemo> userHistory, int k);
}
