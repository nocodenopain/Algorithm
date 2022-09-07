package lab5;

import java.io.*;
import java.util.*;

public class A {
    static int size = 0;
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        int m = in.nextInt();
        Map<Integer, nn> map = new HashMap<>();
        nn head = new nn();
        nn tail = new nn();
        head.next = tail;
        tail.pre = head;
        while (m-- > 0) {
            String s = in.next();
            if (s.equals("put")) {
                int a = in.nextInt();
                int b = in.nextInt();
                if (map.containsKey(a)){
                    nn temp = map.get(a);
                    temp.delete();
                    nn node = new nn(a,b);
                    map.put(a,node);
                    node.addHead(head);
                } else {
                    if (size < n){
                        size++;
                        nn node = new nn(a,b);
                        node.addHead(head);
                        map.put(a,node);
                    } else {
                        nn temp = tail.pre;
                        temp.delete();
                        map.remove(temp.key);
                        nn node = new nn(a,b);
                        node.addHead(head);
                        map.put(a,node);
                    }
                }
            } else if (s.equals("get")) {
                int a = in.nextInt();
                if (!map.containsKey(a)){
                    out.println(-1);
                } else {
                    nn temp = map.get(a);
                    temp.delete();
                    temp.addHead(head);
                    out.println(temp.val);
                }
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

class nn {
    int key;
    int val;
    nn pre;
    nn next;

    public nn(int key, int val) {
        this.key = key;
        this.val = val;
    }

    public nn() {

    }

    public void delete(){
        this.pre.next = this.next;
        this.next.pre = this.pre;
    }

    public void addHead(nn head){
        this.pre = head;
        this.next = head.next;
        head.next.pre = this;
        head.next = this;
    }
}
