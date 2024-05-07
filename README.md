# Face Cropping Tool

A tool for extracting cropped faces from images, simplifying machine learning face dataset preparation.

## Build and Run

### Prerequisites

- JDK
- Maven

### Build

1. Download or clone the source code.
2. Navigate to the project directory.
3. Build using Maven:

````
mvn clean install
````


4. Extract the generated zip from `target`.

### Run

1. Navigate to the extracted directory.
2. Run:

````
java -jar face-cropping-tool.jar <input_folder>
````

Replace `<input_folder>` with image directory.

## Example
````
java -jar face-cropping-tool.jar /path/to/input/images
````
## Dependencies

- OpenCV (JavaCV) for face detection and processing.
