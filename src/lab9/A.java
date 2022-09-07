package lab9;

import java.io.*;
import java.util.StringTokenizer;


public class A {
    static int mod = (int) 1e9 + 7;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        int max_depth = Math.min(n, m) + 1 >> 1;
        int l = Math.max(n,m);
        long[][] f = new long[l + 1][l + 1];
        long[][] s1 = new long[l + 1][l + 1];
        long[][] s2 = new long[l + 1][l + 1];
        init(f, l + 1);
        sum1(f[1], s1[1], l + 1);
        sum2(f[1], s2[1], l + 1);
        for (int i = 2; i <= l; i++) {
            for (int j = 3; j <= l; j++) {
                f[i][j] = (j - 1) * s1[i - 1][j - 2] - s2[i - 1][j - 2];
                f[i][j] %= mod;
            }
            sum1(f[i], s1[i], l + 1);
            sum2(f[i], s2[i], l + 1);
        }
        long ans = 0;
        for (int i = 1; i <= max_depth; i++) {
            ans = (ans + (int) ((f[i][n] * f[i][m]) % mod)) % mod;
        }
        out.println(ans > 0 ? ans : ans + mod);
        out.close();
    }

    static void init(long[][] f, int l) {
        for (int i = 1; i < l; i++) {
            f[1][i] = 1;
        }

    }

    static void sum2(long[] a, long[] s, int l) {
        s[0] = a[0];
        for (int i = 1; i < l; i++) {
            s[i] = s[i - 1] + i * a[i];
            s[i] %= mod;
        }
    }

    static void sum1(long[] a, long[] s, int l) {
        s[0] = a[0];
        for (int i = 1; i < l; i++) {
            s[i] = s[i - 1] + a[i];
            s[i] %= mod;
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
