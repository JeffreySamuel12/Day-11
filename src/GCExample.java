import java.lang.ref.Cleaner;

public class GCExample {
    private static final Cleaner cleaner = Cleaner.create();

    // Resource that needs to be cleaned up
    private static class State implements Runnable {
        @Override
        public void run() {
            System.out.println("GCExample instance is being cleaned up");
        }
    }

    private final Cleaner.Cleanable cleanable;

    // Constructor
    public GCExample() {
        this.cleanable = cleaner.register(this, new State());
        System.out.println("GCExample instance created");
    }

    public static void main(String[] args) {
        // Create multiple instances of GCExample
        for (int i = 0; i < 5; i++) {
            GCExample example = new GCExample();
        }

        // Nullify references
        System.out.println("Nullifying references...");
        for (int i = 0; i < 5; i++) {
            GCExample example = null;
        }

        // Request garbage collection
        System.out.println("Requesting garbage collection...");
        System.gc();

        // Give some time for garbage collection to happen
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main method completed");
    }
}
