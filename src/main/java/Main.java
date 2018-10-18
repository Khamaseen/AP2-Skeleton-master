import controller.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    private static Controller controller;


    private static void start() {
        System.out.println("controller start, inside main");
        controller.start();
    }

    public static void main(String[] argv) {
        /**
         * original code from skeleton
         */


        /**
         * code self made
         */
//        File file = new File("/home/dennis/Programs/eclipse/workspace/calc_in");
//        try {
//            controller = new Controller(new FileInputStream(file));
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        System.out.println("inside main ");
        controller = new Controller();
        start();
    }
}
