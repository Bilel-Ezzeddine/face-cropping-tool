package org.fca;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_core.RectVector;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <input_folder>");
            System.exit(1);
        }

        String inputDirectory = args[0];
        String outputDirectory = args[0].replace("in", "out");

        File inputDir = new File(inputDirectory);
        File outputDir = new File(outputDirectory);

        if (!inputDir.exists() || !inputDir.isDirectory()) {
            System.err.println("Input directory does not exist or is not a directory.");
            System.exit(1);
        }

        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                System.err.println("Failed to create the output directory.");
                System.exit(1);
            }
        }

        String classifierPath = "src/main/resources/haarcascade_frontalface_default.xml";
        File classifierFile = new File(classifierPath);
        if (!classifierFile.exists()) {
            System.err.println("Classifier file not found.");
            System.exit(1);
        }

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

        System.out.println("All files processed successfully.");
    }

    private static boolean isValidFace(Mat face) {
        return face.cols() > 50 && face.rows() > 50;
    }
}