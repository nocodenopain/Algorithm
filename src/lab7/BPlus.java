package lab7;

import java.io.*;
import java.util.StringTokenizer;

public class BPlus {
    static Node[] tr = new Node[(int) 3e5 << 2];
    static int[] a;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        a = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            a[i] = in.nextInt();
        }
        build(1, 1, n);
        int q = in.nextInt();
        while (q-- > 0) {
            Node node = find(1, in.nextInt(), in.nextInt());
            out.println(node.s);
        }
        out.close();
    }
    private static Node find(int u, int l, int r) {
        if (l <= tr[u].l && tr[u].r <= r) return tr[u];
        int mid = tr[u].l + tr[u].r >> 1;
        Node left = null;
        Node right = null;
        if (l <= mid) left = find(ls(u), l, r);
        if (mid < r) right = find(rs(u), l, r);
        if (left == null) return right;
        if (right == null) return left;
        return merge(left, right);
    }

    private static  Node merge(Node L, Node R) {
        Node res = new Node();
        res.l = L.l;
        res.r = R.r;
        res.sum = L.sum + R.sum;
        res.p = Math.max(L.p, L.sum + R.p);
        res.q = Math.max(R.q, R.sum + L.q);
        res.s = max(R.s, L.s, L.q + R.p);
        return res;
    }

    private static void build(int u, int l, int r) {
        tr[u] = new Node();
        tr[u].l = l;
        tr[u].r = r;
        if (l == r) {
            tr[u].sum = a[l];
            tr[u].p = a[l];
            tr[u].q = a[l];
            tr[u].s = a[l];
            return;
        }
        int mid = l + r >> 1;
        build(ls(u), l, mid);
        build(rs(u), mid + 1, r);
        tr[u] = merge(tr[ls(u)], tr[rs(u)]);
    }

    private static int ls(int u) {
        return u << 1;
    }

    private static int rs(int u) {
        return u << 1 | 1;
    }

    static long max(long a, long b, long c) {
        long ans = Math.max(a, b);
        return Math.max(ans, c);
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

class Node {
    int l, r;
    long p;
    long q;
    long sum;
    long s;
}
