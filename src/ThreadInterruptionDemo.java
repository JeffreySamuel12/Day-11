public class ThreadInterruptionDemo {
    public static void main(String[] args) {
        Thread taskThread = new Thread(new MyTask());
        taskThread.start();

        try {
            // Let the thread run for a while
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Interrupt the thread
        System.out.println("Main thread: Interrupting the task thread...");
        taskThread.interrupt();
    }
}

class MyTask implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Task thread: Running...");
            try {
                Thread.sleep(500); // Simulate some work
            } catch (InterruptedException e) {
                System.out.println("Task thread: Interrupted!");
                // Exit the loop if interrupted during sleep
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Task thread: Exiting...");
    }
}
