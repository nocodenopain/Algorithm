package lab8;

import java.io.*;
import java.util.StringTokenizer;

public class A {
    static double pi = Math.PI;
    static int[] bit_res;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int k = (int) Math.pow(2, n);
        bit_res = new int[k];
        String ss = Integer.toBinaryString(k);
        int bit = ss.length();
        for (int i = 0; i < k; i++) bit_res[i] = ((bit_res[i >> 1] >> 1) | ((i & 1) << (bit - 1)));
        for (int i = 0; i < k; i++) bit_res[i] = bit_res[i] >> 1;
        complex[] c = new complex[k];
        for (int i = 0; i < k; i++) {
            c[i] = new complex(0, 0);
            String s = in.next();
            c[i].r = Double.parseDouble(s);
        }
        swap(c);
        fft(c);
        for (int i = 0; i < k; i++) {
            out.println(c[i].mod());
        }
        out.close();
    }

    static void fft(complex[] c) {
        for (int mid = 1; mid < c.length; mid <<= 1) {
            complex w1 = new complex(Math.cos(pi / mid), Math.sin(pi / mid));
            for (int i = 0; i < c.length; i += mid * 2) {
                complex wk = new complex(1, 0);
                for (int j = 0; j < mid; j++, wk = mul(wk, w1)) {
                    complex x = c[i + j];
                    complex y = mul(wk, c[i + j + mid]);
                    c[i + j] = add(x, y);
                    c[i + j + mid] = sub(x, y);
                }
            }
        }
    }

    static void swap(complex[] c) {
        for (int i = 0; i < c.length; i++) {
            if (i < bit_res[i]) {
                complex temp = c[i];
                c[i] = c[bit_res[i]];
                c[bit_res[i]] = temp;
            }
        }
    }

    static complex mul(complex a, complex b) {
        complex c = new complex(0,0);
        double r = a.r;
        double i = a.i;
        c.r = r * b.r - i * b.i;
        c.i = r * b.i + i * b.r;
        return c;
    }

    static complex add(complex a, complex b) {
        complex c = new complex(0,0);
        c.r += a.r + b.r;
        c.i += a.i + b.i;
        return c;
    }

    static complex sub(complex a, complex b) {
        complex c = new complex(0,0);
        c.r = a.r - b.r;
        c.i = a.i - b.i;
        return c;
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

class complex {
    double r, i;

    public complex(double r, double i) {
        this.r = r;
        this.i = i;
    }

    public double mod() {
        return Math.sqrt(Math.pow(this.r, 2) + Math.pow(this.i, 2));
    }
}
