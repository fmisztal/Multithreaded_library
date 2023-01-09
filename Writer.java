package Lab4.Zad_1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Writer implements Runnable {
    public Library lib;
    public Deque<Book> books;
    public Set<Book> writtenBooks;

    public Writer(Library lib) {
        this.lib = lib;
        this.books = lib.books;
        this.writtenBooks = lib.writtenBooks;
    }

    public void doAction(String action, int time) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ": " + action);
        Thread.sleep((int) (Math.random() * time));
    }

    @Override
    public void run() {
        try {
            while (!books.isEmpty()) {
                Book b;
                synchronized (books.getFirst()) {
                    b = books.getFirst();
                    books.pop();
                }
                doAction("Writing - " + b.name, ThreadLocalRandom.current().nextInt(8000, 16000));
                doAction("Finished - " + b.name + "!", 0);
                synchronized (writtenBooks) {
                    writtenBooks.add(b);
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
