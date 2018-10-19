package controller;

import data.Identifier;
import data.Set;

import java.math.BigInteger;

public interface ControllerReceiverInterface {

    public Set<BigInteger> getSet(Identifier identifier);

    public void insert(Identifier id, Set set);

    public void print(Set set);

    public void printError(String message);
}
