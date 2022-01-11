package az.elvin.thread.v1.threadClass;

public class MainThread extends Thread {

    public void run()
    {
        try {
            System.out.println(
                    "Thread " + Thread.currentThread().getId()
                            + " is running");
        }
        catch (Exception e) {
            System.out.println("Exception is caught");
        }
    }
}
