package driver;

public class Driver {
    public static void main(String[] args) {
        ConsoleInterface console = new ConsoleInterface("bostonmetro.txt");
        console.run();
    }
}
