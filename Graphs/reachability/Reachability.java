import java.util.ArrayList;
import java.util.Scanner;

public class Reachability {

    public static int [] marked;

    private static int reach(ArrayList<Integer>[] adj, int x, int y) {
        int [] marked = new int[adj.length];
        ArrayList<Integer> xEdges = adj[x];
        if(xEdges.contains(y)){
            return 1;
        }

        dfs(x,adj);


        return marked[y];
    }

    public static void dfs(int x,ArrayList<Integer>[] adj){

        //Avoid infinite loops
        ArrayList<Integer> children  = adj[x];
        marked[x] = 1;
        for(int n : children){
            if(marked[n]==0){
                dfs(n,adj);
            }
        }

    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(reach(adj, x, y));
    }
}



