package com.github.cschen1205;

import com.github.cschen1205.imgreg.recommenders.ImageMemo;
import com.github.cschen1205.imgreg.recommenders.ImageRank;
import com.github.cschen1205.imgreg.recommenders.ImageRecommender;
import com.github.cschen1205.imgreg.search.ImageSearchEngineInception;
import com.github.cschen1205.imgreg.search.ImageSearchEntry;
import com.github.cschen1205.objdetect.ObjectDetector;
import com.github.cschen1205.objdetect.models.DetectedObj;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class DeepVision extends ImageSearchEngineInception implements ImageRecommender {

    ObjectDetector detector = new ObjectDetector();

    public DeepVision() {
        super();
        try {
            detector.loadModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ImageSearchEntry> recommends(List<ImageMemo> userHistory, int k) {
        userHistory.sort((a, b) -> Long.compare(b.getEventTime(), a.getEventTime()));
        List<String> mostRecentHistory = new ArrayList<>();
        if(userHistory.size() > 60) {
            for(int i=0; i < 20; ++i) {
                ImageMemo memo = userHistory.get(i);
                String filePath = memo.getImagePath();
                if(mostRecentHistory.indexOf(filePath) < 0) {
                    mostRecentHistory.add(filePath);
                }
            }
        } else if(userHistory.size() > 30) {
            for(int i=0; i < 10; ++i) {
                ImageMemo memo = userHistory.get(i);
                String filePath = memo.getImagePath();
                if(mostRecentHistory.indexOf(filePath) < 0) {
                    mostRecentHistory.add(filePath);
                }
            }
        }

        Map<String, ImageRank> ranks = new HashMap<>();

        for(int i=0; i < mostRecentHistory.size(); ++i){
            String filePath = mostRecentHistory.get(i);
            double distance2 = (double)mostRecentHistory.size() / (i+1.0);

            File file = new File(filePath);
            List<ImageSearchEntry> similar_songs = query(file, 0, 10, true);

            for(ImageSearchEntry entry : similar_songs){
                double distance1 = Math.sqrt(entry.getDistance());

                double distance_mean = (distance1 * distance2) / (distance1 + distance2);

                ImageRank newRank = new ImageRank();
                newRank.setImagePath(entry.getPath());
                newRank.setFeatures(entry.getFeatures());
                newRank.setDistance1(distance1);
                newRank.setDistance2(distance2);
                newRank.setMeanDistance(distance_mean);

                if(ranks.containsKey(entry.getPath())){
                    ImageRank rank = ranks.get(entry.getPath());
                    if(rank.getMeanDistance() < distance_mean){
                        ranks.put(entry.getPath(), newRank);
                    }
                } else {
                    ranks.put(entry.getPath(), newRank);
                }
            }
        }

        List<ImageRank> ranked = new ArrayList<>(ranks.values());
        ranked.sort(Comparator.comparingDouble(ImageRank::getMeanDistance));

        List<ImageSearchEntry> result = new ArrayList<>();
        for(int i=0; i < k; ++i){
            if(i < ranked.size()){
                result.add(ranked.get(i).toSearchEntry());
            }
        }
        return result;
    }

    public List<DetectedObj> detectObjects(BufferedImage img) {
        try {
            return detector.detectObjects(img);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
