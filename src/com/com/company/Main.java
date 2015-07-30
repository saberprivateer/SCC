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

    public static void dfsloop(ArrayList<int[]> g, int[] scc,int n, ArrayList<Integer> order) {
        Main.t = 0;
        log("Start dfs loop size "+n);
        int s;

        int[] exp = new int[n];
        int[] finish = new int[n];
        Arrays.fill(exp, -1);
        Arrays.fill(finish, -1);

        int convert;

        //First pass
        for (int i = n; i > 0; i--) {
            howfar(exp);
            convert = order.indexOf(i)+1;
            if (exp[convert - 1] < 0) {
                s = convert;
                //System.out.println(Arrays.toString(exp)+" and i="+i);
                dfs(g, convert, s, exp, finish);
            }
        }

        countscc(exp, scc);
//        System.out.println("Finish = " + Arrays.toString(finish));
        System.out.println("Exp = " + Arrays.toString(exp));
        for(int f=0;f<finish.length;f++){
            order.set(f,finish[f]);
        }
    }

    public static void countscc(int[] exp,int[] scc) {
        //System.out.println(Arrays.toString(exp));
        int[] count = new int[exp.length];
        for (int i=0;i<count.length;i++){
            count[exp[i]-1]++;
        }
        //System.out.println(Arrays.toString(count));
        partition(count, "mot", 0, count.length);
        //System.out.println(Arrays.toString(count));
        scc[0]=count[count.length-1];
        scc[1]=count[count.length-2];
        scc[2]=count[count.length-3];
        scc[3]=count[count.length-4];
        scc[4]=count[count.length-5];
        System.out.println("SCC="+Arrays.toString(scc));


    }


    public static void dfs(ArrayList<int[]> g, int i, int s, int[] exp, int[] finish) {
        //log("Start dfs with i=" + i + " and s=" + s);
        //mark i as explored
        //also counts as setting leader(i) = s
        exp[i - 1] = s;

        int[] loop;
//        log("loop should be: "+ Arrays.toString(g.get(i-1)));
        loop = g.get(i-1);
//        log("Loop = "+Arrays.toString(loop));

        for (int j=1;j<loop.length;j++){
//            log(j+" check exp "+Arrays.toString(exp)+" and loop[j] = "+loop[j]);
            if(exp[loop[j]-1]<0){
                dfs(g,loop[j],s,exp,finish);
            }
        }
        Main.t++;
        finish[i - 1] = Main.t;
        //log("end of dfs");
    }

    public static void testcase(String datafile, int[] answer) throws IOException {
        ArrayList<int[]> edges = new ArrayList<>();
        ArrayList<int[]> adj = new ArrayList<>();
        ArrayList<int[]> adjrev = new ArrayList<>();
        ArrayList<Integer> order = new ArrayList<>();
        long startparse = System.nanoTime();

        parsedata(edges, datafile);
        int n=maxnodes(edges);
        for (int i=0;i<n;i++){
            order.add(i,i+1);
        }

        convertToAdjacency(edges, adj, "fwd");
        convertToAdjacency(edges, adjrev,"rev");
        log("#nodes = "+n);
//        log("Final Adjacency:");
//        for (int i = 0;i<adjrev.size();i++) {
//            log(Arrays.toString(adjrev.get(i)));
//        }

        long endparse = System.nanoTime();
        long parseduration = (endparse-startparse);
        log("Pre-processing (extraction, parsing, conversion) ran for " + parseduration / 1000000 + " milliseconds");

        log("START FIRST PASS");
        dfsloop(adjrev, answer, n, order);
        log("Finishing Times:");
        log(Arrays.toString(order.toArray()));
        log("START SECOND PASS");
        dfsloop(adj,answer, n, order);
//      System.out.println("The answer to " + datafile + " is " + Arrays.toString(answer));
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        log("Begin Program");
        int[] answer = new int[5];
        testcase("SCC", answer);
        log("End Program");

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        log("Program ran for " + duration / 1000000 + " milliseconds");
    }

    public static int maxnodes(ArrayList<int[]> edges){
        //log("start maxnodes");
        //log(edges.size());
        int n = edges.get(edges.size() - 1)[0];
        //log("start iterating max nodes");
        for (int[] aG : edges) {
            n = Math.max(n, aG[1]);
        }
        return n;
    }

    public static void convertToAdjacency(ArrayList<int[]> edges,ArrayList<int[]> alist, String dir){
        log("Begin Convert to Adjacency");
        ArrayList<Integer> hold = new ArrayList<>();
        int[] converthold;
        int n = maxnodes(edges);

        //log("The max number of entries is "+n);

        int first=0;
        int last=1;

        if (dir.equals("rev")){
            first = 1;
            last = 0;
        }

        for (int i=1;i<=n;i++){
            hold.clear();
            hold.add(0, i);
            //log("hold = "+hold);
            for (int j=0;j<edges.size();j++){
                if(edges.get(j)[first]==i){
                    hold.add(edges.get(j)[last]);
                }
            }
                converthold = new int[hold.size()];
                for (int k=0;k<hold.size();k++){
                    converthold[k]=hold.get(k);
                }
                //log(Arrays.toString(converthold));
                alist.add(converthold);


        }
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
                        mid = l + ((r-l)/2) - 1;
                    } else {
                        mid = l + (r - l - 1)/2;
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
