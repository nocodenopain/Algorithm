package lab2;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Q2 {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n;
        int[] dis;
        int[] vis;
        ArrayList<Edge>[] graph;
        n = in.nextInt();
        int[] a = new int[n];
        dis = new int[n + 1];
        vis = new int[n + 1];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        //Write your code here
        graph = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }
        graph[1].add(new Edge(2));
        graph[n].add(new Edge(n - 1));
        for (int i = 0; i < n; i++) {
            if (i + 1 != a[i] && a[i] != i && a[i] != i + 2) {
                graph[i + 1].add(new Edge(a[i]));
            }
            if (i > 1) {
                graph[i].add(new Edge(i - 1));
                graph[i].add(new Edge(i + 1));
            }
        }
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i < n + 1; i++) {
            nodes[i] = new Node();
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        while (!queue.isEmpty()){
            int temp = queue.remove();
            vis[temp] = 1;
            dis[temp] = nodes[temp].depth;
            for (int i = 0; i < graph[temp].size(); i++) {
                if (vis[graph[temp].get(i).to] == 0 && nodes[graph[temp].get(i).to].depth == 0){
                    nodes[graph[temp].get(i).to].depth = nodes[temp].depth + 1;
                    queue.add(graph[temp].get(i).to);
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            out.print(dis[i] + " ");
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
class Node{
    int id;
    int depth;
}