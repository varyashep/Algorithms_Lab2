package org.example;

public class DCT {

    public static float[][] applyDCT(int[][] pixels) {
        int height = pixels.length;
        int width = pixels[0].length;
        float[][] dctCoeff = new float[height][width];

        for (int u = 0; u < height; u++) {
            for (int v = 0; v < width; v++) {
                double sum = 0;
                for (int x = 0; x < height; x++) {
                    for (int y = 0; y < width; y++) {
                        double a = Math.cos(((2*x + 1) * u * Math.PI) / (2 * height));
                        double b = Math.cos(((2*y + 1) * v * Math.PI) / (2*width));
                        sum += pixels[x][y] * a * b;
                    }
                }
                double cu = u == 0 ? 1.0 / Math.sqrt(2) : 1.0;
                double cv = v == 0 ? 1.0 / Math.sqrt(2) : 1.0;
                dctCoeff[u][v] = (float) (2.0 / Math.sqrt(height * width) * cu * cv * sum);
            }
        }

        return dctCoeff;
    }

    public static int[][] applyIDCT(float[][] dctCoeff) {
        int height = dctCoeff.length;
        int width = dctCoeff[0].length;
        int[][] pixels = new int[height][width];

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                double sum = 0;
                for (int u = 0; u < height; u++) {
                    for (int v = 0; v < width; v++) {
                        double cu = u == 0 ? 1.0 / Math.sqrt(2) : 1.0;
                        double cv = v == 0 ? 1.0 / Math.sqrt(2) : 1.0;
                        double a = Math.cos(((2 * x + 1) * u * Math.PI) / (2 * height));
                        double b = Math.cos(((2 * y + 1) * v * Math.PI) / (2 * width));
                        sum += cu * cv * dctCoeff[u][v] * a *b;
                    }
                }
                pixels[x][y] = (int) Math.round(0.25 * sum);
            }
        }
        return pixels;
    }

    public static int[][] generateQuantizationMatrix(int Q) {

        int[][] standardQuantizationMatrix = {
                {16, 11, 10, 16, 24, 40, 51, 61},
                {12, 12, 14, 19, 26, 58, 60, 55},
                {14, 13, 16, 24, 40, 57, 69, 56},
                {14, 17, 22, 29, 51, 87, 80, 62},
                {18, 22, 37, 56, 68, 109, 103, 77},
                {24, 35, 55, 64, 81, 104, 113, 92},
                {49, 64, 78, 87, 103, 121, 120, 101},
                {72, 92, 95, 98, 112, 100, 103, 99}
        };

        int scaleFactor;
        if (Q > 50) {
            scaleFactor = 200 - 2*Q;
        } else {
            scaleFactor = 5000 / Q;
        }

        int[][] quantizationMatrix = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                quantizationMatrix[i][j] = (standardQuantizationMatrix[i][j] * scaleFactor + 50) / 100;
            }
        }

        return quantizationMatrix;
    }

    public static int[][] quantizeDCTCoefficients(float[][] dctCoefficients, int[][] quantizationMatrix) {
        int height = dctCoefficients.length;
        int width = dctCoefficients[0].length;
        int[][] quantizedCoefficients = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                quantizedCoefficients[i][j] = (int) (dctCoefficients[i][j] / quantizationMatrix[i][j]);
            }
        }

        return quantizedCoefficients;
    }

    public static float[][] dequantizeDCTCoefficients(int[][] quantizedCoefficients, int[][] quantizationMatrix) {
        int height = quantizedCoefficients.length;
        int width = quantizedCoefficients[0].length;
        float[][] dequantizedCoefficients = new float[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                dequantizedCoefficients[i][j] = quantizedCoefficients[i][j] * quantizationMatrix[i][j];
            }
        }

        return dequantizedCoefficients;
    }

}
