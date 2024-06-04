package org.example;

import java.awt.*;
import java.awt.image.SampleModel;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("cat.jpg");
        File file1 = new File("C:/Users/user/Documents/ETU/ETUsem4/Algorithms/Lab2/untitled/initial.txt");
        File initImage = new File("C:/Users/user/Documents/ETU/ETUsem4/Algorithms/Lab2/untitled/cat.jpg");
        float initImageSize = initImage.length();
        System.out.println(initImageSize);
        Path outputPath = Paths.get("C:/Users/user/Documents/image.jpg");
        File file2 = new File("C:/Users/user/Documents/ETU/ETUsem4/Algorithms/Lab2/untitled/transform.txt");


        int[][][] rgb = ColorConversion.getRGB(String.valueOf(path));
        Save.write3DArrayToFile(rgb, "initial.txt");
        System.out.println("Initial size (bits): " + file1.length()*8);
        float initSize = file1.length()*8;
        int[][][] YCbCr = ColorConversion.getYCbCr(String.valueOf(path), rgb);
        Save.write3DArrayToFile(YCbCr, "transform.txt");
        System.out.println("Size after YCbCr (bits): " + file2.length()*8);
        //ColorConversion.convertToImage(YCbCr, String.valueOf(outputPath));
        int[][] Y = Matrix.split3DArray(YCbCr, 1);
        int[][] downsampledCb = Sampling.downsampling(Matrix.split3DArray(YCbCr, 2), 2, 2, 2);
        int[][] downsampledCr = Sampling.downsampling(Matrix.split3DArray(YCbCr, 3), 2, 2, 2);

        Save.writeThree2DArraysToFile(Y, downsampledCb, downsampledCr, "transform.txt");
        System.out.println("Size after Downsampling (bits): " + file2.length()*8);

        List<int[][]> blocksY = BlockArray.divideIntoBlocks(Y);
        List<int[][]> blocksCb = BlockArray.divideIntoBlocks(downsampledCb);
        List<int[][]> blocksCr = BlockArray.divideIntoBlocks(downsampledCr);


        List<float[][]> DCT_Y = new ArrayList<>();
        List<float[][]> DCT_Cb = new ArrayList<>();
        List<float[][]> DCT_Cr = new ArrayList<>();
        float[][] coeffs;
        for (int i = 0; i < blocksY.size(); i++) {
            coeffs = DCT.applyDCT(blocksY.get(i));
            DCT_Y.add(coeffs);
        }
        float[][] Y_DCT = BlockArray.combineBlocksFloat(DCT_Y, Y.length, Y[0].length);

        for (int i = 0; i < blocksCb.size(); i++) {
            coeffs = DCT.applyDCT(blocksCb.get(i));
            DCT_Cb.add(coeffs);
        }
        float[][] Cb_DCT = BlockArray.combineBlocksFloat(DCT_Cb, downsampledCb.length, downsampledCb[0].length);

        for (int i = 0; i < blocksCr.size(); i++) {
            coeffs = DCT.applyDCT(blocksCr.get(i));
            DCT_Cr.add(coeffs);
        }
        float[][] Cr_DCT = BlockArray.combineBlocksFloat(DCT_Cr, downsampledCr.length, downsampledCr[0].length);

        Save.writeThree2DArraysToFileFloat(Y_DCT, Cb_DCT, Cr_DCT, "transform.txt");

        System.out.println("Size after DCT (bits): " + file2.length()*8);


        int[][] quantizationMatrix = DCT.generateQuantizationMatrix(100);
        List<float[][]> q_blocksY = BlockArray.divideIntoBlocksFloat(Y_DCT);
        List<float[][]> q_blocksCb = BlockArray.divideIntoBlocksFloat(Cb_DCT);
        List<float[][]> q_blocksCr = BlockArray.divideIntoBlocksFloat(Cr_DCT);
        List<int[][]> quantization_Y = new ArrayList<>();
        List<int[][]> quantization_Cb = new ArrayList<>();
        List<int[][]> quantization_Cr = new ArrayList<>();

        int[][] q_coeffs;
        for (int i = 0; i < q_blocksY.size(); i++) {
            q_coeffs = DCT.quantizeDCTCoefficients(q_blocksY.get(i), quantizationMatrix);
            quantization_Y.add(q_coeffs);
        }
        int[][] Y_Q = BlockArray.combineBlocks(quantization_Y, Y_DCT.length, Y_DCT[0].length);

        for (int i = 0; i < q_blocksCb.size(); i++) {
            q_coeffs = DCT.quantizeDCTCoefficients(q_blocksCb.get(i), quantizationMatrix);
            quantization_Cb.add(q_coeffs);
        }
        int[][] Cb_Q = BlockArray.combineBlocks(quantization_Cb, Cb_DCT.length, Cb_DCT[0].length);

        for (int i = 0; i < q_blocksCr.size(); i++) {
            q_coeffs = DCT.quantizeDCTCoefficients(q_blocksCr.get(i), quantizationMatrix);
            quantization_Cr.add(q_coeffs);
        }
        int[][] Cr_Q = BlockArray.combineBlocks(quantization_Cr, Cr_DCT.length, Cr_DCT[0].length);



        Save.writeThree2DArraysToFile(Y_Q, Cb_Q, Cr_Q, "transform.txt");
        System.out.println("Size after Quantization (bits): " + file2.length()*8);


        FileOutputStream fos = new FileOutputStream("transform.txt");
        DataOutputStream dos = new DataOutputStream(fos);
        ArrayList<Integer> Y_zigzag = Matrix.zigZag(Y_Q);
        ArrayList<Integer> Y_RLE = RLE.getRle(Y_zigzag);

        for (Integer value: Y_RLE) {
            dos.writeInt(value);
        }

        ArrayList<Integer> Cb_zigzag = Matrix.zigZag(Cb_Q);
        ArrayList<Integer> Cb_RLE = RLE.getRle(Cb_zigzag);

        for (Integer value: Cb_RLE) {
            dos.writeInt(value);
        }

        ArrayList<Integer> Cr_zigzag = Matrix.zigZag(Cr_Q);
        ArrayList<Integer> Cr_RLE = RLE.getRle(Cr_zigzag);

        for (Integer value: Cr_RLE) {
            dos.writeInt(value);
        }

        dos.close();


        System.out.println("Size after RLE (bits): " + file2.length()*8);

        int[] Y_backRLE = RLE.getBackRle(Y_RLE);
        ArrayList<Integer> Y_afterRLE = new ArrayList<>();
        for (int i = 0; i < Y_backRLE.length; i++) {
            Y_afterRLE.add(Y_backRLE[i]);
        }

        int[] Cb_backRLE = RLE.getBackRle(Cb_RLE);
        ArrayList<Integer> Cb_afterRLE = new ArrayList<>();
        for (int i = 0; i < Cb_backRLE.length; i++) {
            Cb_afterRLE.add(Cb_backRLE[i]);
        }

        int[] Cr_backRLE = RLE.getBackRle(Cr_RLE);
        ArrayList<Integer> Cr_afterRLE = new ArrayList<>();
        for (int i = 0; i < Cr_backRLE.length; i++) {
            Cr_afterRLE.add(Cr_backRLE[i]);
        }

        int[][] Y_invZigZag = Matrix.inverseZigZag(Y_afterRLE);
        int[][] Cb_invZigZag = Matrix.inverseZigZag(Cb_afterRLE);
        int[][] Cr_invZigZag = Matrix.inverseZigZag(Cr_afterRLE);

        List<int[][]> dq_blocksY = BlockArray.divideIntoBlocks(Y_Q);
        List<int[][]> dq_blocksCb = BlockArray.divideIntoBlocks(Cb_Q);
        List<int[][]> dq_blocksCr = BlockArray.divideIntoBlocks(Cr_Q);
        List<float[][]> dequantization_Y = new ArrayList<>();
        List<float[][]> dequantization_Cb = new ArrayList<>();
        List<float[][]> dequantization_Cr = new ArrayList<>();

        float[][] dq_coeffs;
        for (int i = 0; i < dq_blocksY.size(); i++) {
            dq_coeffs = DCT.dequantizeDCTCoefficients(dq_blocksY.get(i), quantizationMatrix);
            dequantization_Y.add(dq_coeffs);
        }
        float[][] Y_dQ = BlockArray.combineBlocksFloat(dequantization_Y, Y_Q.length, Y_Q[0].length);

        for (int i = 0; i < dq_blocksCb.size(); i++) {
            dq_coeffs = DCT.dequantizeDCTCoefficients(dq_blocksCb.get(i), quantizationMatrix);
            dequantization_Cb.add(dq_coeffs);
        }
        float[][] Cb_dQ = BlockArray.combineBlocksFloat(dequantization_Cb, Cb_Q.length, Cb_Q[0].length);

        for (int i = 0; i < dq_blocksCr.size(); i++) {
            dq_coeffs = DCT.dequantizeDCTCoefficients(dq_blocksCr.get(i), quantizationMatrix);
            dequantization_Cr.add(dq_coeffs);
        }
        float[][] Cr_dQ = BlockArray.combineBlocksFloat(dequantization_Cr, Cr_Q.length, Cr_Q[0].length);

        List<int[][]> iDCT_Y = new ArrayList<>();
        List<int[][]> iDCT_Cb = new ArrayList<>();
        List<int[][]> iDCT_Cr = new ArrayList<>();

        List<float[][]> iDCT_blocksY = BlockArray.divideIntoBlocksFloat(Y_dQ);
        List<float[][]> iDCT_blocksCb = BlockArray.divideIntoBlocksFloat(Cb_dQ);
        List<float[][]> iDCT_blocksCr = BlockArray.divideIntoBlocksFloat(Cr_dQ);

        int[][] invCoeffs;
        for (int i = 0; i < iDCT_blocksY.size(); i++) {
            invCoeffs = DCT.applyIDCT(iDCT_blocksY.get(i));
            iDCT_Y.add(invCoeffs);
        }
        int[][] Y_iDCT = BlockArray.combineBlocks(iDCT_Y, Y_dQ.length, Y_dQ[0].length);

        for (int i = 0; i < iDCT_blocksCb.size(); i++) {
            invCoeffs = DCT.applyIDCT(iDCT_blocksCb.get(i));
            iDCT_Cb.add(invCoeffs);
        }
        int[][] Cb_iDCT = BlockArray.combineBlocks(iDCT_Cb, downsampledCb.length, downsampledCb[0].length);

        for (int i = 0; i < iDCT_blocksCr.size(); i++) {
            invCoeffs = DCT.applyIDCT(iDCT_blocksCr.get(i));
            iDCT_Cr.add(invCoeffs);
        }
        int[][] Cr_iDCT = BlockArray.combineBlocks(iDCT_Cr, downsampledCr.length, downsampledCr[0].length);

        int[][] Cb_upsampled = Sampling.upsampling(Cb_iDCT, 2, 2);
        int[][] Cr_upsampled = Sampling.upsampling(Cr_iDCT, 2, 2);

        int[][][] final_YCbCr = BlockArray.convertTo3DArray(Y_iDCT, Cb_upsampled, Cr_upsampled);
        int[][][] iRGB = ColorConversion.getRGB(String.valueOf(path), final_YCbCr);


        ColorConversion.convertToImage(iRGB, String.valueOf(outputPath));

        File finalImage = new File("C:/Users/user/Documents/image.jpg");
        float finalImageSize = finalImage.length();
        System.out.println("k: " + initImageSize/finalImageSize);
    }
}