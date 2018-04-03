package com.github.cschen1205.imgreg.recommenders.models;

import com.github.cschen1205.imgreg.search.models.ImageSearchEngine;
import com.github.cschen1205.imgreg.search.models.ImageSearchEntry;

import java.util.List;

public interface ImageRecommender extends ImageSearchEngine {
    List<ImageSearchEntry> recommends(List<ImageMemo> userHistory, int k);
}
