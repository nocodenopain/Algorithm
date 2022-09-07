package lab3;

import java.io.*;
import java.util.*;

public class A {
    static int mod = (int) (1e9 + 7);
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        ArrayList<Integer>[] graph1 = new ArrayList[n + 1];
        ArrayList<Integer>[] graph2 = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph1[i] = new ArrayList<>();
            graph2[i] = new ArrayList<>();
        }
        int[] vis1 = new int[n + 1];
        int[] vis2 = new int[n + 1];
        long[] ord = new long[n + 1];
        long[] res = new long[n + 1];
        int[] in_degree = new int[n + 1];
        int[] out_degree = new int[n + 1];
        while(m -- > 0){
            int a = in.nextInt();
            int b = in.nextInt();
            graph1[a].add(b);
            graph2[b].add(a);
            in_degree[b]++;
            out_degree[a]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < n + 1; i++) {
            if (in_degree[i] == 0){
                queue.add(i);
                ord[i] = 1;
            }
        }
        while (!queue.isEmpty()){
            int temp = queue.remove();
            vis1[temp] = 1;
            for (int i = 0; i < graph1[temp].size(); i++) {
                int a = graph1[temp].get(i);
                if (vis1[a] == 0){
                    ord[a] = (ord[a] + ord[temp]) % mod;
                    in_degree[a]--;
                    if (in_degree[a] == 0){
                        queue.add(a);
                    }
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            if (out_degree[i] == 0){
                queue.add(i);
                res[i] = 1;
            }
        }
        while (!queue.isEmpty()){
            int temp = queue.remove();
            vis2[temp] = 1;
            for (int i = 0; i < graph2[temp].size(); i++) {
                int a = graph2[temp].get(i);
                if (vis2[a] == 0){
                    res[a] = (res[a] + res[temp]) % mod;
                    out_degree[a]--;
                    if (out_degree[a] == 0){
                        queue.add(a);
                    }
                }
            }
        }
        for (int i = 1; i < n + 1; i++) {
            out.print((res[i] * ord[i]) % mod + " ");
        }
        out.close();
    }

    static class QReader {
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer = new StringTokenizer("");

        private String innerNextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        public boolean hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
            return true;
        }

        public String nextLine() {
            tokenizer = new StringTokenizer("");
            return innerNextLine();
        }

        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class QWriter implements Closeable {
        private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        public void print(Object object) {
            try {
                writer.write(object.toString());
            } catch (IOException e) {
                return;
            }
        }

        public void println(Object object) {
            try {
                writer.write(object.toString());
                writer.write("\n");
            } catch (IOException e) {
                return;
            }
        }

        @Override
        public void close() {
            try {
                writer.close();
            } catch (IOException e) {
                return;
            }
        }
    }
}