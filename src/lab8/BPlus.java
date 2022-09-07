package lab8;

import java.io.*;
import java.util.StringTokenizer;

public class BPlus {
    static int[] rt;
    static int number;
    static Node[] tree;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        rt = new int[n + 1];
        tree = new Node[n * 40];
        Node[] t = tree;
        tree[0] = new Node();
        int[] a = new int[n];
        int min = (int) 1e9 + 3;
        int max = -1;
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            if (a[i] > max) max = a[i];
            if (a[i] < min) min = a[i];
        }
        for (int i = 1; i <= n; i++) {
            rt[i] = insert(rt[i - 1], min, max, a[i - 1]);
        }
        while (m-- > 0) {
            int l = in.nextInt();
            int r = in.nextInt();
            if (l == r) {
                out.println(a[l - 1]);
            } else {
                int k = r - l + 1;
                k = k >> 1;
                if ((l % 2 == 0 && r % 2 == 0) || (l % 2 == 1 && r % 2 == 1)) {
                    k++;
                    int ans = query(rt[l - 1], rt[r], min, max, k);
                    out.println(ans);
                } else {
                    int ans = query(rt[l - 1], rt[r], min, max, k);
                    out.println(ans);
                }
            }
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

    static int insert(int id, int l, int r, int v) {
        number++;
        int q = number;
        tree[q] = new Node(tree[id].l, tree[id].r, tree[id].cnt);
        if (l == r) {
            tree[q].cnt++;
            return q;
        }
        int mid = l + r >> 1;
        if (v <= mid) {
            tree[q].l = insert(tree[id].l, l, mid, v);
        } else {
            tree[q].r = insert(tree[id].r, mid + 1, r, v);
        }
        tree[q].cnt = tree[tree[q].l].cnt + tree[tree[q].r].cnt;
        return q;
    }

    static int query(int l_node, int r_node, int l, int r, int k) {
        if (l == r) return l;
        int mid = l + r >> 1;
        int cnt = tree[tree[r_node].l].cnt - tree[tree[l_node].l].cnt;
        if (k <= cnt) {
            return query(tree[l_node].l, tree[r_node].l, l, mid, k);
        } else {
            return query(tree[l_node].r, tree[r_node].r, mid + 1, r, k - cnt);
        }
    }
}

class Node {
    int l, r, cnt;

    public Node() {

    }

    public Node(int l, int r, int cnt) {
        this.l = l;
        this.r = r;
        this.cnt = cnt;
    }
}