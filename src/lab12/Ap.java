package lab12;

import java.io.*;
import java.util.StringTokenizer;

public class Ap {
    static int s;
    static int t;
    static int cnt = 1;
    static long he[] = new long[11111];
    static long to[] = new long[111111];
    static long[] flow = new long[111111];
    static long ne[] = new long[111111];
    static int dep[];
    static int[] q = new int[11111];
    static int tail, head;
    static long[] cur = new long[11111];

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        s = in.nextInt();
        t = in.nextInt();
        while (m-- > 0) {
            int u = in.nextInt();
            int v = in.nextInt();
            long w = in.nextLong();
            add(u, v, w);
            add(v, u, 0);
        }
        long ans = 0;
        ans += cal();

        out.println(ans);
        out.close();
    }

    private static long cal() {
        long ans = 0;
        while (bfs()) {
            ans += dfs(s, (long) 1e18);
        }
        return ans;
    }


    static void add(int u, int v, long w) {
        to[++cnt] = v;
        flow[cnt] = w;
        ne[cnt] = he[u];
        he[u] = cnt;
    }

    static boolean bfs() {
        dep = new int[11111];
        tail = 0;
        head = 1;
        q[++tail] = s;
        dep[s] = 1;
        while (tail <= head) {
            int u = q[tail++];
            for (int p = (int) he[u]; p != 0; p = (int) ne[p]) {
                int v = (int) to[p];
                if (flow[p] != 0 && dep[v] == 0) {//按照有残量的边搜过去
                    dep[v] = dep[u] + 1;
                    cur[v] = he[v];
                    if(v==t) return true;
                    q[++head] = v;
                }
            }
        }
        return dep[t] != 0;
    }

    static long dfs(int u, long in) {
        if (u == t)
            return in;
        long out = 0;
        for (int p = (int) he[u]; p != 0 && in != 0; p = (int) ne[p]) {
            cur[u] = p;
            int v = (int) to[p];
            if (flow[p] != 0 && dep[v] == dep[u] + 1) {
                long res = dfs(v, Math.min(flow[p], in));
                flow[p] -= res;
                flow[p ^ 1] += res;
                in -= res;
                out += res;
            }
        }
        if (out == 0)
            dep[u] = 0;
        return out;
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
