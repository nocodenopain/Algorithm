package lab10;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class B {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        StringBuilder sb = new StringBuilder();
        int n = in.nextInt();
        ArrayList<Integer> ar = new ArrayList<>();
        ar.add(in.nextInt());
        for (int i = 1; i < n; i++) {
            int temp = in.nextInt();
            if (ar.get(ar.size() - 1) != temp) {
                ar.add(temp);
            }
        }
        int[] c = new int[ar.size()];
        int l = c.length;
        for (int i = 0; i < l; i++) {
            c[i] = ar.get(i);
        }
        int[][] f = new int[l][l];
        int cnt = 0;
        for (int i = 1; i < l; i++) {
            for (int j = 0; j + i - 1 < l; j++) {
                int k = j + i - 1;
                if (j == k) f[j][k] = 0;
                else if (c[j] == c[k]) f[j][k] = f[j + 1][k - 1] + 1;
                else f[j][k] = Math.min(f[j + 1][k] + 1, f[j][k - 1] + 1);
            }
        }
//        out.println(f[0][l - 1]);
        if (c.length > 1) {
            if (c[0] == c[l - 1]) out.println(f[1][l - 2] + 1);
            else out.println(Math.min(f[1][l - 1] + 1, f[0][l - 2] + 1));
        } else out.println(0);
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
}
