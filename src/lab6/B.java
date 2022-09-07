package lab6;

import java.io.*;
import java.util.StringTokenizer;

public class B {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        if (n == 1){
            out.println(0);
        } else {
            String ss = Integer.toBinaryString(n - 1);
            int l = ss.length();
            out.println(2 * l);
            for (int i = 0; i < l; i++) {
                int k = 1 << i;
                out.print(l + " ");
                for (int j = 0; j < n; j++) {
                    if ((k & j) != 0) out.print(j + 1 + " ");
                }
                out.println("");
                out.print(l + " ");
                for (int j = 0; j < n; j++) {
                    if ((k & j) == 0) out.print(j + 1 + " ");
                }
                out.println("");
            }
            for (int i = 0; i < l; i++) {
                int k = 1 << i;
                out.print(l + " ");
                for (int j = 0; j < n; j++) {
                    if ((k & j) == 0) out.print(j + 1 + " ");
                }
                out.println("");
                out.print(l + " ");
                for (int j = 0; j < n; j++) {
                    if ((k & j) != 0) out.print(j + 1 + " ");
                }
                out.println("");
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
}
