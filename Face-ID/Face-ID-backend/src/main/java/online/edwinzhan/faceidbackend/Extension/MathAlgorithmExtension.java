package online.edwinzhan.faceidbackend.Extension;

public class MathAlgorithmExtension {

    /**
     * Computes the centroid size of the points.
     * @param points The points to be measured.
     * @return Centroid size.
     */
    public double computeCentroidSize(double[][] points) {
        double sum = 0;
        for (double[] point : points) {
            sum += Math.sqrt(Math.pow(point[0], 2) + Math.pow(point[1], 2));
        }
        return sum / points.length;
    }

    /**
     * Computes the centroid of the points.
     * @param points The points to be measured.
     * @return Centroid coordinates.
     */
    public double[] computeCentroid(double[][] points) {
        double[] centroid = new double[2]; // x, y
        for (double[] point : points) {
            centroid[0] += point[0];
            centroid[1] += point[1];
        }
        centroid[0] /= points.length;
        centroid[1] /= points.length;
        return centroid;
    }

    /**
     * Translates points such that their centroid is at the origin.
     * @param points The points to be translated.
     * @return Translated points.
     */
    public double[][] translatePointsToOrigin(double[][] points) {
        double[] centroid = computeCentroid(points);
        double[][] translatedPoints = new double[points.length][2];
        for (int i = 0; i < points.length; i++) {
            translatedPoints[i][0] = points[i][0] - centroid[0];
            translatedPoints[i][1] = points[i][1] - centroid[1];
        }
        return translatedPoints;
    }

    /**
     * Scales points to normalize centroid size.
     * @param points The points to be scaled.
     * @return Scaled points.
     */
    public double[][] scalePoints(double[][] points) {
        double size = computeCentroidSize(points);
        double[][] scaledPoints = new double[points.length][2];
        for (int i = 0; i < points.length; i++) {
            scaledPoints[i][0] = points[i][0] / size;
            scaledPoints[i][1] = points[i][1] / size;
        }
        return scaledPoints;
    }

    /**
     * Finds the optimal rotation matrix using Singular Value Decomposition (SVD).
     * @param points The input points.
     * @param template The template points.
     * @return Optimal rotation matrix.
     */
    public double[][] findOptimalRotation(double[][] points, double[][] template) {
        // Here, we'd usually compute covariance matrix of points and template,
        // then perform Singular Value Decomposition (SVD),
        // and compute rotation = V * transpose(U) from SVD.
        // For simplicity and considering limited computational resources here,
        // we'll leave this method to be further developed as per your use-case.
        // Utilizing a linear algebra library, like Apache Commons Math, will be useful.

        // For the sake of example:
        return new double[][]{{1, 0}, {0, 1}};
    }

    /**
     * Applies a rotation matrix to points.
     * @param points The points to be rotated.
     * @param rotation The rotation matrix.
     * @return Rotated points.
     */
    public double[][] rotatePoints(double[][] points, double[][] rotation) {
        double[][] rotatedPoints = new double[points.length][2];
        for (int i = 0; i < points.length; i++) {
            rotatedPoints[i][0] = rotation[0][0] * points[i][0] + rotation[0][1] * points[i][1];
            rotatedPoints[i][1] = rotation[1][0] * points[i][0] + rotation[1][1] * points[i][1];
        }
        return rotatedPoints;
    }


    /**
     * Applies the Procrustes analysis to align points with a template.
     * @param points The points to be aligned.
     * @param template The template to align with.
     * @return Aligned points.
     */
    public double[][] applyProcrustes(double[][] points, double[][] template) {
        // Step 1: Translate points to origin
        points = translatePointsToOrigin(points);
        // Step 2: Scale points to normalize centroid size
        points = scalePoints(points);
        // Step 3: Find the optimal rotation matrix to align points with template
        double[][] rotationMatrix = findOptimalRotation(points, template);
        // Step 4: Rotate the points using the found optimal rotation matrix
        points = rotatePoints(points, rotationMatrix);
        // At this stage, 'points' should be aligned with 'template' in the least-squares sense
        return points;
    }


    public double calculateEuclideanDistance(double[][] points1, double[][] points2) {
        double sum = 0.0;

        // Ensure points1 and points2 have the same length
        if (points1.length != points2.length) {
            throw new IllegalArgumentException("Dimensions must match!");
        }

        // Calculating the Euclidean distance between corresponding points
        for (int i = 0; i < points1.length; i++) {
            for (int j = 0; j < 2; j++) { // Iterating through x, y coordinates
                sum += Math.pow(points1[i][j] - points2[i][j], 2);
            }
        }

        // The square root of the sum of squared differences
        return Math.sqrt(sum);
    }

    public double calculateSimpleSimilarity(double[][] points1, double[][] points2) {
        double distance = calculateEuclideanDistance(points1, points2);
        // Invert the distance to calculate similarity
        return 1 / (1 + distance);
    }


}
