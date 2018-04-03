package com.github.cschen1205.imgreg.recommenders.models;

import com.github.cschen1205.imgreg.search.models.ImageSearchEntry;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageRank {
    private String imagePath;
    private double distance1;
    private double distance2;
    private double meanDistance;
    private float[] features;

    public ImageSearchEntry toSearchEntry() {
        return new ImageSearchEntry(imagePath, features).withDistance(meanDistance);
    }
}
