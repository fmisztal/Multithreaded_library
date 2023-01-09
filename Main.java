package Lab4.Zad_1;

public class Main {
    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Wrong number of arguments!");
            return;
        }

        try {
            int B = Integer.parseInt(args[0]); // number of books
            int W = Integer.parseInt(args[1]); // number of writers
            int R = Integer.parseInt(args[2]); // number of readers

            Library lib = new Library();
            lib.numberOfBooks = B;

            Writer[] writers = new Writer[W];
            Reader[] readers = new Reader[R];

            for (int i = 0; i < B; i++)
                lib.books.add(new Book("Book " + String.valueOf(i + 1)));

            for (int i = 0; i < W; i++) {
                writers[i] = new Writer(lib);
                Thread t = new Thread(writers[i], "Writer " + (i + 1));
                t.start();
            }

            for (int i = 0; i < R; i++) {
                readers[i] = new Reader(lib);
                Thread t = new Thread(readers[i], "Reader " + (i + 1));
                t.start();
            }
        } catch (Exception ex) {
            System.out.println("Wrong type of arguments!");
            return;
        }
    }
}
