package data;

import data.Identifier;
import data.Set;

import java.math.BigInteger;
import java.util.HashMap;

public class Model {

    HashMap<Identifier, Set<BigInteger>> hmap;


    public Model() {
        hmap = new HashMap<Identifier, Set<BigInteger>>();
    }

    public void insert(Identifier identifier, Set<BigInteger> set) {
        hmap.put(identifier, set);
    }

    public Set<BigInteger> obtain(Identifier identifier) {
        return hmap.get(identifier);
    }

    public void eraseAll() {
        hmap = new HashMap<Identifier, Set<BigInteger>>();
    }

}