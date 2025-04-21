public class Main {

    public static int computeSomething(int x) {
        int result = 0;
        for (int i = 1; i <= 100; i++) {
            result += (x * i) / i; // Simplifies, but still loops
        }
        return result;
    }

    public static void main(String[] args) {
        // Since the JVM is JIT there should be a big drop after repeatedly calling a
        // compute heavy function (adapt parameters to that it's "hard" to run on your
        // own hardware). E.g. this had a steep drop from 20000ns to 2000 after 20 runs
        // which equates to a performance uplift of 10x faster.
        final int runs = 100;
        final int callsPerRun = 10;

        for (int run = 1; run <= runs; run++) {
            long start = System.nanoTime();
            for (int i = 0; i < callsPerRun; i++) {
                computeSomething(i);
            }
            long end = System.nanoTime();
            System.out.printf("[APP] Run %03d Time (ns): %06d%n", run, (end - start));
        }
    }
}
