package online.edwinzhan.faceidbackend.Service;

import online.edwinzhan.faceidbackend.Extension.MathAlgorithmExtension;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.FacemarkKazemi;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import static org.bytedeco.opencv.global.opencv_face.drawFacemarks;
import static org.bytedeco.opencv.global.opencv_highgui.cvWaitKey;
import static org.bytedeco.opencv.global.opencv_highgui.imshow;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;


public class FaceMarkService extends MathAlgorithmExtension {

    public static int[][] getFaceMarks(String PhotoNameAndLocation, String CreateMarkers)
        throws IOException, URISyntaxException, InterruptedException {
        //Using try-with-resources to ensure that the resources are closed when the program is finished
        try (CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_default.xml");
             Mat img = imread("path_to_your_image.jpg")) {

            //Using FaceMarkKazemi to detect facial landmarks
            FacemarkKazemi faceMark = FacemarkKazemi.create();
            faceMark.loadModel("face_landmark_model.dat");

            //Converting the image to grayscale
            Mat gray = new Mat();
            cvtColor(img, gray, COLOR_BGR2GRAY);

            //Detecting faces
            RectVector faces = new RectVector();
            faceDetector.detectMultiScale(img, faces);
            System.out.println("Faces detected: " + faces.size());

            //If there are more than one face or no faces, return null
            if (faces.size() != 1) {
                System.out.println(" More than 1 face or no Faces Detected");
                return null;
            }

            //Creating a variable for landmarks
            try (Point2fVectorVector landmarks = new Point2fVectorVector()) {

                //Using landmarks to detect landmarks
                boolean success = faceMark.fit(img, faces, landmarks);

                //create a 2D array to store the coordinates of the landmarks
                int[][] FacialCoordinates = new int[68][2];

                //If the landmarks are detected, then the coordinates of the landmarks are stored in the 2D array
                if (success) {
                    for (long i = 0; i < landmarks.size(); i++) {

                        //Using a for loop to iterate through the landmarks
                        Point2fVector v = landmarks.get(i);
                        for (int x = 0; x < v.size(); x++) {

                            //Storing the coordinates of the landmarks in the 2D array
                            FacialCoordinates[x][0] = (int) v.get(x).get(0);
                            FacialCoordinates[x][1] = (int) v.get(x).get(1);

                            Point position = new Point(FacialCoordinates[x][0], FacialCoordinates[x][1]);

                            if (Objects.equals(CreateMarkers, "XandY")) {

                                //Using putText to display the coordinates of the landmarks on the image
                                opencv_imgproc.putText(img,
                                        ". X: " + FacialCoordinates[x][0] + ", Y: " + FacialCoordinates[x][1], position,
                                        opencv_imgproc.FONT_HERSHEY_PLAIN, 0.7, Scalar.RED, 1, 0, false);

                            } else if (Objects.equals(CreateMarkers, "NumberedPoints")) {

                                //Using putText to display the number of the landmarks on the image
                                opencv_imgproc.putText(img, "." + x, position, opencv_imgproc.FONT_HERSHEY_PLAIN, 1, Scalar.RED,
                                        1, 0, false);

                            }

                        }
                        if (Objects.equals(CreateMarkers, "drawFacemarks")) {

                            //Using drawFace-marks to draw the landmarks on the image
                            drawFacemarks(img, v, Scalar.YELLOW);

                            //Displaying the image
                            imshow("Kazemi Facial Landmark", img);

                            //Waiting for the user to press a key
                            cvWaitKey(0);

                        }
                    }
                }

                if (CreateMarkers != null) {

                    //Writing the image with the landmarks to a file
                    imwrite("Photos/Face-mark Output/" + "Face-mark_Output_"+ PhotoNameAndLocation.substring(PhotoNameAndLocation.length()-8,PhotoNameAndLocation.length()-4) +".jpg", img);
                }
                return FacialCoordinates;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return null;
            }
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


}
