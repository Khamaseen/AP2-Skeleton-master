package data;

import controller.APException;

public class Set<E extends Comparable<E>> implements SetInterface<E> {

    private List<E> list;

    private final int MAX_SIZE = 800;

    public Set() {
        list = new List<E>();
    }

    public Set(E input) {
        list = new List<E>();
        addElement(input);
    }

    public Set(E[] inputs) {
        list = new List<E>();
        for(E element : inputs) {
            list.insert(element);
        }
    }

    public Set(List<E> input) throws APException {
        if(list instanceof ListInterface<?>) {list = (List<E>) input.copy();}
        else{
            throw new APException("error trying to copy a different instance at set");
        }
    }

    public int getSize() {
        return list.size();
    }

    public boolean addElement(E element) {
        if(list.size() >= MAX_SIZE) return false;
        else {
            if(!list.find(element)){
                list.insert(element);
                return true;
            }else{
                return false;
            }
        }
    }

    public boolean removeElement(E element) {
        if(list.isEmpty()) return false;
        else {
            list.find(element);
            list.remove();
            return true;
        }
    }

    public boolean containsElement(E element) {
        return list.find(element);
    }

    public SetInterface<E> copyContents() {
        SetInterface<E> temp = new Set<E>();
        int currentPos = list.getCurrentPosition();

        list.goToFirst();
        for(int i = 0; i < list.size(); i++) {
            if(!temp.containsElement(list.retrieve())) temp.addElement(list.retrieve());
            list.goToNext();
        }

        list.goToPos(currentPos);
        return temp;
    }

    public SetInterface<E> union(SetInterface<E> input) {
        SetInterface<E> temp = null;
        temp = input.copyContents();

        if(isEmpty()) return input;
        else if(input == null || input.isEmpty()) return this;

        this.list.goToFirst();
        for(int i = 0; i < this.getSize(); i++) {
            if(!temp.containsElement(this.list.retrieve())) {
                temp.addElement(this.list.retrieve());
            }
            this.list.goToNext();
        }

        return temp;
    }

    public SetInterface<E> intersect(SetInterface<E> input) {
        SetInterface<E> temp = new Set<E>();
        if(this.getSize() == 0) return this;

        list.goToFirst();
        for(int i = 0; i < this.getSize(); i++) {
            if(input.containsElement(this.list.retrieve())) {
                temp.addElement(this.list.retrieve());
            }
            list.goToNext();
        }
        return temp;
    }

    public SetInterface<E> complement(SetInterface<E> input) {
        SetInterface<E> temp = new Set<E>();
        if(this.getSize() == 0 || input.getSize() == 0) return this;
        list.goToFirst();

        for(int i = 0; i < this.getSize(); i++) {
            E currElem = this.list.retrieve();
            if(!input.containsElement(currElem)) {
                temp.addElement(currElem);
            }
            list.goToNext();
        }
        return temp;
    }

    public SetInterface<E> symmetricDifference(SetInterface<E> input) {
        if(this.isEmpty()) return input;
        else if(input.getSize() == 0) return this;

        SetInterface<E> temp = this.union(input);
        temp = temp.complement(this.intersect(input));
        return temp;
    }

    public String giveString() {
        list.goToFirst();
        String outputString = new String();
        for(int i = 0; i < list.size(); i++) {
            outputString += list.retrieve().toString() + " ";
            if(!list.goToNext()) break;
        }
        return outputString;
    }

    public boolean isEmpty() {
        if(list.isEmpty()) return true;
        else return false;
    }
}