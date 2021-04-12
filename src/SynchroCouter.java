public class SynchroCouter {
    public static volatile int count = 0;

    public int getCount(){
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }
}
