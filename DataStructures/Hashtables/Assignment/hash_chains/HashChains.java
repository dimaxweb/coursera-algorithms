import java.io.*;
import java.util.*;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<LinkedList<String>> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;

    public static void main(String[] args) throws IOException {

        new HashChains().processQueries();
    }


    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int)hash % bucketCount;
    }



    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private LinkedList<String> findBucket(String s){
        int hash = hashFunc(s);
        LinkedList<String> lstChain = elems.get(hash);
        return lstChain;

    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void processQuery(Query query) {

        int hash;
        LinkedList<String> lstChain;

        switch (query.type) {
            case "add":

                hash = hashFunc(query.s);
                lstChain = elems.get(hash);

                if(lstChain!=null){
                    boolean isElementExists = false;
                    ListIterator<String> listIterator = lstChain.listIterator();
                    while (listIterator.hasNext()) {
                        String q = listIterator.next();
                        if(q.equals(query.s)){
                            isElementExists = true;
                        }
                    }

                    if(!isElementExists){
                        lstChain.addFirst(query.s);
                        elems.set(hash,lstChain);
                    }
                }
                else{
                    lstChain = new LinkedList<String>();
                    lstChain.addFirst(query.s);
                    elems.set(hash,lstChain);
                }

                break;
            case "del":
                hash = hashFunc(query.s);
                lstChain = elems.get(hash);
                if(lstChain!=null){
                    lstChain.remove(query.s);
                }

                break;
            case "find":

                hash = hashFunc(query.s);
                lstChain = elems.get(hash);
                boolean elementFound = false;

                if(lstChain!=null){

                    for (String cur : lstChain){
                        if(cur.equals(query.s)){
                            elementFound = true;
                        }
                    }
                }

                writeSearchResult(elementFound);
                break;

            case "check":

                hash = query.ind;
                lstChain = elems.get(hash);
                if(lstChain!=null) {
                    for (String cur : lstChain)
                        out.print(cur + " ");

                }

                    out.println();


                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        int queryCount = in.nextInt();

        for(int i=0;i<=this.bucketCount;i++){
            elems.add(null);
        }

        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
