package lab7;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static final int N = (int) 5e5 + 5;

    static class Node {
        int l, r;
        long sum;
    }

    static Node[] tr = new Node[N << 2];
    static int[] w = new int[N];

    static int n, m;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        n = in.nextInt();
        m = in.nextInt();

        for (int i = 1; i <= n; i++) w[i] = in.nextInt();

        build(1, 1, n);

        while (m-- > 0) {
            int t, x, y;
            t = in.nextInt();
            x = in.nextInt();
            y = in.nextInt();

            if (t == 1) upd(1, x, y);
            else out.println(query(1, x, y).sum);
        }

        out.close();
    }

    private static Node query(int u, int l, int r) {
        if (l <= tr[u].l && tr[u].r <= r) return tr[u];
        int mid = tr[u].l + tr[u].r >> 1;

        Node L = new Node();
        Node R = new Node();
        L.l = -1;
        R.l = -1;
        if (l <= mid) L = query(ls(u), l, r);
        if (mid < r) R = query(rs(u), l, r);

        if (L.l == -1) return R;
        if (R.l == -1) return L;
        return merge(L, R);
    }

    private static void upd(int u, int x, int k) {
        if (tr[u].l == tr[u].r) {
            tr[u].sum += k;
            return;
        }
        int mid = tr[u].l + tr[u].r >> 1;
        if (x <= mid) upd(ls(u), x, k);
        else upd(rs(u), x, k);
        tr[u] = merge(tr[ls(u)], tr[rs(u)]);
    }

    private static void build(int u, int l, int r) {
        tr[u] = new Node();
        tr[u].l = l;
        tr[u].r = r;
        if (l == r) {
            tr[u].sum = w[l];
            return;
        }
        int mid = l + r >> 1;
        build(ls(u), l, mid);
        build(rs(u), mid + 1, r);
        tr[u] = merge(tr[ls(u)], tr[rs(u)]);
    }

    private static Node merge(Node L, Node R) {
        Node res = new Node();
        res.l = L.l;
        res.r = R.r;
        res.sum = L.sum + R.sum;
        return res;
    }

    private static int ls(int u) {
        return u << 1;
    }

    private static int rs(int u) {
        return u << 1 | 1;
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
