import java.util.*;
import java.io.*;

public class tree_orders {
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

	public class TreeOrders {
		int n;
		int[] key, left, right;
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			key = new int[n];
			left = new int[n];
			right = new int[n];
			for (int i = 0; i < n; i++) { 
				key[i] = in.nextInt();
				left[i] = in.nextInt();
				right[i] = in.nextInt();
			}
		}


		public void InOrderRecursive(int index,List<Integer> result){

			if(index == -1) return;

			//left
			InOrderRecursive(left[index],result);

			result.add(key[index]);

			//right
			InOrderRecursive(right[index],result);
		}

		public void PostOrderRecursive(int index,List<Integer> result){

			if(index == -1) return;

			//left
			PostOrderRecursive(left[index],result);

			//right
			PostOrderRecursive(right[index],result);

			System.out.print(key[index] + " ");

//			result.add(key[index]);
		}

		public void PreOrderRecursive(int index,List<Integer> result){

			if(index == -1) return;


//			result.add(key[index]);
			System.out.print(key[index] + " ");

			//left
			PreOrderRecursive(left[index],result);

			//right
			PreOrderRecursive(right[index],result);

		}


		List<Integer> inOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();

			InOrderRecursive(0,result);

			return result;
		}

		List<Integer> preOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
                        // Finish the implementation
                        // You may need to add a new recursive method to do that
			PreOrderRecursive(0,result);
                        
			return result;
		}

		List<Integer> postOrder() {
			ArrayList<Integer> result = new ArrayList<Integer>();
                        // Finish the implementation
                        // You may need to add a new recursive method to do that
            PostOrderRecursive(0,result);
			return result;
		}
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_orders().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}

	public void print(List<Integer> x) {
		for (Integer a : x) {
			System.out.print(a + " ");
		}
		System.out.println();
	}

	public void run() throws IOException {
		TreeOrders tree = new TreeOrders();
		tree.read();
		print(tree.inOrder());
		tree.preOrder();
		System.out.println();
		tree.postOrder();


	}
}
