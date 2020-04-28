package exception;

public class BadFileException extends Exception {

    /**
     * A simple exception class which displays a message and exits.
     * @param s - Message to be displayed
     */

    public BadFileException(String s) {
        System.out.println(s);
        System.exit(0);
    }
}
