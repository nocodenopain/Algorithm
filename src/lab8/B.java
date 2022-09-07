package lab8;

import java.io.*;
import java.util.StringTokenizer;

public class B {
    static point[] tr;
    static int[] a;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int q = in.nextInt();
        a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }
        int max = -1;
        int min = (int) 1e9 + 1;
        for (int i = 1; i < n + 1; i++) {
            if (a[i] > max) max = a[i];
            if (a[i] < min) min = a[i];
        }
        point[] tr = new point[n + 1];
        tr[0] = new point(min, max, 0);
        for (int i = 1; i <= n; i++) {
            int temp = a[i];
            tr[i] = new point(min, max, tr[i - 1].cnt + 1);
            int mid = (tr[i - 1].l + tr[i - 1].r) >> 1;
            if (tr[i - 1].ls != null) {
                tr[i].ls = tr[i - 1].ls;
            }
            if (tr[i - 1].rs != null) {
                tr[i].rs = tr[i - 1].rs;
            }
            if (temp < mid) {
                if (tr[i].ls != null) {
                    tr[i].ls.cnt++;
                } else {
                    tr[i].ls = new point(min, mid, 1);
                    push_down(tr[i].ls, temp);
                }
            } else {
                if (tr[i].rs != null) {
                    tr[i].rs.cnt++;
                } else {
                    tr[i].rs = new point(mid + 1, max, 1);
                    push_down(tr[i].rs, temp);
                }
            }
        }
        while (q-- > 0) {
            int l = in.nextInt();
            int r = in.nextInt();
            int mid = r + l >> 1;
            int ans = query(tr[l - 1], tr[r], mid);
            out.println(ans);
        }
        out.close();
    }

    static int query(point l, point r, int mid) {
        if (r.l == r.r) return r.r;
        int cnt = 0;
        if (l.ls != null) cnt = r.ls.cnt - l.ls.cnt;
        else if (r.ls != null) cnt = r.ls.cnt;
        if (mid <= cnt) return query(l.ls, r.ls, mid);
        else return query(l.rs, r.rs, mid - cnt);
    }

    static void push_down(point point, int temp) {
        if (point.l == point.r) return;
        int mid = (point.l + point.r) >> 1;
        if (temp < mid) {
            if (point.ls != null) {
                point.ls.cnt++;
            } else {
                point.ls = new point(point.l, mid, 1);
            }
            push_down(point.ls, temp);
        } else {
            if (point.rs != null) {
                point.rs.cnt++;
            } else {
                point.rs = new point(mid + 1, point.r, 1);
            }
            push_down(point.rs, temp);
        }
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

class point {
    int l, r, cnt;
    point ls, rs;

    public point(int l, int r, int cnt) {
        this.cnt = cnt;
        this.r = r;
        this.l = l;
    }
}
