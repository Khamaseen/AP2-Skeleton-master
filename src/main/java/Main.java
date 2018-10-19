import controller.Controller;

public class Main {

    private static Controller controller;


    private static void start() {
        controller.start();
    }

    public static void main(String[] argv) {
        controller = new Controller();
        start();
    }
}
