public class DeadlockLivelockDemo {
    static class Resource {
        private boolean isLocked;

        public synchronized boolean isLocked() {
            return isLocked;
        }

        public synchronized void setLocked(boolean isLocked) {
            this.isLocked = isLocked;
        }
    }

    public static void main(String[] args) {
        final Object resource1 = new Object();
        final Object resource2 = new Object();
        final Resource res1 = new Resource();
        final Resource res2 = new Resource();

        // Deadlock scenario
        Thread t1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: Locked resource 1");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                synchronized (resource2) {
                    System.out.println("Thread 1: Locked resource 2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2: Locked resource 2");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                synchronized (resource1) {
                    System.out.println("Thread 2: Locked resource 1");
                }
            }
        });

        // Livelock scenario
        Thread t3 = new Thread(() -> {
            while (true) {
                synchronized (res1) {
                    if (!res1.isLocked()) {
                        res1.setLocked(true);
                        System.out.println("Thread 3: Locked resource 1");
                        synchronized (res2) {
                            if (res2.isLocked()) {
                                res1.setLocked(false);
                                System.out.println("Thread 3: Released resource 1");
                                continue;
                            }
                            System.out.println("Thread 3: Locked resource 2");
                            break;
                        }
                    }
                }
            }
        });

        Thread t4 = new Thread(() -> {
            while (true) {
                synchronized (res2) {
                    if (!res2.isLocked()) {
                        res2.setLocked(true);
                        System.out.println("Thread 4: Locked resource 2");
                        synchronized (res1) {
                            if (res1.isLocked()) {
                                res2.setLocked(false);
                                System.out.println("Thread 4: Released resource 2");
                                continue;
                            }
                            System.out.println("Thread 4: Locked resource 1");
                            break;
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
