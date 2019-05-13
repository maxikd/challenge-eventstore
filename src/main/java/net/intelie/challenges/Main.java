package net.intelie.challenges;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        EventStore vector = new VectorEventStore();
        EventStore syncArray = new SynchronizedArrayListEventStore();
        EventStore syncLinked = new SynchronizedLinkedListEventStore();
        EventStore manuallySync = new ManuallySynchronizedEventStore();

        try {
            performTest(vector);
            performTest(syncArray);
            performTest(syncLinked);
            performTest(manuallySync);
        } catch (Exception e) {

        }
    }

    private static final String[] TYPES = new String[]{"Int", "Long", "String", "Array", "Event"};
    private static final int THREAD_POOL_SIZE = 5;

    private static void performTest(final EventStore store) throws InterruptedException {

        Random random = new Random();

        System.out.println("Test started for: " + store.getClass());
        long averageTime = 0;
        for (int i = 0; i < 5; i++) {

            long startTime = System.nanoTime();
            ExecutorService exServer = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            for (int j = 0; j < THREAD_POOL_SIZE; j++) {
                exServer.execute(new Runnable() {
                    @SuppressWarnings("unused")
                    @Override
                    public void run() {

                        for (int k = 0; k < 500000; k++) {
                            long randomNumber = (long) Math.ceil(Math.random() * 550000);
                            store.insert(new Event(TYPES[random.nextInt(4)], randomNumber));
                        }
                    }
                });
            }

            EventIterator iterator;

            iterator = store.query(TYPES[0], Long.MIN_VALUE, Long.MAX_VALUE);
            iterate(iterator);

            iterator = store.query(TYPES[1], Long.MIN_VALUE, Long.MAX_VALUE);
            iterate(iterator);

            iterator = store.query(TYPES[2], Long.MIN_VALUE, Long.MAX_VALUE);
            iterate(iterator);

            iterator = store.query(TYPES[3], Long.MIN_VALUE, Long.MAX_VALUE);
            iterate(iterator);

            iterator = store.query(TYPES[4], Long.MIN_VALUE, Long.MAX_VALUE);
            iterate(iterator);

            // Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted. Invocation
            // has no additional effect if already shut down.
            // This method does not wait for previously submitted tasks to complete execution. Use awaitTermination to do that.
            exServer.shutdown();

            // Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs, or the current thread is
            // interrupted, whichever happens first.
            exServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

            long endTime = System.nanoTime();
            long totalTime = (endTime - startTime) / 1000000L;
            averageTime += totalTime;
            System.out.println("500K entries added/retrieved in " + totalTime + " ms");
        }
        System.out.println("For " + store.getClass() + " the average time is " + averageTime / 5 + " ms");
        System.out.println();
    }

    private static void iterate(EventIterator iterator) {
        while (iterator.moveNext()) {
            Event e = iterator.current();
            //System.out.println("Type: " + e.type() + "; Timestamp: " + e.timestamp());
        }
    }
}
