package data;

import data.Identifier;
import data.Set;

import java.util.HashMap;

public class Model {

    HashMap<Identifier, Set<Long>> hmap;


    public Model() {
        hmap = new HashMap<Identifier, Set<Long>>();
    }

    public void insert(Identifier identifier, Set<Long> set) {
        hmap.put(identifier, set);
    }

    public Set<Long> obtain(Identifier identifier) {
        return hmap.get(identifier);
    }

    public void eraseAll() {
        hmap = new HashMap<Identifier, Set<Long>>();
    }

}