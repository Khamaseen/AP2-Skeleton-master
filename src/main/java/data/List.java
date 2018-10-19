package data;

public class List<E extends Comparable<E>> implements ListInterface<E> {

    private class Node {
        E data;
        Node prior, next;

        public Node(E d) {
            this(d, null, null);
        }

        public Node(E data, Node prior, Node next) {
            this.data = data == null ? null : data;
            this.prior = prior;
            this.next = next;
        }

    }

    private int size;
    private Node head, tail, current;

    public boolean isEmpty() {
        return size == 0;
    }

    public ListInterface<E> init() {
        size = 0;
        head = null;
        tail = null;
        current = null;
        return this;
    }

    public int size() {
        return size;
    }

    public ListInterface<E> insert(E d) {
        Node newNode = new Node(d);

        if(isEmpty()) {
            head = newNode;
            tail = newNode;
            current = newNode;
        }
        else goToFirst();

        for(int i = 0; i < size(); i++) {
            if(d.compareTo(current.data) > 0 && current.next != null) {
                goToNext();
            }
            else {
                if(d.compareTo(current.data) > 0) {
                    newNode.prior = current;
                    newNode.next = current.next;
                    current.next = newNode;
                }
                else {
                    if(current.prior != null) current.prior.next = newNode;
                    newNode.prior = current.prior;
                    newNode.next = current;
                    current.prior = newNode;
                }

                current = newNode;
                break;
            }
        }
        size++;
        if(head == newNode.next) head = newNode;
        if(tail == newNode.prior) tail = newNode;
        return this;
    }

    public E retrieve() {
        if(size == 0) return null;
        else return current.data;
    }

    public ListInterface<E> remove() {
        if(size == 0) return null;
        else if(size == 1) {
            head = null;
            tail = null;
            current = null;
            size = 0;
        }

        if(current == tail) {
            current.prior.next = null;
            tail = current.prior;
        }
        else if(current == head) {
            current.next.prior = null;
            head = current.next;
        }
        else {
            current.prior.next = current.next;
            current.next.prior = current.prior;
            current = current.next;
        }
        size--;

        return this;
    }

    public boolean find(E d) {
        if(size == 0 || head == null) return false;

        current = head;
        for(int i = 0; i < size; i++) {
            if(current.data.compareTo(d) == 0) {
                return true;
            }
            else goToNext();
        }
        return false;
    }

    public boolean goToFirst() {
        if(size == 0) return false;
        else {
            current = head;
            return true;
        }
    }

    public boolean goToPos(int position) {
        if(size == 0) return false;
        else {
            goToFirst();
            for(int i = 0; i < position; i++) {
                goToNext();
            }
            return true;
        }
    }

    public boolean goToLast() {
        if(size == 0) return false;
        while(current.next != null) {
            current = current.next;
        }
        return true;
    }

    public boolean goToNext() {
        if(size == 0 || current.next == null) return false;
        else {
            current = current.next;
            return true;
        }
    }

    public boolean goToPrevious() {
        if(size == 0 || current == head) return false;
        else {
            current = current.prior;
            return true;
        }
    }

    public ListInterface<E> copy() {
        ListInterface<E> tempList = new List<E>();
        Node tempPtr = head;

        int jumps = 0;
        while(true) {
            if(tempPtr == null || tempPtr.data.compareTo(current.data) == 0) break;
            else {
                tempPtr = tempPtr.next;
                jumps++;
            }
        }
        current = head;
        for(int i = 0; i < size; i++) {
            tempList = tempList.insert(current.data);
        }
        tempList.goToFirst();
        for(int i = 0; i < jumps; i++) tempList.goToNext();
        return tempList;
    }

    public int getCurrentPosition() {
        Node tempPtr = head;
        for(int i = 0; i < size(); i++) {
            if(tempPtr == current) {
                return i;
            }
            else {
                goToNext();
            }
        }
        return -1;
    }
}