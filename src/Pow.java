public class Pow implements Runnable{
    private double a;
    private Object critical;

    public Pow(double a, Object critical){
        this.a = a;
        this.critical = critical;
    }

    @Override
    public void run() {
        synchronized (critical){

        }
    }
}
