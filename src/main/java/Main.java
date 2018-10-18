import controller.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    private static Controller controller;


    private void start() {
        // Create a scanner on System.in
        
        // While there is input, read line and parse it.
    }

    public static void main(String[] argv) {
        /**
         * original code from skeleton
         */
        new Main().start();


        /**
         * code self made
         */
        File file = new File("/home/dennis/Programs/eclipse/workspace/calc_in");
        try {
            controller = new Controller(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        controller.start();
    }
}
