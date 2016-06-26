import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class JobQueue {

    public class ThreadItem{
        public int threadId;
        public int startTime;
    }

    public class PriorityJobQueque {

        private ThreadItem [] items;

        public PriorityJobQueque(int [] threads){
            int len  = threads !=null  ? threads.length : 0;
            items = new ThreadItem[len];
            for(int i =0;i<len;i++){
                ThreadItem it = new ThreadItem();
                it.threadId = i;
                items[i] = it;
            }
        }

        public ThreadItem AssignJob(int jobStartTime,int jobNumber){

            if(jobNumber>=items.length && jobNumber%items.length == 0){

//                for(int i=0;i<items.length;i++) {

                    //MinHeapify(items,i);
//                }

                Arrays.sort(items, new Comparator<ThreadItem>() {
                    @Override
                    public int compare(ThreadItem o1, ThreadItem o2) {
                        return o1.startTime  - o2.startTime;
                    }
                });

            }

            int threadIndex = jobNumber % items.length;
            ThreadItem it = items[threadIndex];

            ThreadItem modified = new ThreadItem();
            modified.threadId = it.threadId;
            modified.startTime=it.startTime +  jobStartTime;
            items[threadIndex] = modified;
            return it;
        }

        public void Swap(int i,int smallest,ThreadItem [] Arr){
            ThreadItem t = Arr[i];
            Arr[i] = Arr[smallest];
            Arr[smallest]  =t;
        }

        private void MinHeapify(ThreadItem [] Arr,int i){
            int smallest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (((left < Arr.length) && (Arr[left].startTime  < Arr[i].startTime))) {
                smallest = left;
            }

            if (((right < Arr.length) && (Arr[right].startTime   < Arr[smallest].startTime))) {
                smallest = right;
            }

            if (smallest != i) {
                Swap(i, smallest,Arr);
                MinHeapify(Arr, smallest);
            }

        }

    }


    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    private PriorityJobQueque queque;
    private ThreadItem [] assignedThreads;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void writeResponse2(){
        for (int i = 0; i < assignedThreads.length; ++i) {
            out.println(assignedThreads[i].threadId + " " +assignedThreads[i].startTime);
        }
    }

    private void assignJobs2(){

        queque  = new PriorityJobQueque(new int[numWorkers]);
        assignedThreads  = new ThreadItem[jobs.length];
        for(int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            assignedThreads[i] = queque.AssignJob(jobs[i],i);

        }
    }

    private void assignJobs() {

        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];

        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];

            int bestWorker = 0;
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker])
                    bestWorker = j;
            }

            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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
