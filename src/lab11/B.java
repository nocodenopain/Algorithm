package lab11;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class B {
    static long mod = (long) (1e9 + 7);

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        int l = 1 << n;
        int[] g = new int[l];
        while (m-- > 0) {
            int a = in.nextInt();
            int b = in.nextInt();
            g[a] += 1 << b - 1;
        }
        long[] f = new long[l];
        f[0] = 1;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) == 0) {
                    f[i + (1 << j)] += f[i] * (1L << cnt(g[j + 1] & i));
                    f[i + (1 << j)] %= mod;
                }
            }
        }
        System.out.println(f[l - 1]);
        out.close();
    }
    static int cnt(int i){
        int ans = 0;
        String s = Integer.toBinaryString(i);
        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) == '1') ans++;
        }
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

