import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
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
                it.startTime  = 0;
                items[i] = it;
            }
        }

        public ThreadItem AssignJob(int jobStartTime){

            ThreadItem  item    = items[0];

            ThreadItem itemCopy= new ThreadItem();
            itemCopy.threadId  = item.threadId;
            itemCopy.startTime  =item.startTime;

            item.startTime+=jobStartTime;

            SiftDown(item,0);

            return itemCopy;

        }

        public void Swap(int i,int smallest,ThreadItem [] Arr){
            ThreadItem t = Arr[i];
            Arr[i] = Arr[smallest];
            Arr[smallest]  =t;
        }

        public void SiftDown(ThreadItem item,int index){

            ThreadItem leftItem = null;
            int left = (2 * index)  + 1;
            if(left  < items.length){
                leftItem  = items[left];
            }

            ThreadItem rightItem = null;
            int right = (2 * index)  + 2;
            if(right  < items.length){
                rightItem  = items[right];
            }

            int indexSmallest = index;
            int timeSmallest   = item.startTime;


            if(leftItem!=null){
                if(leftItem.startTime  < timeSmallest || (leftItem.startTime == timeSmallest && leftItem.threadId  < item.threadId) ){
                    timeSmallest = leftItem.startTime;
                    indexSmallest  = left;
                }
            }


            if(rightItem!=null){
                if(rightItem.startTime  < timeSmallest || (rightItem.startTime == timeSmallest && rightItem.threadId  < item.threadId) ){
                    timeSmallest = rightItem.startTime;
                    indexSmallest  = right;
                }
            }

            if(item.startTime !=timeSmallest ){

                Swap(index,indexSmallest,items);

                SiftDown(items[indexSmallest],indexSmallest);
            }




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
          //new JobQueue().solve();
        new JobQueue().solveStressTesting();
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
            assignedThreads[i] = queque.AssignJob(duration);

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

    long nextLong(Random rng, long n) {
        // error checking and 2^x checking removed for simplicity.
        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits-val+(n-1) < 0L);
        return val;
    }

    public  int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand  = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public boolean compareAlgorithms(ThreadItem [] items1,int [] workers,long [] times ){

        boolean isEqual = true;
        if(items1.length != workers.length || items1.length!=times.length){
            throw new IllegalArgumentException("Items lenght need to be equal.Items1 length" + items1.length + "Workers length:"+workers.length + "Times length:" + times.length);
        }
        for(int i=0;i<items1.length;i++){

            ThreadItem  item = items1[i];
            if(item.threadId!=workers[i] || item.startTime!=times[i]){
                isEqual =false;
                break;
            }

        }

        return isEqual;
    }

    public void stressTest(){

        boolean isEqual = true;
        int numOfIterations = 0;

        while(isEqual){

            int min=1,max = 10;
            numWorkers = randInt(min,max);
            int m = randInt(min,max);

            jobs = new int[m];

            for (int i = 0; i < m; ++i) {
                jobs[i] = randInt(min,max);
            }

            assignJobs();
            assignJobs2();

            isEqual = compareAlgorithms(this.assignedThreads,this.assignedWorker,this.startTime);

            numOfIterations ++;

        }

        out.println("Exit on input:");
        out.println("Num of workers:" + numWorkers);
        out.println("Jobs length:" + jobs.length);
        for(int i=0;i<jobs.length;i++){
            out.print(" " + jobs[i]);
        }

        out.println("");
        out.println("Brute force algorithm....");
        writeResponse();
        out.println("Priority Queque algorithm....");
        writeResponse2();


    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs2();
        writeResponse2();
        out.close();
    }

    public void solveStressTesting(){
        out = new PrintWriter(new BufferedOutputStream(System.out));
        stressTest();
//        writeResponse();
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
