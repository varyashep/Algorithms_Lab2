package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ColorConversion {

    public static int[][][] getRGB(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] rgb = new int[width][height][3];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x,y));
                rgb[x][y][0]= color.getRed();
                rgb[x][y][1]= color.getGreen();
                rgb[x][y][2]= color.getBlue();
            }
        }
        return rgb;
    }

    public static int[][][] getYCbCr(String path, int[][][] rgb) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] YCbCr = new int[width][height][3];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int r = rgb[x][y][0];
                int g = rgb[x][y][1];
                int b = rgb[x][y][2];
                YCbCr[x][y][0] = (int) Math.min(Math.max(0, Math.round(0.299 * r + 0.587 * g + 0.114 * b)), 255);
                YCbCr[x][y][1] = (int) Math.min(Math.max(0, Math.round((-0.299 * r - 0.587*g + 0.886 * b)/1.772 + 128)), 255);
                YCbCr[x][y][2] = (int) Math.min(Math.max(0, Math.round((0.701 * r - 0.587 * g - 0.114 * b)/1.402 + 128)), 255);
            }
        }

        return YCbCr;
    }

    public static int[][][] getRGB(String path, int[][][] YCbCr) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] rgb = new int[width][height][3];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int Y = YCbCr[x][y][0];
                int Cb = YCbCr[x][y][1];
                int Cr = YCbCr[x][y][2];

                // Обратное преобразование из YCbCr в RGB
                int r = (int) Math.min(Math.max(0, Math.round(Y + 1.402 * (Cr - 128))), 255);
                int g = (int) Math.min(Math.max(0, Math.round(Y - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128))), 255);
                int b = (int) Math.min(Math.max(0, Math.round(Y + 1.772 * (Cb - 128))), 255);
//                int r = (int) (Y + 1.402 * (Cr - 128));
//                int g = (int) (Y - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128));
//                int b = (int) (Y + 1.772 * (Cb - 128));

                rgb[x][y][0] = r;
                rgb[x][y][1] = g;
                rgb[x][y][2] = b;
            }
        }

        return rgb;
    }

    public static int[][] imageToArray(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));

        int width = image.getWidth();
        int height = image.getHeight();

        int[][] pixelArray = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelArray[y][x] = image.getRGB(x,y);
            }
        }

        return pixelArray;
    }

    public static void arrayToImage(int[][] pixelArray, String outputPath) throws IOException {

        int width = pixelArray[0].length;
        int height = pixelArray.length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, pixelArray[y][x]);
            }
        }

        ImageIO.write(image, "jpg", new File(outputPath));
    }


    public static void convertToImage(int[][][] array, String outputPath) throws IOException {
        int width = array.length;
        int height = array[0].length;
        int channels = array[0][0].length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = array[x][y][0];
                int green = array[x][y][1];
                int blue = array[x][y][2];

                Color color = new Color(red, green, blue);
                image.setRGB(x, y, color.getRGB());
            }
        }

        ImageIO.write(image, "jpg", new File(outputPath));
    }

    public static int[][][] combineArrays(int[][] array0, int[][] array1, int[][] array2) {
        int depth = array0.length;
        int height = array0[0].length;

        int[][][] combinedArray = new int[depth][height][3];

        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < height; j++) {
                combinedArray[i][j][0] = array0[i][j];
                combinedArray[i][j][1] = array1[i][j];
                combinedArray[i][j][2] = array2[i][j];
            }
        }

        return combinedArray;
    }

}
