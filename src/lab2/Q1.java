package lab2;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Q1 {
    static int mod = 998244353;
    static long[] P = new long[(int) 1e6];

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        P[1] = 1;
        for (int i = 2; i < 1e6; i++) {
            P[i] = (P[i - 1] * i) % mod;
        }
        int[] vis = new int[(int) 1e6];
        int n = in.nextInt();
        long ans = 1;
        ArrayList<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }
        int t = n - 1;
        while (t-- > 0) {
            int a = in.nextInt();
            int b = in.nextInt();
            graph[a].add(new Edge(b));
            graph[b].add(new Edge(a));
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        while (!queue.isEmpty()) {
            int temp = queue.remove();
            vis[temp] = 1;
            int a = 0;
            for (int i = 0; i < graph[temp].size(); i++) {
                if (vis[graph[temp].get(i).to] ==0){
                    queue.add(graph[temp].get(i).to);
                    a++;
                }
            }
            if (a != 0) {
                ans = (ans * P[a]) % mod;
            }
        }
        out.print(ans);
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

class Edge {
    int to;

    public Edge(int to) {
        this.to = to;
    }

    public Edge() {
    }
}