package lab10;

import java.io.*;
import java.util.StringTokenizer;

public class A {
    static int mod = (int)1e9 + 7;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int l = in.nextInt();
        int r = in.nextInt();
        int[] cnt = new int[5001];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int temp = in.nextInt();
            cnt[temp]++;
            sum += temp;
        }
        int lf = Math.max(sum - r, l);
        int rf = Math.min(sum-l, r);
        long[][] f = new long[5001][rf + 1];
        init(f);
        for (int i = 1; i < 5001; i++) {
            for (int j = 1; j < rf + 1; j++) {
                f[i][j] = f[i - 1][j];
                if (i > j) {
                } else {
                    for (int k = 1; k <= cnt[i]; k++) {
                        if (j - k * i >= 0){
                            f[i][j] += f[i - 1][j - k * i];
                            f[i][j] %= mod;
                        } else break;
                    }
                }
            }
        }
        long ans = 0;
        for (int i = lf; i <= rf; i++) {
            ans += f[5000][i];
            ans %= mod;
        }
        out.println(ans);
        out.close();
    }
    static void init(long[][] f){
        for (int i = 0; i < f.length; i++) {
            f[i][0] = 1;
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


