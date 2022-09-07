package lab3;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        int c = in.nextInt();
        int t = in.nextInt();
        int[] rabbit = new int[n];
        int[] net = new int[m];
        int[] num = new int[m];
        for (int i = 0; i < n; i++) {
            rabbit[i] = in.nextInt();
        }
        for (int i = 0; i < m; i++) {
            net[i] = in.nextInt();
        }
        Arrays.sort(rabbit);
        Arrays.sort(net);
        int cnt = 0;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (rabbit[i] - net[m - 1] > t){
                break;
            }
            for (int j = cnt; j < m; j++) {
                if (Math.abs(rabbit[i] - net[j]) <= t && num[j] < c){
                    num[j]++;
                    ans++;
                    if (num[j] == c){
                        cnt++;
                    }
                    break;
                }
                if (net[j] - rabbit[i] > t){
                    break;
                }
            }
        }
        out.print(ans);
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
