package controller;

import data.Identifier;
import data.Model;
import data.Set;
import input.ReceiverOfInput;
import ui.View;

import java.math.BigInteger;

public class Controller implements ControllerMainInterface, ControllerReceiverInterface{

    private Model model;
    private ReceiverOfInput receiver;
    private View view;

    public Controller() {
        model = new Model();
        receiver = new ReceiverOfInput();
        receiver.injectController(this);
        view = new View(System.out);
    }

    //used by main
    public void start() {
        receiver.readInput();
    }

    //used by receiverofinput
    public Set<BigInteger> getSet(Identifier identifier) {
        return model.obtain(identifier);
    }

    //used by receiverofinput
    public void insert(Identifier id, Set set) {
        model.insert(id, set);
    }

    //called by receiver, controller works on set directly
    public void print(Set set) {
        String answer = set.giveString();
        view.displayAnswers(String.format(answer));
    }

    //receiver
    public void printError(String message) {
        view.displayAnswers(message);
    }
}

