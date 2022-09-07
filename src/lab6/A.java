package lab6;

import java.io.*;
import java.util.*;

public class A {
    static ArrayList<Integer>[] graph;
    static int[] vis;
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        graph = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            graph[a].add(b);
            graph[b].add(a);
        }
        vis = new int[n + 1];
        int[] d = new int[n + 1];
        int q = in.nextInt();
        Queue<Integer> queue = new LinkedList<>();
        while (q-- > 0) {
            int ans = 0;
            int x = in.nextInt();
            int y = in.nextInt();
            for (int i = 1; i < n + 1; i++) {
                init(d);
                init(vis);
                if (i == x || i == y) continue;
                queue.add(i);
                while (!queue.isEmpty()) {
                    int temp = queue.remove();
                    vis[temp] = 1;
                    for (int j = 0; j < graph[temp].size(); j++) {
                        int v = graph[temp].get(j);
                        if (vis[v] == 1) continue;
                        d[v] = d[temp] + 1;
                        queue.add(v);
                    }
                }
                if (d[x] == d[y]) ans++;
            }
            if (x == y) ans++;
            out.println(ans);
        }
        out.close();
    }

    static void init(int[] a){
        Arrays.fill(a, 0);
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
