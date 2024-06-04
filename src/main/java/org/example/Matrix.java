package org.example;

import java.util.ArrayList;

public class Matrix {
//    public static ArrayList<Integer> zigZag(int[][] matrix) {
//        ArrayList<Integer> result = new ArrayList<>();
//       int n = matrix.length;
//       int d = 0;
//       int row = 0;
//       int col = 0;
//       while (d < n) {
//           if (d % 2 == 0) {
//               while (row >= 0) {
//                   result.add(matrix[row][col]);
//                   row--;
//                   col++;
//               }
//               row = 0;
//           } else {
//               while (col >= 0) {
//                   result.add(matrix[row][col]);
//                   row++;
//                   col--;
//               }
//               col = 0;
//           }
//           d++;
//       }
//        if (d % 2 == 0) {
//            row--;
//            col++;
//        }
//        else {
//            row++;
//            col--;
//        }
//
//           while (d < n*2 + 1) {
//               if (d % 2 != 0) {
//                    while (col >= d - n + 1) {
//                        //result.add(row + " " + col);
//                        col--;
//                        if (row < n-1) {
//                            row++;
//                        }
//                    }
//                   col = d - n + 2;
//               }
//               else {
//                   while (row >= d - n + 1) {
//                       //result.add(row + " " + col);
//                       result.add(matrix[row][col]);
//                       row--;
//                       if (col < n - 1) {
//                           col++;
//                       }
//                   }
//                   row = d - n + 2;
//               }
//               d++;
//           }
//
//        return result;
//    }
//
//    public static int[][] inverseZigZag(ArrayList<Integer> zigZagList) {
//        int size = zigZagList.size();
//        int n = (int) Math.sqrt(size);
//        int[][] matrix = new int[n][n];
//        int d = 0;
//        int row = 0;
//        int col = 0;
//        int index = 0;
//
//        while (d < n) {
//            if (d % 2 == 0) {
//                while (row < n) {
//                    matrix[row][col] = zigZagList.get(index++);
//                    if (row > 0) {
//                        row--;
//                        col++;
//                    } else {
//                        break;
//                    }
//                }
//                row++;
//            } else {
//                while (col < n) {
//                    matrix[row][col] = zigZagList.get(index++);
//                    if (col > 0) {
//                        row++;
//                        col--;
//                    } else {
//                        break;
//                    }
//                }
//                col++;
//            }
//            d++;
//        }
//
//        return matrix;
//    }




    public static ArrayList<Integer> zigZag(int[][] array) {
        ArrayList<Integer> zigzagResult = new ArrayList<>();
        int rows = array.length;
        int cols = array[0].length;
        int total = rows + cols - 1;

        for (int currentSum = 0; currentSum < total; currentSum++) {
            if (currentSum % 2 == 0) {
                for (int i = Math.min(currentSum, rows - 1); i >= Math.max(0, currentSum - cols + 1); i--) {
                    zigzagResult.add(array[i][currentSum - i]);
                }
            } else {
                for (int i = Math.max(0, currentSum - cols + 1); i <= Math.min(currentSum, rows - 1); i++) {
                    zigzagResult.add(array[i][currentSum - i]);
                }
            }
        }

        return zigzagResult;
    }


    public static int[][] inverseZigZag(ArrayList<Integer> zigzag, int rows, int cols) {
        int[][] array = new int[rows][cols];
        int index = 0;
        int n = rows;
        int m = cols;

        for (int sum = 0; sum <= n + m - 2; sum++) {
            if (sum % 2 == 0) {
                for (int i = Math.min(sum, n - 1); i >= Math.max(0, sum - m + 1); i--) {
                    array[i][sum - i] = zigzag.get(index++);
                }
            } else {
                for (int i = Math.max(0, sum - m + 1); i <= Math.min(sum, n - 1); i++) {
                    array[i][sum - i] = zigzag.get(index++);
                }
            }
        }
        return array;
    }

}
