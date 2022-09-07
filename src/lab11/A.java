package lab11;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class A {
    static long f[][];
    static long ans;
    static ArrayList<Edge>[] graph;
    static int[] vis;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = n - 1;
        vis = new int[n + 1];
        graph = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }
        while (m-- > 0) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
//            graph[in.nextInt()].add(new Edge(in.nextInt(), in.nextInt()));
            graph[a].add(new Edge(b, c));
            graph[b].add(new Edge(a, c));
        }
        f = new long[n + 1][2];
        dfs(1);
        out.println(f[1][0]);
        out.close();
    }

    static void dfs(int u) {
        vis[u] = 1;
        long max = 0;
        for (int i = 0; i < graph[u].size(); i++) {
            int go = graph[u].get(i).to;
            if (vis[go] == 0) {
                f[go][1] += graph[u].get(i).w;
                dfs(go);
                f[u][1] += f[go][0];
                f[u][0] += f[go][0];
                max = Math.max(max, f[go][1] - f[go][0]);
            }
        }
        f[u][0] += max;
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
    long w;

    public Edge(int to, int w) {
        this.to = to;
        this.w = w;
    }
}
