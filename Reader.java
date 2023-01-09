package Lab4.Zad_1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Reader implements Runnable {
    public Library lib;
    public Book borrowedBook;
    public Set<Book> books;
    public Set<Book> booksRead = new HashSet<>();
    public Reader(Library lib) {
        this.lib = lib;
        this.books = lib.writtenBooks;
        borrowedBook = null;
    }
    public void doAction(String action, int time) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ": " + action);
        Thread.sleep((int) (Math.random() * time));
    }

    @Override
    public void run() {
        try {
            while (booksRead.size() < lib.numberOfBooks) {

                if (!books.isEmpty() && borrowedBook == null) {
                    synchronized (books) {
                        for (Book b : books) {
                            if (!booksRead.contains(b)) {
                                borrowedBook = b;
                                books.remove(b);
                                break;
                            }
                        }
                    }
                }

                if(borrowedBook != null) {
                    doAction("Reading - " + borrowedBook.name, ThreadLocalRandom.current().nextInt(6000, 10000));
                    doAction("Read - " + borrowedBook.name + "!", 0);
                    synchronized (books) {
                        books.add(borrowedBook);
                        booksRead.add(borrowedBook);
                        borrowedBook = null;
                    }
                }

//                Thread.sleep(0);
                Thread.yield();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
