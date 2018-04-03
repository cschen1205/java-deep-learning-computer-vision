package com.github.cschen1205.imgreg.recommenders.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ImageMemo {
    private String imagePath;
    private long eventTime;

    public ImageMemo() {

    }

    public ImageMemo(String filePath){
        this.imagePath = filePath;
        eventTime = new Date().getTime();
    }
}
