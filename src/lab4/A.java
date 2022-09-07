package lab4;

import java.io.*;
import java.util.*;

public class A {
    static long mod = (long) (1e9 + 7);

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        long ce = in.nextLong();
        long[] f = new long[n + 1];
        double[] g = new double[n + 1];
        int[] degree = new int[n + 1];
        int[] h = new int[n + 1];
        int[] a = new int[n + 1];
        int[] b = new int[n + 1];
        int[] c = new int[n + 1];
        long ans = 0;
        double compare = 0;
        for (int i = 1; i < n + 1; i++) {
            h[i] = in.nextInt();
            a[i] = in.nextInt();
            b[i] = in.nextInt();
            c[i] = in.nextInt();
        }
        ArrayList<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }
        while (m-- > 0) {
            int q = in.nextInt();
            int w = in.nextInt();
            graph[q].add(w);
            degree[w]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        if (ce >= h[1]) {
            long a1 = ce + a[1];
            long a2 = ce * b[1];
            long a3 = c[1];
            f[1] = Math.max(a1, a2);
            f[1] = Math.max(f[1], a3);
            if (f[1] > 1e9) {
                g[1] = Math.log(f[1]);
                f[1] = f[1] % mod;
            }
            queue.add(1);
        }
        while (!queue.isEmpty()) {
            int temp = queue.remove();
            if (f[temp] >1e9){
                g[temp] = Math.log(f[temp]);
                f[temp] %= mod;
            }
            if (g[temp] == 0) {
                for (int i = 0; i < graph[temp].size(); i++) {
                    int t = graph[temp].get(i);
                    degree[t]--;
                    if (f[temp] >= h[t]) {
                        f[t] = Math.max(f[t],f[temp] + a[t]);
                        f[t] = Math.max(f[t],f[temp] * b[t]);
                        f[t] = Math.max(f[t],c[t]);
                    }
                    if (degree[t] == 0 && f[t] != 0){
                        queue.add(t);
                    }
                }
                if (graph[temp].size() == 0 && compare == 0){
                    ans = Math.max(ans,f[temp]);
                }
            } else {
                for (int i = 0; i < graph[temp].size(); i++) {
                    int t = graph[temp].get(i);
                    degree[t]--;
                    double q = g[temp] + Math.log(b[t]);
                    if (g[t] < q){
                        f[t] = ((f[temp] % mod) * b[t]) % mod;
                        g[t] = q;
                    }
                    if (degree[t] == 0){
                        queue.add(t);
                    }
                }
                if (graph[temp].size() == 0 && g[temp] > compare){
                    compare = g[temp];
                    ans = f[temp];
                }
            }
        }
        if (ans == 0) {
            out.print(-1);
        } else {
            out.print(ans);
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
