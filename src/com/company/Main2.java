package com.company;

import java.io.*;
import java.util.*;

public class Main2 {
    static class computer {//电脑类，存位置、可用时间、单位收入
        int index;
        int time;
        long income;

        computer(int index) {
            this.index = index;
        }
    }

    static class interval {//放进优先队列的区间类，存起始点、已用时间base、单位收入和
        int start;
        int end;
        int base;
        long sum;

        interval(int start, int end, int base, long sum) {
            this.start = start;
            this.end = end;
            this.base = base;
            this.sum = sum;
        }
    }

    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();

        int n = in.nextInt();
        int m = in.nextInt();

        computer[] com = new computer[n + 1];
        for (int i = 1; i <= n; i++) {
            com[i] = new computer(i);
            com[i].time = in.nextInt();
        }

        for (int i = 1; i <= n; i++) {
            com[i].income = in.nextLong();
        }

        long[] sum = new long[n + 1];      //前缀和数组
        sum[1] = com[1].income;
        for (int i = 2; i < sum.length; i++) {
            sum[i] = sum[i - 1] + com[i].income;
        }

        int ln = (int) Math.ceil(Math.log(n) / Math.log(2));//st表ln是表示最多用几列
        computer[][] st = new computer[n + 1][ln + 1];
        for (int i = 1; i <= n; i++) {
            st[i][0] = com[i];
        }
        for (int j = 1; j <= ln; j++) {
            for (int i = 1; i + (1 << j) - 1 <= n; i++) {
                if (st[i][j - 1].time <= st[i + (1 << j - 1)][j - 1].time)
                    st[i][j] = st[i][j - 1];
                else st[i][j] = st[i + (1 << j - 1)][j - 1];
            }
        }

        interval[] heap = new interval[ln * ln + 1];//大顶堆
        int tail = 0;  //指示堆最末元素的下一个位置

        interval begin = new interval(1, n, 0, sum[n]);//最开始全部区间
        heap[tail++] = begin;

        long ans = 0;
        while (m > 0 && tail > 0) {//m用完or区间不能再分时结束
            int start = heap[0].start;
            int end = heap[0].end;
            int temp = (int) (Math.log(end - start + 1) / Math.log(2));//st查找的大小

            computer now;//找到区间内哪个可用时间最少
            if (st[start][temp].time <= st[end - (1 << temp) + 1][temp].time)
                now = st[start][temp];
            else now = st[end - (1 << temp) + 1][temp];

            int time = Math.min(m, now.time - heap[0].base);//本次消耗时间，m与期间最小时间的min值
            m -= time;
            ans += heap[0].sum * (time);

            if (now.index - 1 >= start) {//子区间1
                interval first = new interval(start, now.index - 1, time + heap[0].base, sum[now.index - 1] - sum[start - 1]);
                heap[tail++] = first;
                up(heap, tail - 1);
            }

            if (end >= now.index + 1) {//子区间2
                interval second = new interval(now.index + 1, end, time + heap[0].base, sum[end] - sum[now.index]);
                heap[tail++] = second;
                up(heap, tail - 1);
            }

            heap[0] = heap[--tail];//维护大顶堆
            heap[tail] = null;
            down(heap, tail);

        }

        out.println(ans);
        out.close();
    }

    static void up(interval[] heap, int start) {//从0开始存的，大根堆up
        int child = start;
        int parent = (start - 1) / 2;
        interval temp = heap[child];

        while (parent >= 0 && temp.sum > heap[parent].sum) {
            heap[child] = heap[parent];
            heap[parent] = temp;
            child = parent;
            parent = (parent - 1) / 2;
            temp = heap[child];
        }
    }

    static void down(interval[] heap, int count) {//大根堆down
        int parent = 0;
        int child = 1;
        interval temp = heap[parent];

        while (child < count) {
            if (child + 1 < count && heap[child].sum < heap[child + 1].sum)
                child++;
            if (temp.sum >= heap[child].sum)
                break;

            heap[parent] = heap[child];
            heap[child] = temp;

            parent = child;
            child = 2 * child + 1;
            temp = heap[parent];
        }
    }

    static class QReader {
        private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer = new StringTokenizer("");

        private String innerNextLine() {
            try {
                return reader.readLine();
            } catch (IOException e) {
                return null;
            }
        }

        public void hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
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
        private final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

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



