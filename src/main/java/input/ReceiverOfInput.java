package input;

import controller.APException;
import controller.Controller;
import data.Identifier;
import data.Set;

import java.math.BigInteger;

public class ReceiverOfInput {

    private TokenStream tokenStream;
    private Controller controller;
    private int count;

    public ReceiverOfInput() {
        tokenStream = new TokenStream();
    }

    public void injectController(Controller controller) {
        this.controller = controller;
    }

    public void readInput() {
        count = 1;
        try {
            while(!readProgram()) {}
        } catch (APException e) {
           // e.printStackTrace();
        }
        tokenStream.close();
    }

    private Boolean readProgram() throws APException {
        try {
           if(!readStatements()){
               return false;
           }
           else{
               return true;
           }
        } catch (APException e) {
            controller.printError(e.getMessage());
            tokenStream.skipLine();
        }
        count++;
        return false;
    }

    private boolean readStatements() throws APException {
        while(true) {
            Token t = tokenStream.skipAndRead();
            switch(t.kind) {
                case Token.COMMENT: readComment(); break;
                case Token.PRINT: readPrint(); break;
                case Token.CHAR: readAssignment(t.character); break;
                case Token.EOL:
                    String message = String.format("%d: error no statement", count);
                    throw new APException(message);
                case Token.EOF: return true;
                default:
                    String except = String.format("%d: error unknown start of statement", count);
                   throw new APException(except);
            }
            count++;
        }
    }

    private void readComment() throws APException {
        tokenStream.skipLine();
    }

    private void readPrint() throws APException {
        Set<BigInteger> set = new Set<BigInteger>();

        Token t = tokenStream.skipAndRead();
        switch(t.kind) {
            case Token.CHAR:
            case Token.START_SET:
                tokenStream.putBack(t.character);
                set = readExpression();
                break;
            case Token.START_EXPR:
                tokenStream.putBack(t.character);
                set = readExpression();
                break;
            default:
                tokenStream.putBack(t.character);
                String except = String.format("%d: error unknown character after print statement", count);
                throw new APException(except);
        }

        t = tokenStream.skipAndRead();
        if(t.kind == Token.EOL) {
            printSet(set);
            return;
        }
        if(t.kind == Token.EOF) {
            tokenStream.putBack(t.character);
            printSet(set);
            return;
        }

        tokenStream.putBack(t.character);
        String message = String.format("%d: error unexpected character after print statement", count);
        throw new APException(message);
    }

    private void readAssignment(char first) throws APException {
        Identifier identifier = readIdentifier(first);
        Token t = tokenStream.skipAndRead();
        if(t.kind != Token.ASSIGNMENT) {
            tokenStream.putBack(t.character);
            String message = String.format("%d: error expected an assignment after an identifier", count);
            throw new APException(message);};

        Set<BigInteger> set = new Set<BigInteger>();
        t = tokenStream.skipAndRead();
        switch(t.kind) {
            case Token.CHAR:
            case Token.START_SET:
                tokenStream.putBack(t.character);
                set = readExpression();
                break;
            case Token.START_EXPR:
                tokenStream.putBack(t.character);
                set = readExpression();
                break;
            default:
                tokenStream.putBack(t.character);
                String message = String.format("%d: error expexted an assignement after \"=\"", count);
                throw new APException(message);
        }

        t = tokenStream.skipAndRead();
        if(t.kind == Token.EOL) {
            insertIntoHash(identifier, set);
            return;
        }
        if(t.kind == Token.EOF) {
            tokenStream.putBack(t.character);
            insertIntoHash(identifier, set);
            return;
        }

        tokenStream.putBack(t.character);
        String except = String.format("%d: error unexpected character after statement", count);
        throw new APException(except);
    }



    private Set<BigInteger> readExpression() throws APException {
        Set<BigInteger> set = readTerm();

        while(true) {
            Token t = tokenStream.skipAndRead();
            switch(t.kind) {
                case Token.UNION:
                    set = (Set<BigInteger>) set.union(readTerm());
                    break;
                case Token.COMPLEMENT:
                    set = (Set<BigInteger>) set.complement(readTerm());
                    break;
                case Token.SYM_DIFF:
                    set = (Set<BigInteger>) set.symmetricDifference(readTerm());
                    break;
                default:
                    tokenStream.putBack(t.character);
                    return set;
            }
        }
    }

    private Set<BigInteger> readTerm() throws APException {
        Set<BigInteger> set = readFactor();

        while(true) {
            Token t = tokenStream.skipAndRead();
            switch(t.kind) {
                case Token.INTERSECT: set = (Set<BigInteger>) set.intersect(readFactor()); break;
                default:
                    tokenStream.putBack(t.character);
                    return set;
            }
        }
    }

    private Set<BigInteger> readFactor() throws APException {
        Set<BigInteger> set = new Set<BigInteger>();

        Token t = tokenStream.skipAndRead();
        switch(t.kind) {
            case Token.START_SET:
                set = readSet();
                return set;
            case Token.CHAR:
                Identifier identifier = readIdentifier(t.character);
                set = (Set<BigInteger>) getSetFromIdentifier(identifier);
                return set;
            case Token.START_EXPR:
                set = readExpression();
                t = tokenStream.skipAndRead();
                if(t.kind != Token.END_EXPR) {
                    tokenStream.putBack(t.character);
                    String excep = String.format("%d: error missing parentheses", count);
                    throw new APException(excep);
                }
                return set;
            default:
                tokenStream.putBack(t.character);
                String excep = String.format("%d: error expecting set, identifier, or expression :: readFactor()", count);
                throw new APException(excep);
        }
    }

    private Set<BigInteger> readSet() throws APException {
        Set<BigInteger> set = new Set<BigInteger>();

        Token t = tokenStream.skipAndRead();
        if (t.kind == Token.END_SET) { return set; }
        else{tokenStream.putBack(t.character);}
        while(true) {
            t = tokenStream.skipAndRead();
            switch(t.kind) {
                case Token.NATURAL:
                    set.addElement(readNaturalNumber(t.character));
                    t = tokenStream.skipAndRead();
                    if(t.kind == Token.SEPERATOR) { break; }
                    if(t.kind == Token.END_SET) { return set; }
                    tokenStream.putBack(t.character);
                    String excep1 = String.format("%d: error unexpected character at set :: readSet()", count);
                    throw new APException(excep1);
                case Token.ZERO:
                    NaturalNumber naturalNumber = new NaturalNumber(t.character);
                    set.addElement(naturalNumber.getBigInteger());
                    t = tokenStream.skipAndRead();
                    if(t.kind == Token.SEPERATOR) { break; }
                    if(t.kind == Token.END_SET) { return set; }
                    tokenStream.putBack(t.character);
                    String answer = String.format("%d: error unexpected character at set :: readSet()", count);
                    throw new APException(answer);

                default:
                    tokenStream.putBack(t.character);
                    String excep = String.format("%d: error unexpected character at set :: readSet()", count);
                    throw new APException(excep);
            }
        }
    }


    private Identifier readIdentifier(char firstChar) throws APException {
        Identifier identifier = new Identifier(firstChar);
        while( true ) {
            Token t = tokenStream.readAny();
            if(t.kind == Token.CHAR || t.kind == Token.NATURAL || t.kind == Token.ZERO) {
                identifier.add(t.character);
            }else {
                tokenStream.putBack(t.character);
                return identifier;
            }
        }
    }

    private BigInteger readNaturalNumber(char first) throws APException {
        NaturalNumber naturalNumber = new NaturalNumber(first);

        while(true) {
            Token t = tokenStream.readAny();
            if(t.kind != Token.NATURAL && t.kind != Token.ZERO) {
                tokenStream.putBack(t.character);
                break;
            }
            naturalNumber.add(t.character);
        }
        return naturalNumber.getBigInteger();
    }

    public void insertIntoHash(Identifier identifier, Set<BigInteger> set) {
        controller.insert(identifier, set);
    }
    public void printSet(Set set) {
        controller.print(set, count);
    }
    public Set<BigInteger> getSetFromIdentifier(Identifier identifier) throws APException {
        Set<BigInteger> set = controller.getSet(identifier);
        if (set == null) {
            String message = String.format("%d: error unknown identifier", count);
            throw new APException(message); }
        return set;
    }
}
