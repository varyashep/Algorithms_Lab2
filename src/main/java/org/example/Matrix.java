package org.example;

import java.util.ArrayList;

public class Matrix {
    public static ArrayList<Integer> zigZag(int[][] matrix) {
        ArrayList<Integer> result = new ArrayList<>();
       int n = matrix.length;
       int d = 0;
       int row = 0;
       int col = 0;
       while (d < n) {
           if (d % 2 == 0) {
               while (row >= 0) {
                   result.add(matrix[row][col]);
                   row--;
                   col++;
               }
               row = 0;
           } else {
               while (col >= 0) {
                   result.add(matrix[row][col]);
                   row++;
                   col--;
               }
               col = 0;
           }
           d++;
       }
        if (d % 2 == 0) {
            row--;
            col++;
        }
        else {
            row++;
            col--;
        }

           while (d < n*2 + 1) {
               if (d % 2 != 0) {
                    while (col >= d - n + 1) {
                        //result.add(row + " " + col);
                        col--;
                        if (row < n-1) {
                            row++;
                        }
                    }
                   col = d - n + 2;
               }
               else {
                   while (row >= d - n + 1) {
                       //result.add(row + " " + col);
                       result.add(matrix[row][col]);
                       row--;
                       if (col < n - 1) {
                           col++;
                       }
                   }
                   row = d - n + 2;
               }
               d++;
           }

        return result;
    }

    public static int[][] inverseZigZag(ArrayList<Integer> zigZagList) {
        int size = zigZagList.size();
        int n = (int) Math.sqrt(size);
        int[][] matrix = new int[n][n];
        int d = 0;
        int row = 0;
        int col = 0;
        int index = 0;

        while (d < n) {
            if (d % 2 == 0) {
                while (row < n) {
                    matrix[row][col] = zigZagList.get(index++);
                    if (row > 0) {
                        row--;
                        col++;
                    } else {
                        break;
                    }
                }
                row++;
            } else {
                while (col < n) {
                    matrix[row][col] = zigZagList.get(index++);
                    if (col > 0) {
                        row++;
                        col--;
                    } else {
                        break;
                    }
                }
                col++;
            }
            d++;
        }

        return matrix;
    }



    public static int[][] split3DArray(int[][][] YCbCr, int choice) {
        int depth = YCbCr.length;
        int height = YCbCr[0].length;
        int width = YCbCr[0][0].length;
        int[][] Y = new int[height][depth];
        int[][] Cb = new int[height][depth];
        int[][] Cr = new int[height][depth];

        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < height; j++) {
                Y[i][j] = YCbCr[i][j][0];
                Cb[i][j] = YCbCr[i][j][1];
                Cr[i][j] = YCbCr[i][j][2];
            }
        }
        switch (choice) {
            case 1:
                return Y;
            case 2:
                return Cb;
            case 3:
                return Cr;
        }
        return null;
    }

}
