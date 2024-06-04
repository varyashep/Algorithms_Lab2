package org.example;

import java.util.ArrayList;

public class RLE {

    public static ArrayList<Integer> getRle(ArrayList<Integer> zigzag) {
        ArrayList<Integer> rle = new ArrayList<>();
        int count = 1;

        for (int i = 1; i < zigzag.size(); i++) {
            if (zigzag.get(i) == zigzag.get(i-1)) {
                count++;
            } else {
                rle.add(zigzag.get(i-1));
                rle.add(count);
                count = 1;
            }
        }

        rle.add(zigzag.get(zigzag.size() - 1));
        rle.add(count);

        return rle;
    }

    public static int[] getBackRle(ArrayList<Integer> rle) {
        int size = 0;
        for (int i = 0; i < rle.size(); i += 2) {
            size += rle.get(i + 1);
        }

        int[] zigzag = new int[size];
        int index = 0;

        for (int i = 0; i < rle.size(); i += 2) {
            int num = rle.get(i);
            int count = rle.get(i + 1);
            for (int j = 0; j < count; j++) {
                zigzag[index++] = num;
            }
        }

        return zigzag;
    }

}
