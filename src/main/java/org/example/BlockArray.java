package org.example;

import java.util.ArrayList;
import java.util.List;

public class BlockArray {

    public static List<int[][]> divideIntoBlocks(int[][] array) {
        List<int[][]> blocks = new ArrayList<>();
        int height = array.length;
        int width = array[0].length;

        for (int i = 0; i < height; i += 8) {
            for (int j = 0; j < width; j += 8) {
                int[][] block = new int[8][8];
                for (int k = 0; k < 8 && i + k < height; k++) {
                    for (int l = 0; l < 8 && j + l < width; l++) {
                        block[k][l] = array[i + k][j + l];
                    }
                }
                blocks.add(block);
            }
        }

        return blocks;
    }

    public static List<float[][]> divideIntoBlocksFloat(float[][] array) {
        List<float[][]> blocks = new ArrayList<>();
        int height = array.length;
        int width = array[0].length;

        for (int i = 0; i < height; i += 8) {
            for (int j = 0; j < width; j += 8) {
                float[][] block = new float[8][8];
                for (int k = 0; k < 8 && i + k < height; k++) {
                    for (int l = 0; l < 8 && j + l < width; l++) {
                        block[k][l] = array[i + k][j + l];
                    }
                }
                blocks.add(block);
            }
        }

        return blocks;
    }

    public static float[][] combineBlocksFloat(List<float[][]> blocks, int originalHeight, int originalWidth) {
        float[][] combinedArray = new float[originalHeight][originalWidth];
        int blockIndex = 0;

        for (int i = 0; i < originalHeight; i += 8) {
            for (int j = 0; j < originalWidth; j += 8) {
                if (blockIndex < blocks.size()) {
                    float[][] block = blocks.get(blockIndex);
                    for (int k = 0; k < 8 && i + k < originalHeight; k++) {
                        for (int l = 0; l < 8 && j + l < originalWidth; l++) {
                            combinedArray[i + k][j + l] = block[k][l];
                        }
                    }
                    blockIndex++;
                }
            }
        }

        return combinedArray;
    }

    public static int[][] combineBlocks(List<int[][]> blocks, int originalHeight, int originalWidth) {
        int[][] combinedArray = new int[originalHeight][originalWidth];
        int blockIndex = 0;

        for (int i = 0; i < originalHeight; i += 8) {
            for (int j = 0; j < originalWidth; j += 8) {
                if (blockIndex < blocks.size()) {
                    int[][] block = blocks.get(blockIndex);
                    for (int k = 0; k < 8 && i + k < originalHeight; k++) {
                        for (int l = 0; l < 8 && j + l < originalWidth; l++) {
                            combinedArray[i + k][j + l] = block[k][l];
                        }
                    }
                    blockIndex++;
                }
            }
        }

        return combinedArray;
    }

    public static int[][][] convertTo3DArray(int[][] array1, int[][] array2, int[][] array3) {

        int depth = array1.length;
        int height = array1[0].length;

        // Создаем трехмерный массив
        int[][][] result = new int[depth][height][3];

        // Копируем значения из исходных массивов в трехмерный массив
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j][0] = array1[i][j];
                result[i][j][1] = array2[i][j];
                result[i][j][2] = array3[i][j];
            }
        }

        return result;
    }
}
