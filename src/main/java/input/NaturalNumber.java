package input;

import java.math.BigInteger;

public class NaturalNumber {

    private static int START = 0;
    private static int STARTING_LENGTH = 2;

    public char[] naturalNumber;
    private int lastPosition;

    public NaturalNumber(char first) {
        naturalNumber = new char[STARTING_LENGTH];
        lastPosition = START;

        add(first);
    }

    public void add(char character) {
        if(!(lastPosition < naturalNumber.length)) {
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

    public BigInteger getBigInteger(){
        char[] temp = new char[lastPosition];
        for(int i=0; i<lastPosition; i++){
            temp[i] = naturalNumber[i];
        }
        String str = String.copyValueOf(temp);

        return new BigInteger(str);
    }
}
