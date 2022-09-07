package lab1;
import java.io.*;
import java.util.*;


public class Matching {
    public static void main(String[] args) {
        QReader in = new QReader();
        QWriter out = new QWriter();
        int n = in.nextInt();
        String[] manName = new String[n];
        String[] womanName = new String[n];
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = -1;
        }
        Map<String,Integer> man = new HashMap<>();
        Map<String,Integer> woman = new HashMap<>();
        for (int i = 0; i < n; i++) {
            manName[i] = in.next();
            man.put(manName[i], i);
        }
        for (int i = 0; i < n; i++) {
            womanName[i] = in.next();
            woman.put(womanName[i], i);
        }
        int[][] manList = new int[n][n];
        int[][] womanList = new int[n][n];
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String temp = in.next();
                manList[i][j] = woman.get(temp);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String temp = in.next();
                womanList[i][j] = man.get(temp);
                res[i][man.get(temp)] = j;
            }
        }
        Queue<String> freeMan = new LinkedList<>(Arrays.asList(manName).subList(0, n));
        while(!freeMan.isEmpty()){
            String temp = freeMan.remove();
            int number = man.get(temp);
            boolean f = false;
            for (int i = 0; i < n; i++) {
                int ord = manList[number][i];
                if (ans[ord] == -1){
                    ans[ord] = number;
                    f = true;
                    break;
                } else if (res[ord][number] < res[ord][ans[ord]]){
                    freeMan.add(manName[ans[ord]]);
                    ans[ord] = number;
                    f = true;
                    break;
                }
            }
            if (!f){
                freeMan.add(temp);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (ans[j] == i){
                    out.print(manName[i] + " " + womanName[j] + "\n");
                }
            }
        }
        out.close();
    }
}
class QReader {
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

class QWriter implements Closeable {
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