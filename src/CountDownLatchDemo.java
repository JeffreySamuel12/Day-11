import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) {
        // Create a CountDownLatch with a count of 3
        CountDownLatch latch = new CountDownLatch(3);

        // Create and start three worker threads
        for (int i = 1; i <= 3; i++) {
            new Thread(new Worker(latch, "Worker " + i)).start();
        }

        try {
            // Main thread waits until the latch count reaches zero
            System.out.println("Main thread waiting for workers to finish...");
            latch.await();
            System.out.println("All workers have finished. Main thread proceeding...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Worker implements Runnable {
    private CountDownLatch latch;
    private String name;

    public Worker(CountDownLatch latch, String name) {
        this.latch = latch;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is working...");
            Thread.sleep((int) (Math.random() * 1000)); // Simulate work
            System.out.println(name + " has finished work.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown(); // Decrement the latch count
        }
    }
}
