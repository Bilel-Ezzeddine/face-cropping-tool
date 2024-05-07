package org.fca;

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

        String classifierPath = "./conf/haarcascade_frontalface_default.xml";
        File classifierFile = new File(classifierPath);
        if (!classifierFile.exists()) {
            System.out.println(classifierFile.getAbsolutePath());
            System.err.println("Classifier file not found.");
            System.exit(1);
        }

        var processor = ImageProcessor.getInstance(inputDir, outputDir);
        processor.process(classifierPath);
        System.out.println("All files processed successfully.");
    }
}