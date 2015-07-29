package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Main {

    public static void log(Object args) {
        System.out.println(args.toString());
    }

    public static int t = 0;

    public static void countscc(int[] exp, int[] scc) {
        //System.out.println(Arrays.toString(exp));
        int[] count = new int[exp.length];
        for (int i = 0; i < count.length; i++) {
            count[exp[i] - 1]++;
        }
        //System.out.println(Arrays.toString(count));
        partition(count, "mot", 0, count.length);
        //System.out.println(Arrays.toString(count));
        scc[0] = count[count.length - 1];
        scc[1] = count[count.length - 2];
        scc[2] = count[count.length - 3];
        scc[3] = count[count.length - 4];
        scc[4] = count[count.length - 5];
        System.out.println("SCC=" + Arrays.toString(scc));


    }

    public static void howfar(int[] exp) {
        double explored = 0;
        double percentexp;
        for (int anExp : exp) {
            if (anExp > 0) {
                explored++;
            }
        }
        percentexp = (explored / exp.length * 100);
        log(String.format("%.2f", percentexp) + "% loop completed. " + (int) explored + "/" + exp.length);

    }

    public static void dfsOnce(ArrayList<int[]> g, int[] answer) {
        int v;
        int temp;
        int n = g.get(g.size() - 1)[0];
        for (int[] aG : g) {
            n = Math.max(n, aG[1]);
        }
        Stack stack = new Stack();
        int[] exp = new int[n];
        int[] expgray = new int[n];
        int[] finish = new int[n];
//        Arrays.fill(exp, -1);
        log(n + " = size");
        int time = 0;
        for (int i = n; i > 0; i--) {
            Arrays.fill(exp, -1);
            exp[i - 1] = 1;
            //log(Arrays.toString(exp));
            stack.push(i);
            time = 1;
            log(stack);
//            System.arraycopy(exp, 0, expgray, 0, exp.length);
            while (!stack.empty()) {
//                log(Arrays.toString(exp));
                v = (int) stack.pop();
                time++;
//                log(v);
//                log(stack);
                for (int j = 0; j < g.size(); j++) {
//                    log("does "+g.get(j)[1]+"=="+v);
                    if (v == g.get(j)[1]) {
                        if ((exp[g.get(j)[0] - 1]) < 0) {
                            time++;
                            exp[g.get(j)[0] - 1] = 1;
                            stack.push(g.get(j)[0]);
                        }
                    }

                    finish[g.get(i)[1] - 1] = time;
                }
//                log("Stack at the end = "+stack);
            }
        }
        log(Arrays.toString(finish) + " = Finish");
    }

    public static void testcase(String datafile, int[] answer) throws IOException {
        ArrayList<int[]> edges = new ArrayList<>();

        long startparse = System.nanoTime();

        parsedata(edges, datafile);

        long endparse = System.nanoTime();
        long parseduration = (endparse - startparse);
        log("Parse ran for " + parseduration / 1000000 + " milliseconds");

//        for (int[] edge : edges) {
//            log(Arrays.toString(edge));
//        }

        Main.t = 0;
        log("Run dfsOnce");
        dfsOnce(edges, answer);
        //System.out.println("The answer to " + datafile + " is " + Arrays.toString(answer));
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        log("Begin Program");
        int[] answer = new int[5];
        testcase("testcase1", answer);
        log("End Program");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        log("Program ran for " + duration / 1000000 + " milliseconds");
    }

    public static void parsedata(ArrayList<int[]> edges, String datafile) throws IOException {
        String[] arr = data(datafile);

        for (String anArr : arr) {
            String[] temp;
            temp = anArr.split("\\s+");
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
        LineNumberReader lnr = new LineNumberReader(new FileReader(new File(fullPath)));
        lnr.skip(Long.MAX_VALUE);
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

    public static void partition(int[] args, String choose, int l, int r) {
        int p;
        int i;
        int temp;
        int mid;


        if (r - l > 1) {
            switch (choose) {
                case "first":
                    p = args[l];
                    break;
                case "last":
                    p = args[r - 1];
                    args[r - 1] = args[l];
                    args[l] = p;
                    break;
                case "mot":
                    //find the middle
                    if (((r - l) & 1) == 0) {
                        mid = l + ((r - l) / 2) - 1;
                    } else {
                        mid = l + (r - l - 1) / 2;
                    }
                    if (args[l] > args[mid]) {
                        if (args[mid] > args[r - 1]) {
                            p = args[mid];
                            args[mid] = args[l];
                            args[l] = p;
                        } else if (args[l] > args[r - 1]) {
                            p = args[r - 1];
                            args[r - 1] = args[l];
                            args[l] = p;
                        } else {
                            p = args[l];
                        }
                    } else {
                        if (args[l] > args[r - 1]) {
                            p = args[l];
                        } else if (args[mid] > args[r - 1]) {
                            p = args[r - 1];
                            args[r - 1] = args[l];
                            args[l] = p;
                        } else {
                            p = args[mid];
                            args[mid] = args[l];
                            args[l] = p;
                        }
                    }
                    break;
                default:
                    p = args[l];
                    break;
            }
            i = l + 1;
            for (int j = i; j < r; j++) {
                if (args[j] < p) {
                    temp = args[j];
                    args[j] = args[i];
                    args[i] = temp;
                    i++;
                }
            }
            temp = args[i - 1];
            args[i - 1] = args[l];
            args[l] = temp;

            //Partition on the left
            partition(args, choose, l, i - 1);
            //Partition on the right
            partition(args, choose, i, r);
        }

    }
}
