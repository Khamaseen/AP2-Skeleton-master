import controller.Controller;
import controller.ControllerMainInterface;

public class Main {

    private static ControllerMainInterface controller;


    private static void start() {
        controller.start();
    }

    public static void main(String[] argv) {
        controller = new Controller();
        start();
    }
}
