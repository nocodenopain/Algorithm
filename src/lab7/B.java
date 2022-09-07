package lab7;

import java.io.*;
import java.util.StringTokenizer;

public class B {
    static Max_ST_Table max = new Max_ST_Table();
    static Min_ST_Table min = new Min_ST_Table();
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        long[] a = new long[n];
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
        }
        sum[0] = 0;
        for (int i = 1; i < n + 1; i++) {
            sum[i] = sum[i - 1] + a[i - 1];
        }
        max.init(sum);
        min.init(sum);
        int q = in.nextInt();
        for (int i = 0; i < q; i++) {
            int l = in.nextInt();
            int r = in.nextInt();
            long ans = merge(a,l,r);
            out.println(ans);
        }
        out.close();
    }
    static long merge(long[] a, int l, int r){
        if (l == r){
            return a[l - 1];
        }
        if (l == r - 1){
            long t1 = a[l - 1];
            long t2 = a[l];
            long t3 = t1 + t2;
            long ans = Math.max(t1,t2);
            return Math.max(ans, t3);
        }
        int mid = (r + l)/2;
        long ans1 = merge(a,l,mid);
        long ans2 = merge(a,mid + 1,r);
        long ans = Math.max(ans1,ans2);
        long t1 = max.find_max(mid + 2,r + 1);
        long t2 = min.find_min(l,mid + 1);
        ans = Math.max(ans,t1 - t2);
        return ans;
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

class Min_ST_Table {
    long[][] st;

    public void init(long[] a) {
        int n = a.length;
        st = new long[a.length + 1][30];
        for (int i = 1; i <= n; ++i) {
            this.st[i][0] = a[i - 1];
        }
        for (int j = 1; (1 << j) <= n; ++j) {
            for (int i = 1; i + (1 << j) - 1 <= n; ++i) {
                st[i][j] = Math.min(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    public long find_min(int l, int r) {
        int t = (int) (Math.log(r - l + 1) / Math.log(2));
        return Math.min(this.st[l][t], this.st[r - (1 << t) + 1][t]);
    }
}

class Max_ST_Table  {
    long[][] st;

    public void init(long[] a) {
        int n = a.length;
        st = new long[a.length + 1][30];
        for (int i = 1; i <= n; ++i) {
            this.st[i][0] = a[i - 1];
        }
        for (int j = 1; (1 << j) <= n; ++j) {
            for (int i = 1; i + (1 << j) - 1 <= n; ++i) {
                st[i][j] = Math.max(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    public long find_max(int l, int r) {
        int t = (int) (Math.log(r - l + 1) / Math.log(2));
        return Math.max(this.st[l][t], this.st[r - (1 << t) + 1][t]);
    }
}