package net.intelie.challenges;

public class MemoryEventStore
        extends VectorEventStore
        //extends SynchronizedArrayListEventStore
        //extends SynchronizedLinkedListEventStore
        //extends ManuallySynchronizedEventStore
{
    /*
      This class must inherit from one of the options above.

      Each option has its own implementation, and each has its advantages and disadvantages.
      These advantages and disadvantages are explained on each implementation .java file.

      The main(String[]) method on Main class has a basic performance test that can be run
      to assess the performance of each implementation.

      I used these articles to create my solutions:
      https://dzone.com/articles/arraylist-vs-linkedlist-vs
      https://www.codejava.net/java-core/collections/understanding-collections-and-thread-safety-in-java
      https://crunchify.com/hashmap-vs-concurrenthashmap-vs-synchronizedmap-how-a-hashmap-can-be-synchronized-in-java/

      Test results:

        Test started for: class net.intelie.challenges.VectorEventStore
        500K entries added/retrieved in 443 ms
        500K entries added/retrieved in 1683 ms
        500K entries added/retrieved in 871 ms
        500K entries added/retrieved in 2453 ms
        500K entries added/retrieved in 2405 ms
        For class net.intelie.challenges.VectorEventStore the average time is 1571 ms

        Test started for: class net.intelie.challenges.SynchronizedArrayListEventStore
        500K entries added/retrieved in 454 ms
        500K entries added/retrieved in 1746 ms
        500K entries added/retrieved in 2231 ms
        500K entries added/retrieved in 1047 ms
        500K entries added/retrieved in 3232 ms
        For class net.intelie.challenges.SynchronizedArrayListEventStore the average time is 1742 ms

        Test started for: class net.intelie.challenges.SynchronizedLinkedListEventStore
        500K entries added/retrieved in 1958 ms
        500K entries added/retrieved in 1913 ms
        500K entries added/retrieved in 1134 ms
        500K entries added/retrieved in 2580 ms
        500K entries added/retrieved in 1485 ms
        For class net.intelie.challenges.SynchronizedLinkedListEventStore the average time is 1814 ms

        Test started for: class net.intelie.challenges.ManuallySynchronizedEventStore
        --- the test didn't finish ---

      Analysing these numbers, I chose to implement the EventStore using the Vector<> collection,
      as it is synchronized and showed to have the best performance to add and read a lot of data.
     */
}
