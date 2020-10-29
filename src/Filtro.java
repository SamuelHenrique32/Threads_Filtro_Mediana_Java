public class Filtro extends Thread {

    private int tid, nthreads;

    public Filtro(int tid, int nthreads) {

        this.tid = tid;
		this.nthreads = nthreads;
    }
    
    public void run() {

        System.out.println("Ola da thread " + this.tid);

        //Thread.sleep(10);
    }
}