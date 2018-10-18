package input;

public class NaturalNumber {

    private static int START = 0;
    private static int STARTING_LENGTH = 2;

    public char[] naturalNumber;
    private int lastPosition;

    public NaturalNumber() {
        naturalNumber = new char[STARTING_LENGTH];
        lastPosition = START;
    }

    public NaturalNumber(char first) {
        naturalNumber = new char[STARTING_LENGTH];
        lastPosition = START;

        add(first);
    }

    public void add(char character) {
        if( !(lastPosition < naturalNumber.length) ) {
            naturalNumber = doubleNaturalNumber(naturalNumber, lastPosition);
        }
        naturalNumber[lastPosition++] = character;
    }

    private char[] doubleNaturalNumber(char[] instance, int position) {
        naturalNumber = new char[naturalNumber.length * 2];
        for(int x = 0; x < position; x++) {
            naturalNumber[x] = instance[x];
        }
        return naturalNumber;
    }

    public Long getLong() {
        long temp = 0;
        int backwards = lastPosition - 1;
        for(int x = 0; x < lastPosition; x++) {
            temp += (long) ( ((int) naturalNumber[backwards]) - 48 ) * (Math.pow(10.0, (double) x));
            backwards--;
        }

        return temp; //somechange
    }
}
