package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void log(Object args) {
        System.out.println(args.toString());
    }

    public static int t = 0;

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

    public static void preprocess(ArrayList<int[]> g, ArrayList<int[]> grev) {
        int[] args = new int[g.size()];
        int[] linked = new int[g.size()];
        for (int i = 0; i < g.size(); i++) {
            args[i] = g.get(i)[1];
            linked[i] = g.get(i)[0];
        }
        partitionlinked(args, 0, args.length, linked);
        for (int i = 0; i < g.size(); i++) {
            grev.add(i, new int[]{linked[i], args[i]});
//            grev.get(i)[1] = args[i];
//            grev.get(i)[0] = linked[i];
        }
    }

    public static void preprocesssecond(ArrayList<int[]> g, ArrayList<int[]> gsort) {
        int[] args = new int[g.size()];
        int[] linked = new int[g.size()];
        for (int i = 0; i < g.size(); i++) {
            args[i] = g.get(i)[0];
            linked[i] = g.get(i)[1];
        }
        partitionlinked(args, 0, args.length, linked);
        for (int i = 0; i < g.size(); i++) {
            gsort.add(i, new int[]{args[i],linked[i]});
        }
    }

    public static void dfsloop(ArrayList<int[]> g, int[] scc) {
        log("Start dfs loop");
        int s;
        //get the # of nodes
        int n = g.get(g.size() - 1)[0];
        for (int[] aG : g) {
            n = Math.max(n, aG[1]);
        }
        log("number of nodes, n=" + n);
        int[] exp = new int[n];
        int[] finish = new int[n];
        Arrays.fill(exp, -1);
        Arrays.fill(finish, -1);

        ArrayList<int[]> grev = new ArrayList<>();
        ArrayList<int[]> gsort = new ArrayList<>();
        log("grev initial size:" + grev.size());
        preprocess(g, grev);
        log("preprocessing comlete - " + grev.get(grev.size() - 1)[1]);

        System.out.println("grev:");
        for (int[] aG : grev) {
            System.out.println(Arrays.toString(aG));
        }

        //First pass
        log("START FIRST PASS");
        for (int i = n; i > 0; i--) {
//            howfar(exp);
            if (exp[i - 1] < 0) {
                s = i;
                //System.out.println(Arrays.toString(exp)+" and i="+i);
                dfs(grev, i, s, exp, finish, "rev");
            }
        }

        log("");
        preprocesssecond(g, gsort);
        System.out.println(Arrays.toString(finish));
        System.out.println("g:");
        for (int[] aG : gsort) {
            System.out.println(Arrays.toString(aG));
        }
        //Second pass
        log("START SECOND PASS");
        Arrays.fill(exp, -1);
        int fstart;
        int j;
        for (int i = n; i > 0; i--) {

//            howfar(exp);

            //log("i is currently "+i);
            j = 0;
            while (finish[j] != i) {
                //log("finish[j]="+finish[j]+" and i="+i);
                j++;
            }
            fstart = j + 1;
            if (exp[fstart - 1] < 0) {
                s = fstart;
                //System.out.println(Arrays.toString(exp)+" and i="+i);
                //log("Start dfs from outerloop");
                dfs(gsort, fstart, s, exp, finish, "fwd");
            }
        }
        countscc(exp, scc);
        System.out.println(Arrays.toString(exp));
    }

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
        //System.out.println("SCC="+Arrays.toString(scc));


    }


    public static void dfs(ArrayList<int[]> g, int i, int s, int[] exp, int[] finish, String dir) {
        log("Start dfs with i=" + i + " and s=" + s);
        exp[i - 1] = s;
        int first;
        int last;
        int len = g.size();
        //System.out.println(Arrays.toString(exp));
        if (dir.equals("rev")) {
            first = 1;
            last = 0;
        } else {
            first = 0;
            last = 1;
        }

        int head;

        int locate = 0;
        while (g.get(locate)[first] != i) {
            locate++;
            if (locate >= len) {
                break;
            }
        }
        log("locate = " + locate);
        int ch;
        if (locate < len) {
            while (g.get(locate)[first] == i) {
//        for (int j = locate; j < g.size(); j++) {
                ch = g.get(locate)[first];
                log("loop is searching for " + i + "==" + ch);
//            if (g.get(j)[first] == i) {
                head = g.get(locate)[last];
                //log("head="+head);
                //log("The vector "+g.get(j)[0]+"->"+head);
                if (exp[head - 1] < 0) {
                    //log("recurse on dfs " + head);
                    dfs(g, head, s, exp, finish, dir);
                }
//
                locate++;
                if (locate >= len) {
                    break;
                }
            }
        }


        Main.t++;
        if (dir.equals("rev")) {
            finish[i - 1] = Main.t;
        }
        /*
        if (dir.equals("rev")) {
            //System.out.println("Finish = " + Arrays.toString(finish));
        } else {
            //System.out.println("Exp = " + Arrays.toString(exp));
        }
        */
        //log("end of dfs");
    }

    public static void testcase(String datafile, int[] answer) throws IOException {
        ArrayList<int[]> edges = new ArrayList<>();

        long startparse = System.nanoTime();

        parsedata(edges, datafile);

        long endparse = System.nanoTime();
        long parseduration = (endparse - startparse);
        log("Parse ran for " + parseduration / 1000000 + " milliseconds");

        Main.t = 0;
        dfsloop(edges, answer);
        System.out.println("The answer to " + datafile + " is " + Arrays.toString(answer));
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        log("Begin Program");
        int[] answer = new int[5];
        testcase("testcase3", answer);
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

    public static void partitionlinked(int[] args, int l, int r, int[] linked) {
        int p;
        int i;
        int temp;

        if (r - l > 1) {
            p = args[l];

            i = l + 1;
            for (int j = i; j < r; j++) {
                if (args[j] < p) {
                    temp = args[j];
                    args[j] = args[i];
                    args[i] = temp;

                    temp = linked[j];
                    linked[j] = linked[i];
                    linked[i] = temp;

                    i++;
                }
            }
            temp = args[i - 1];
            args[i - 1] = args[l];
            args[l] = temp;

            temp = linked[i - 1];
            linked[i - 1] = linked[l];
            linked[l] = temp;

            //Partition on the left
            partitionlinked(args, l, i - 1, linked);
            //Partition on the right
            partitionlinked(args, i, r, linked);
        }
    }

}

