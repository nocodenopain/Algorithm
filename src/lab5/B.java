package lab5;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class B {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        int[] ans = new int[m];
        ArrayList<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            graph[a].add(new Edge(b, c, i));
            graph[b].add(new Edge(a, c, i));
        }
        long[] dis = new long[n + 1];
        long[] rd = new long[n + 1];
        int[] vis = new int[n + 1];
        int[] s = new int[11];
        int[] t = new int[11];
        int p = in.nextInt();
        for (int i = 0; i < p; i++) {
            s[i] = in.nextInt();
            t[i] = in.nextInt();
        }
        PriorityQueue<Node> queue;
        for (int i = 0; i < p; i++) {
            queue = new PriorityQueue<>();
            init(dis);
            init(rd);
            vis = new int[n + 1];
            int u = s[i], v = t[i];
            dis[u] = 0;
            queue.add(new Node(u, dis[u]));
            while (!queue.isEmpty()) {
                Node temp = queue.remove();
                if (temp.id == v) {
                    break;
                }
                if (vis[temp.id] == 1) {
                    continue;
                }
                vis[temp.id] = 1;
                dis[temp.id] = temp.dis;
                for (int j = 0; j < graph[temp.id].size(); j++) {
                    if (dis[graph[temp.id].get(j).to] > dis[temp.id] + graph[temp.id].get(j).weight) {
                        queue.add(new Node(graph[temp.id].get(j).to, dis[temp.id] + graph[temp.id].get(j).weight));
                        dis[graph[temp.id].get(j).to] = dis[temp.id] + graph[temp.id].get(j).weight;
                    }
                }
            }
            queue = new PriorityQueue<>();
            vis = new int[n + 1];
            rd[v] = 0;
            queue.add(new Node(v, rd[v]));
            while (!queue.isEmpty()) {
                Node temp = queue.remove();
                if (vis[temp.id] == 1) continue;
//                if (temp.id == u) break;
                vis[temp.id] = 1;
                rd[temp.id] = temp.dis;
                for (int j = 0; j < graph[temp.id].size(); j++) {
                    if (rd[graph[temp.id].get(j).to] > rd[temp.id] + graph[temp.id].get(j).weight) {
                        queue.add(new Node(graph[temp.id].get(j).to, rd[temp.id] + graph[temp.id].get(j).weight));
                        rd[graph[temp.id].get(j).to] = rd[temp.id] + graph[temp.id].get(j).weight;
                    }
                    if (dis[temp.id] < dis[graph[temp.id].get(j).to] && dis[temp.id] + rd[graph[temp.id].get(j).to] + graph[temp.id].get(j).weight == dis[v]) {
                        ans[graph[temp.id].get(j).id]++;
                    }
                }
            }
        }
        for (int i = 0; i < m; i++) {
            out.println(ans[i]);
        }
        out.close();
    }

    static void init(long[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = (long) (Long.MAX_VALUE * 0.8);
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

class Edge {
    int to;
    int weight;
    int id;

    public Edge(int to, int weight, int id) {
        this.to = to;
        this.weight = weight;
        this.id = id;
    }
}

class Node implements Comparable {
    int id;
    long dis;

    public Node(int u, long di) {
        this.id = u;
        this.dis = di;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Node) {
            return Long.compare(this.dis, ((Node) o).dis);
        } else {
            throw new RuntimeException("");
        }
    }
}
