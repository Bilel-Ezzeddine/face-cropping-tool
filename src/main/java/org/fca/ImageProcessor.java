package org.fca;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import java.io.File;

public class ImageProcessor {

    private final File inputDir;
    private final File outputDir;

    private static ImageProcessor INSTANCE = null;

    public ImageProcessor(File inputDir, File outputDir) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
    }

    void process(String classifierPath){
        try (CascadeClassifier faceDetector = new CascadeClassifier(classifierPath)) {
            File[] personFolders = inputDir.listFiles(File::isDirectory);
            if (personFolders != null) {
                for (File personFolder : personFolders) {
                    String personName = personFolder.getName();
                    File[] imageFiles = personFolder.listFiles((dir, name) ->
                            name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));

                    if (imageFiles != null) {
                        int fileCounter = 1;
                        for (File imageFile : imageFiles) {
                            Mat image = opencv_imgcodecs.imread(imageFile.getAbsolutePath());
                            RectVector faceRectangles = new RectVector();
                            faceDetector.detectMultiScale(image, faceRectangles);

                            File personOutputDir = new File(outputDir, personName);
                            if (!personOutputDir.exists() && !personOutputDir.mkdirs()) {
                                System.err.println("Failed to create the output directory for " + personName);
                                continue;
                            }

                            for (int i = 0; i < faceRectangles.size(); i++) {
                                Mat face = new Mat(image, faceRectangles.get(i));
                                if (isValidFace(face)) {
                                    File outputImageFile = new File(personOutputDir,
                                            personName + "_" + fileCounter++ + ".jpg");
                                    opencv_imgcodecs.imwrite(outputImageFile.getAbsolutePath(), face);
                                    System.out.println(outputImageFile.getAbsolutePath());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean isValidFace(Mat face) {
        return face.cols() > 50 && face.rows() > 50;
    }

    public static ImageProcessor getInstance(File in, File out){
        if (INSTANCE == null) {
            INSTANCE = new ImageProcessor(in,out);
        }
        return INSTANCE;
    }
}
