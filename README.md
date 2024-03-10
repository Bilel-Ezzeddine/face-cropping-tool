# face-cropping-tool
Facilitate machine learning face dataset training by effortlessly extracting cropped faces from images

## How to Build and Run

### Prerequisites

- Java Development Kit (JDK) installed on your system
- Maven build tool installed on your system

### Build Instructions

1. Ensure you have the source code of the Face Cropping Tool available on your local machine.

2. Navigate to the project directory.

3. Build the application using Maven:

   ```
   mvn clean install
   ```

   This will compile the source code, run tests (if any), and package the application into an executable JAR file with dependencies included.

### Run Instructions

1. After successfully building the project, navigate to the `target` directory.

2. Run the application using the following command:

   ```
   java -jar face-cropping-tool.jar <input_folder>
   ```

   Replace `<input_folder>` with the path to the directory containing the images from which you want to extract faces.

3. The application will process the images, detect faces, and save the cropped faces to an output directory.

## Example Usage

```
java -jar face-cropping-tool.jar /path/to/input/images
```

## Dependencies

- OpenCV (JavaCV) - Provides computer vision libraries for face detection and image processing.

