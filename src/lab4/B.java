package lab4;

import java.io.*;
import java.util.*;

public class B {
    static long[] sum;

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            nodes[i] = new Node(a[i],i);
        }
        Arrays.sort(nodes);
        for (int i = 0; i < n; i++) {
            b[i] = in.nextInt();
        }
        long ans = 0;
        sum = new long[n];
        sum[0] = b[0];
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + b[i];
        }
        ST_Table st = new ST_Table();
        st.init(a);
        PriorityQueue<interval> q = new PriorityQueue<>();
        q.add(new interval(0, n - 1, sum, 0));
        while (!q.isEmpty() && m != 0) {
            interval temp = q.remove();
            int t = st.find_min(temp.l, temp.r);
            int position = binarySearch(nodes,t);
            int cnt = 0;
            if (m > t - temp.time) {
                cnt = t - temp.time;
                m -= t - temp.time;
                ans += temp.sum * (t - temp.time);
            } else {
                cnt = m;
                ans += m * temp.sum;
                m = 0;
                break;
            }
            int tt = temp.time + cnt;
            if (temp.r != temp.l) {
                if (position == temp.l) {
                    q.add(new interval(temp.l + 1, temp.r, sum, tt));
                } else if (position == temp.r) {
                    q.add(new interval(temp.l, temp.r - 1, sum, tt));
                } else {
                    q.add(new interval(temp.l, position - 1, sum, tt));
                    q.add(new interval(position + 1, temp.r, sum, tt));
                }
            }
        }
        out.println(ans);
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

    public static int binarySearch(Node[] srcArray, int des) {
        int low = 0;
        int high = srcArray.length - 1;
        while (low <= high) {
            int middle = (high + low) >>> 1;
            if (des == srcArray[middle].val) {
                return srcArray[middle].id;
            } else if (des < srcArray[middle].val) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return -1;
    }
}

class ST_Table {
    int[][] st;

    public void init(int[] a) {
        int n = a.length;
        st = new int[a.length + 1][200];
        for (int i = 1; i <= n; ++i) {
            this.st[i][0] = a[i - 1];
        }
        for (int j = 1; (1 << j) <= n; ++j) {
            for (int i = 1; i + (1 << j) - 1 <= n; ++i) {
                st[i][j] = Math.min(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }
    }

    public int find_min(int l, int r) {
        l++;
        r++;
        int t = (int) (Math.log(r - l + 1) / Math.log(2));
        return Math.min(this.st[l][t], this.st[r - (1 << t) + 1][t]);
    }
}

class interval implements Comparable {
    int l;
    int r;
    long sum;
    int time;

    public interval(int l, int r, long[] sum, int time) {
        this.l = l;
        this.r = r;
        if (l != 0) {
            this.sum = sum[r] - sum[l - 1];
        } else {
            this.sum = sum[r];
        }
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof interval) {
            return Long.compare(((interval) o).sum, this.sum);
        } else {
            throw new RuntimeException("");
        }
    }
}

class Node implements Comparable {
    int val;
    int id;
    public Node(int val, int id){
        this.id = id;
        this.val = val;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Node) {
            return Long.compare(this.val,((Node) o).val);
        } else {
            throw new RuntimeException("");
        }
    }
}