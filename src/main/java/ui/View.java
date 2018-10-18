package ui;

import java.io.PrintStream;

public class View implements ViewInterface {

    private PrintStream streamOut;

    public View(PrintStream out) {
        streamOut = System.out;
    }

    @Override
    public void displayNotification(String message) {
        streamOut.printf("%s" , message);
    }

    @Override
    public void displayAnswers(String answer) {
        System.out.println(answer);
    }

}
