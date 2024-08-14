class MatrixMultiplication {
    private static final int SIZE = 3; // Size of the matrices
    private static int[][] A = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9} };
    private static int[][] B = { {9, 8, 7}, {6, 5, 4}, {3, 2, 1} };
    private static int[][] C = new int[SIZE][SIZE];

    public static void main(String[] args) {
        Thread[] threads = new Thread[SIZE * SIZE];
        int threadCount = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                threads[threadCount] = new Thread(new Worker(i, j));
                threads[threadCount].start();
                threadCount++;
            }
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the result matrix
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Worker implements Runnable {
        private int row;
        private int col;

        Worker(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void run() {
            for (int k = 0; k < SIZE; k++) {
                synchronized (C) {
                    C[row][col] += A[row][k] * B[k][col];
                }
            }
        }
    }
}
