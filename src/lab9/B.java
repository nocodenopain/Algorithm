package lab9;

import java.io.*;
import java.util.StringTokenizer;


public class B {
    static int mod = (int) 1e9 + 7;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int k = in.nextInt();
        int[] f = new int[k];
        f[0] = 1;
        int[][] k_matrix = new int[k][k];
        for (int i = 0; i < k; i++) {
            k_matrix[i][0] = 1;
        }
        for (int i = 0; i < k - 1; i++) {
            k_matrix[i][i + 1] = 1;
        }
        k_matrix = fastMatrix(k_matrix, n);
        int ans = 0;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                ans = (ans + (int)((long)f[j] * k_matrix[j][i]) % mod) % mod;
            }
        }
        out.println(ans);
        out.close();
    }

    static int[][] mul(int[][] a, int[][] b) {
        int n = a.length;
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    ans[i][j] = (ans[i][j] + (int) (((long) a[i][k] * b[k][j]) % mod)) % mod;
                }
            }
        }
        return ans;
    }

    static int[][] fastMatrix(int[][] a, int n) {
        int l = a.length;
        int[][] ans = new int[l][l];
        init(ans);
        while (n > 0) {
            if (n % 2 == 1) {
                ans = mul(ans, a);
            }
            a = mul(a, a);
            n >>= 1;
        }
        return ans;
    }

    static void init(int[][] a){
        int n = a.length;
        for (int i = 0; i < n; i++) {
            a[i][i] = 1;
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
