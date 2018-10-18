package input;

public class Token {

    //public static final int EOF = 1; //end of line
    public static final int WHITE_SPACE = 2;
    public static final int ZERO = 3; //natural number zero
    public static final int NATURAL = 4; //natural number without zero
    public static final int CHAR = 5; //char

    public static final int START_SET = 6; //start of set
    public static final int END_SET = 7; //end of set
    public static final int SEPERATOR = 8; //Separator between units in a set

    public static final int START_EXPR = 9; //start of expression
    public static final int END_EXPR = 10; //end of expression

    public static final int INTERSECT = 11;
    public static final int UNION = 12;
    public static final int COMPLEMENT = 13;
    public static final int SYM_DIFF = 14;

    public static final int COMMENT = 15; //comment character
    public static final int PRINT = 16; //print character
    public static final int ASSIGNMENT = 17; //assignment character

    public static final int UNDEFINED = 18;

    public static final int EOL = 19;
    public static final int EOF = 20;

    public int kind = EOF; //INITIAL SET TO END OF LINE
    public char character;

    public Token() {
        kind = EOF;
    }

    public Token(char character) {
        this.character = character;
        getKind(this);
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    private static void getKind(Token t) {
        if( t.character == (char) 10 ) { t.setKind(EOL); return; }
        if( Character.isWhitespace(t.character) ) { t.setKind(WHITE_SPACE); return; }
        if( Character.isAlphabetic(t.character) ) { t.setKind(CHAR); return; }
        if( Character.isDigit(t.character)) {
            if( t.character == 0 ) { t.setKind(ZERO); return; }
            else { t.setKind(NATURAL); return; }
        }
        switch(t.character) {
            case '{': t.setKind(START_SET); return;
            case '}': t.setKind(END_SET); return;
            case ',': t.setKind(SEPERATOR); return;

            case '(': t.setKind(START_EXPR); return;
            case ')': t.setKind(END_EXPR); return;

            case '*': t.setKind(INTERSECT); return;
            case '+': t.setKind(UNION); return;
            case '-': t.setKind(COMPLEMENT); return;
            case '|': t.setKind(SYM_DIFF); return;

            case '/': t.setKind(COMMENT); return;
            case '?': t.setKind(PRINT); return;
            case '=': t.setKind(ASSIGNMENT); return;

            default: t.setKind(UNDEFINED); return;
        }
    }

}
