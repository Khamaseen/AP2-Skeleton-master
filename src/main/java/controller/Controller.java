package controller;

import data.Identifier;
import data.Model;
import data.Set;
import input.ReceiverOfInput;
import ui.View;

import java.io.InputStream;

public class Controller {

    private Model model;
    private ReceiverOfInput receiver;
    private View view;

    public Controller(InputStream in) {
        model = new Model();
        receiver = new ReceiverOfInput(in);
        receiver.injectController(this);
        view = new View(System.out);
    }

    //used by main
    public void start() {
        receiver.readInput();
    }

    //used by receiverofinput
    public Set<Long> getSet(Identifier identifier) {
        return model.obtain(identifier);
    }

    //used by receiverofinput
    public void insert(Identifier id, Set set) {
        model.insert(id, set);
    }

    //called by receiver, controller calls model to give to view
    public void print(Identifier id, int count) {
        Set set = model.obtain(id);
        String answer = set.toString();
        answer = String.format("%d: "+answer, count);
        view.displayAnswers(answer);
    }

    //called by receiver, controller works on set directly
    public void print(Set set, int count) {
        String answer = set.giveString();
        answer = String.format("%d: "+answer, count);
        view.displayAnswers(answer);
    }

    public void printError(String message) {
        view.displayAnswers(message);
    }

}

