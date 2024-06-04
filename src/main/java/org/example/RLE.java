package org.example;

import java.util.ArrayList;

public class RLE {


    public static String getRleString(ArrayList<Integer> zigzag) {
        StringBuilder rle = new StringBuilder();
        int count = 1;

        for (int i = 1; i < zigzag.size(); i++) {
            if (zigzag.get(i) == zigzag.get(i-1)) {
                count++;
            } else {
                rle.append(zigzag.get(i-1)).append(' ').append(count).append(' ');
                count = 1;
            }
        }

        rle.append(zigzag.get(zigzag.size() - 1)).append(' ').append(count);

        return rle.toString();
    }

    public static ArrayList<Integer> getBackRleString(String rleString) {
        ArrayList<Integer> rle = new ArrayList<>();
        String[] tokens = rleString.split(" ");

        for (int i = 0; i < tokens.length; i += 2) {
            int value = Integer.parseInt(tokens[i]);
            int count = Integer.parseInt(tokens[i + 1]);

            for (int j = 0; j < count; j++) {
                rle.add(value);
            }
        }

        return rle;
    }

}
