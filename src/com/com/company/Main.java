package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {

    public static void log(Object args) {
        System.out.println(args.toString());
    }


    public static void testcase(String datafile, int[] answer) {
        int[] a = new int[5];
        switch (datafile) {
            case "testcase1":
                a = new int[]{3, 3, 3, 0, 0};
                System.arraycopy(a, 0, answer, 0, a.length);
                break;
            default:
                a = new int[]{2, 3, 3, 0, 0};
                System.arraycopy(a, 0, answer, 0, a.length);
                break;
        }
        //System.out.println(Arrays.toString(answer));
    }

    public static void main(String[] args) throws IOException {
        log("Begin Program");
        int[] answer = new int[5];
        testcase("testcase1", answer);
        testcase("", answer);
        ArrayList<int[]> edges = new ArrayList<>();
        parsedata(edges, "testcase1");
        log("End Program");
    }

    public static void parsedata(ArrayList<int[]> edges, String datafile) throws IOException {
        String[] arr = data("testcase1");

        for (int i = 0; i < arr.length; i++) {
            String[] temp;
            temp = arr[i].split("\\s+");
            int[] temparr = new int[2];
            for (int j = 0; j < temp.length; j++) {
                temparr[j] = Integer.valueOf(temp[j]);
            }
            edges.add(temparr);
        }
    }

    public static String[] data(String datafile) throws IOException {
        //System.out.println("Opening " + datafile + ".");
        //Name of the file
        String filePath = new File("").getAbsolutePath();
        String fullPath = filePath + "/src/Files/" + datafile + ".txt";
        //ArrayList<String> integers = new ArrayList<String>();
        //ArrayList<Integer> ints = new ArrayList<Integer>();

        LineNumberReader lnr = new LineNumberReader(new FileReader(new File(fullPath)));
        lnr.skip(Long.MAX_VALUE);
        //System.out.println(lnr.getLineNumber());
        //Add 1 because line index starts at 0
        // Finally, the LineNumberReader object should be closed to prevent resource leak
        String[] arr = new String[lnr.getLineNumber() + 1];
        lnr.close();
        try {
            //Create object of FileReader
            FileReader inputFile = new FileReader(filePath + "/src/Files/" + datafile + ".txt");
            //Instantiate the BufferedReader Class
            BufferedReader bufferReader = new BufferedReader(inputFile);
            //Variable to hold the one line data
            String line;
            int i;
            i = 0;
            // Read file line by line and print on the console
            while ((line = bufferReader.readLine()) != null) {
                arr[i] = line;
                i++;
            }
            //Close the buffer reader
            bufferReader.close();
        } catch (Exception e) {
            System.out.println("Error while reading file line by line:" + e.getMessage());
        }

        return arr;
    }
}
