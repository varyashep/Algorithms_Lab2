package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Save {

    public static void write3DArrayToFile(int[][][] array3D, String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            // Записываем размеры массива
//            dos.writeInt(array3D.length);
//            dos.writeInt(array3D[0].length);
//            dos.writeInt(array3D[0][0].length);

            // Записываем элементы массива
            for (int[][] matrix : array3D) {
                for (int[] row : matrix) {
                    for (int value : row) {
                        dos.writeInt(value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write2DArrayToFile(int[][] array2D, String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            // Записываем размеры массива
            dos.writeInt(array2D.length);
            dos.writeInt(array2D[0].length);
            // Записываем элементы массива
            for (int[] row : array2D) {
                for (int value : row) {
                    dos.writeInt(value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeThree2DArraysToFile(int[][] array1, int[][] array2, int[][] array3, String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            // Записываем размеры массивов
//            dos.writeInt(array1.length);
//            dos.writeInt(array1[0].length);
//            dos.writeInt(array2.length);
//            dos.writeInt(array2[0].length);
//            dos.writeInt(array3.length);
//            dos.writeInt(array3[0].length);

            // Записываем элементы массивов
            write2DArrayToStream(dos, array1);
            write2DArrayToStream(dos, array2);
            write2DArrayToStream(dos, array3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeThree2DArraysToFileFloat(float[][] array1, float[][] array2, float[][] array3, String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            // Записываем размеры массивов
//            dos.writeInt(array1.length);
//            dos.writeInt(array1[0].length);
//            dos.writeInt(array2.length);
//            dos.writeInt(array2[0].length);
//            dos.writeInt(array3.length);
//            dos.writeInt(array3[0].length);

            // Записываем элементы массивов
            write2DArrayToStream(dos, array1);
            write2DArrayToStream(dos, array2);
            write2DArrayToStream(dos, array3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write2DArrayToStream(DataOutputStream dos, int[][] array) throws IOException {
        for (int[] row : array) {
            for (int value : row) {
                dos.writeInt(value);
            }
        }
    }
    private static void write2DArrayToStream(DataOutputStream dos, float[][] array) throws IOException {
        for (float[] row : array) {
            for (float value : row) {
                dos.writeFloat(value);
            }
        }
    }

    public static long getImageSizeInBits(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        BufferedImage image = ImageIO.read(imageFile);

        int width = image.getWidth();
        int height = image.getHeight();
        int bitDepth = image.getColorModel().hasAlpha() ? 32 : 24; // Если есть альфа-канал, то 32 бита на пиксель, иначе 24

        long sizeInBits = (long) width * height * bitDepth;
        return sizeInBits;
    }
}
