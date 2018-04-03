# java-deep-learning-computer-vision

Deep Learning for Computer Vision in Java.

The current package offered pure Java solution for the following tasks:

* Image Classification (Inception ResNet)
* Image Embedding (Inception ResNet)
* Image Search 
* Product Recommendation based on similar images in user's recent browsing history
* Object Detection (SSD)

# Object Detection 

The following [sample codes](src/main/java/com/github/cschen1205/DeepVisionDemo.java) shows how to detect multiple
objects in an image using [DeepVision](src/main/java/com/github/cschen1205/DeepVision.java) class

```java
import com.github.cschen1205.imgreg.recommenders.ImageUserHistory;
import com.github.cschen1205.imgreg.search.ImageSearchEngine;
import com.github.cschen1205.imgreg.search.ImageSearchEngineInception;
import com.github.cschen1205.imgreg.search.ImageSearchEntry;
import com.github.cschen1205.commons.FileUtils;
import com.github.cschen1205.objdetect.models.DetectedObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ObjectDetectionDemo {
    public static void main(String[] args) {
        DeepVision detector = new DeepVision();

        try {
            BufferedImage img = ImageIO.read(new File("images/test.jpg"));

            List<DetectedObj> result = detector.detectObjects(img);

            System.out.println("There are " + result.size() + " objects detected");
            for(int i=0; i < result.size(); ++i){
                System.out.println("# " + (i + 1) + ": " + result.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

# Image Classification 

The following [sample codes](src/main/java/com/github/cschen1205/DeepVisionDemo.java) shows how to classify image 
using [DeepVision](src/main/java/com/github/cschen1205/DeepVision.java) class

```java
import com.github.cschen1205.imgreg.recommenders.ImageUserHistory;
import com.github.cschen1205.imgreg.search.ImageSearchEngine;
import com.github.cschen1205.imgreg.search.ImageSearchEngineInception;
import com.github.cschen1205.imgreg.search.ImageSearchEntry;
import com.github.cschen1205.commons.FileUtils;
import com.github.cschen1205.objdetect.models.DetectedObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ImageClassifierDemo {
    public static void main(String[] args) {
        DeepVision classifier = new DeepVision();
        
        for(File file : FileUtils.getImageFiles("image_samples", ".png")) {
            String predicted_label = classifier.classifyImage(file);
            logger.info("predicted class for {}: {}", file.getName(), predicted_label);
        }
    }
}
```

# Image Embedding 

The following [sample codes](src/main/java/com/github/cschen1205/DeepVisionDemo.java) shows how to convert an image
 to a fixed-length embedding vector using [DeepVision](src/main/java/com/github/cschen1205/DeepVision.java) class

```java
import com.github.cschen1205.imgreg.recommenders.ImageUserHistory;
import com.github.cschen1205.imgreg.search.ImageSearchEngine;
import com.github.cschen1205.imgreg.search.ImageSearchEngineInception;
import com.github.cschen1205.imgreg.search.ImageSearchEntry;
import com.github.cschen1205.commons.FileUtils;
import com.github.cschen1205.objdetect.models.DetectedObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ImageEmbeddingDemo {
    public static void main(String[] args) {
        DeepVision classifier = new DeepVision();
        
        for(File file : FileUtils.getImageFiles("image_samples", ".png")) {
            float[] encoded = classifier.encodeImage(file);
            logger.info("encode image for {}: {}", file.getName(), encoded);
        }
    }
}
```

# Image Search 

The following [sample codes](src/main/java/com/github/cschen1205/DeepVisionDemo.java) shows how to search for similar
 images using [DeepVision](src/main/java/com/github/cschen1205/DeepVision.java) class

```java
import com.github.cschen1205.imgreg.recommenders.ImageUserHistory;
import com.github.cschen1205.imgreg.search.ImageSearchEngine;
import com.github.cschen1205.imgreg.search.ImageSearchEngineInception;
import com.github.cschen1205.imgreg.search.ImageSearchEntry;
import com.github.cschen1205.commons.FileUtils;
import com.github.cschen1205.objdetect.models.DetectedObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ImageSearchDemo {
    public static void main(String[] args) {
        ImageSearchEngine searchEngine = new ImageSearchEngineInception();
        if(!searchEngine.loadIndexDbIfExists()) {
            searchEngine.indexAll(FileUtils.getImageFiles("image_samples", ".png"));
            searchEngine.saveIndexDb();
        }

        int pageIndex = 0;
        int pageSize = 20;
        boolean skipPerfectMatch = true;
        for(File f : FileUtils.getImageFiles("image_samples", ".png")) {
            System.out.println("querying similar music to " + f.getName());
            List<ImageSearchEntry> result = searchEngine.query(f, pageIndex, pageSize, skipPerfectMatch);
            for(int i=0; i < result.size(); ++i){
                System.out.println("# " + i + ": " + result.get(i).getPath() + " (distSq: " + result.get(i).getDistance() + ")");
            }
        }
    }
}
```

# Product Recommendation 

The following [sample codes](src/main/java/com/github/cschen1205/DeepVisionDemo.java) shows how to recommend products
 based on user's recent image browsing history, using [DeepVision](src/main/java/com/github/cschen1205/DeepVision.java) class

```java
import com.github.cschen1205.imgreg.recommenders.ImageUserHistory;
import com.github.cschen1205.imgreg.search.ImageSearchEngine;
import com.github.cschen1205.imgreg.search.ImageSearchEngineInception;
import com.github.cschen1205.imgreg.search.ImageSearchEntry;
import com.github.cschen1205.commons.FileUtils;
import com.github.cschen1205.objdetect.models.DetectedObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ProductRecommendationDemo {
    public static void main(String[] args) {
        ImageUserHistory userHistory = new ImageUserHistory();
        
        List<String> imageFiles = FileUtils.getImageFilePaths();
        Collections.shuffle(imageFiles);

        for(int i=0; i < 40; ++i){
            String filePath = imageFiles.get(i);
            userHistory.logImage(filePath);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DeepVision recommender = new DeepVision();
        if(!recommender.loadIndexDbIfExists()) {
            recommender.indexAll(FileUtils.getImageFiles("image_samples", ".png"));
            recommender.saveIndexDb();
        }

        System.out.println(userHistory.head(10));

        int k = 10;
        List<ImageSearchEntry> result = recommender.recommends(userHistory.getHistory(), k);

        for(int i=0; i < result.size(); ++i){
            ImageSearchEntry entry = result.get(i);
            System.out.println("Search Result #" + (i+1) + ": " + entry.getPath());
        }
    }
}
```




