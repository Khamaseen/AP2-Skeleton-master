package data;

public class Identifier {

    private static int START = 0;
    private static int STARTING_LENGTH = 2;

    public char[] identifier;
    private int lastPosition;

    public Identifier(char firstChar) {
        identifier = new char[STARTING_LENGTH];
        lastPosition = START;

        add(firstChar);
    }

    public void add(char character) {
        if( !(lastPosition < identifier.length) ) {
            identifier = doubleIdentifier(identifier, lastPosition);
        }
        identifier[lastPosition++] = character;
    }

    private char[] doubleIdentifier(char[] instance, int position) {
        identifier = new char[identifier.length * 2];
        for(int x = 0; x < position; x++) {
            identifier[x] = instance[x];
        }
        return identifier;
    }

    public int getSize() {
        return lastPosition;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * lastPosition;
        hash = hash * sumOfChar(identifier, lastPosition);
        return hash;
    }

    private static int sumOfChar(char[] row, int last) {
        int sum = 1;
        for( int i =0; i<last; i++) {
            sum += row[i]*i;
            sum += row[i];
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass() == this.getClass()) {
            Identifier comp = (Identifier) o;
            if ( comp.lastPosition == this.lastPosition ) {
                int result = 0;
                for( int i = 0 ; i < this.lastPosition; i++ ) {
                    result += (comp.identifier[i] - this.identifier[i]);
                }
                return result == 0;
            }
        }
        return false;
    }

}
