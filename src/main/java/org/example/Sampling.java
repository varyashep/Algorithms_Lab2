package org.example;

public class Sampling {

    public static int[][] downsampling(int[][] image, double Cy, double Cx, int type) {
        // 1 - Удаление строк и столбцов
        // 2 - Замена блока пикселем с средним значением цвета блока
        // 3 - Замена блока на пиксель, ближайший по значению к среднему
        // Для 2 и 3 случая Cx - ширина блока, Cy - высота блока
        int rows = image.length;
        int cols = image[0].length;
        int downsampledRows;
        int downsampledCols;
        int[][] downsampledImage;
        switch (type) {
            case 1:
                downsampledRows = (int) Math.ceil(rows/Cy);
                downsampledCols = (int) Math.ceil(cols/Cx);
                downsampledImage = new int[downsampledRows][downsampledCols];
                for (int i = 0; i < downsampledImage.length; i++) {
                    for (int j = 0; j < downsampledImage[i].length; j++) {
                        if ((int) (i*Cy) < rows && (int) (j*Cx) < cols) {
                            downsampledImage[i][j] = image[(int) (i*Cy)][(int) (j*Cx)];
                        }
                    }
                }
            break;
            case 2:
                downsampledRows = (int) Math.ceil(rows/Cy);
                downsampledCols = (int) Math.ceil(cols/Cx);
                downsampledImage = new int[downsampledRows][downsampledCols];
                for (int i = 0; i < downsampledRows; i++) {
                    for (int j = 0; j < downsampledCols; j++) {
                        int sumR = 0, sumG = 0, sumB = 0;
                        int count = 0;

                        for (int bi = 0; bi < Cy && i * Cy + bi < rows; bi++) {
                            for (int bj = 0; bj < Cx && j * Cx + bj < cols; bj++) {
                                int pixel = image[(int) (i * Cy + bi)][(int) (j * Cx + bj)];
                                sumR += (pixel >> 16) & 0xFF;
                                sumG += (pixel >> 8) & 0xFF;
                                sumB += pixel & 0xFF;
                                count++;
                            }
                        }

                        int avgR = sumR / count;
                        int avgG = sumG / count;
                        int avgB = sumB / count;

                        downsampledImage[i][j] = (avgR << 16) | (avgG << 8) | avgB;
                    }
                }
            break;
            case 3:
                downsampledRows = (int) Math.ceil(rows/Cy);
                downsampledCols = (int) Math.ceil(cols/Cx);
                downsampledImage = new int[downsampledRows][downsampledCols];

                for (int i = 0; i < downsampledRows; i++) {
                    for (int j = 0; j < downsampledCols; j++) {
                        int sum = 0;
                        int count = 0;

                        for (int bi = 0; bi < Cy && i * Cy + bi < rows; bi++) {
                            for (int bj = 0; bj < Cx && j * Cx + bj < cols; bj++) {
                                sum += image[(int) (i * Cy + bi)][(int) (j * Cx + bj)];
                                count++;
                            }
                        }

                        int avg = sum / count;

                        int nearestPixel = 0;
                        int minDiff = Integer.MAX_VALUE;
                        for (int bi = 0; bi < Cy && i * Cy + bi < rows; bi++) {
                            for (int bj = 0; bj < Cx && j * Cx + bj < cols; bj++) {
                                int diff = Math.abs(image[(int) (i * Cy + bi)][(int) (j * Cx + bj)] - avg);
                                if (diff < minDiff) {
                                    minDiff = diff;
                                    nearestPixel = image[(int) (i * Cy + bi)][(int) (j * Cx + bj)];
                                }
                            }
                        }

                        downsampledImage[i][j] = nearestPixel;
                    }
                }
            break;
            default:
               return null;
        }
        return downsampledImage;
    }

    public static int[][] upsampling(int[][] image, int Cy, int Cx) {
        int rows = image.length;
        int cols = image[0].length;

        int upsampledRows = (int) Math.ceil(rows * Cy);
        int upsampledCols = (int) Math.ceil(cols * Cx);

        int[][] upsampledImage = new int[upsampledRows][upsampledCols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int pixel = image[i][j];

                for (int bi = 0; bi < upsampledRows && i * Cy + bi < upsampledRows; bi++) {
                    for (int bj = 0; bj < upsampledCols && j * Cx + bj < upsampledCols; bj++) {
                        upsampledImage[(int) (i * Cy + bi)][(int) (j * Cx + bj)] = pixel;
                    }
                }
            }
        }
        return upsampledImage;


    }
}
