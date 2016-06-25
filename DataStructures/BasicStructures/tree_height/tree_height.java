import java.util.*;
import java.io.*;

public class tree_height {
    class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			return tok.nextToken();
		}
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}

	public class TreeHeight {
		int n;
		int parent[];
		int depths [];
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			parent = new int[n];
			depths   =new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = in.nextInt();
				depths[i] = 0;
			}
		}

		int computeHeight() {
                        // Replace this code with a faster implementation
			int maxHeight = 0;
			for (int vertex = 0; vertex < n; vertex++) {
				int height = 0;
				int i = vertex;
				for (; i != -1; i = parent[i]){
					if(depths[i]!=0){
						height+=depths[i]-1;
						break;
					}
					height++;
				}
				if(parent[vertex]!=-1){
					depths[parent[vertex]] = height;
				}

				maxHeight = Math.max(maxHeight, height);
			}


			return maxHeight;
		}



		int [] getChildren(int parent){
			return new int [10];
		}

		int  maxFromArray(int [] arr){
			int max  = arr.length  > 1  ?  arr[0]  :  0;
			for(int i=0;i<arr.length;i++){
				if(arr[i] > max){
					max = arr[i];
				}
			}
			return max;

		}




	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_height().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}
	public void run() throws IOException {
		TreeHeight tree = new TreeHeight();
		tree.read();
		System.out.println(tree.computeHeight());
	}
}
