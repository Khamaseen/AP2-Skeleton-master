package input;

import controller.APException;
import controller.Controller;
import data.Identifier;
import data.Set;

import java.io.InputStream;

public class ReceiverOfInput {

    private TokenStream tokenStream;
    private InputStream streamIn;
    private Controller controller;
    private int count;

    public ReceiverOfInput() {
        tokenStream = new TokenStream();
    }

    public void injectController(Controller controller) {
        this.controller = controller;
    }

    public void readInput() {
        System.out.println("readInput() :: ReceiverOfInput");
        count = 1;
        try {
            while(!readProgram()) {
                // TODO error handling
            }
        } catch (APException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tokenStream.close();
    }

    private Boolean readProgram() throws APException {
      //  System.out.println("readProgram() :: ReceiverOfINput");

        try {
           if(!readStatements()){
               //todo some error thing
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
           // System.out.println("readStatements() while - loop :: ReceiverOfInputs");
            Token t = tokenStream.skipAndRead();
            switch(t.kind) {
                case Token.COMMENT: readComment(); break;
                case Token.PRINT: readPrint(); break;
                case Token.CHAR: readAssignment(t.character); break;
                case Token.EOL: throw new APException("Error no statement");
                case Token.EOF: return true;
                default:
                    return false;
            }
            count++;
            if(count == 107){
                System.out.println("107..");
            }
        }
    }

    private void readComment() throws APException {
        tokenStream.skipLine();
    }

    private void readPrint() throws APException {
        Set<Long> set = new Set<Long>();

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
//			t = tokenStream.skipAndRead();
//			if ( t.kind != Token.END_EXPR ) {}
                break;
            default:
                throw new APException("");
        }

        t = tokenStream.skipAndRead();
        if ( t.kind == Token.EOL ) {
            printSet(set);
            return;
        }
        if ( t.kind == Token.EOF ) {
            tokenStream.putBack(t.character);
            printSet(set);
            return;
        }

        throw new APException("unexpected character after print statement /expecting eol");
    }

    private void readAssignment(char first) throws APException {
        Identifier identifier = readIdentifier(first);
        Token t = tokenStream.skipAndRead();
        if ( t.kind != Token.ASSIGNMENT ) {}

        Set<Long> set = new Set<Long>();
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
			t = tokenStream.skipAndRead();
			if ( t.kind != Token.END_EXPR ) {}
                break;
            default:
                throw new APException("expected an assignment");
        }

        t = tokenStream.skipAndRead();
        if ( t.kind == Token.EOL ) {
            insertIntoHash(identifier, set);
            return;
        }
        if ( t.kind == Token.EOF ) {
            tokenStream.putBack(t.character);
            insertIntoHash(identifier, set);
            return;
        }

        throw new APException("unexpected character after statement");
    }



    private Set<Long> readExpression() throws APException {
        Set<Long> set = readTerm();

        while(true) {
            Token t = tokenStream.skipAndRead();
            switch(t.kind) {
                case Token.UNION:
                    set = (Set<Long>) set.union(readTerm());
                    break;
                case Token.COMPLEMENT:
                    set = (Set<Long>) set.complement(readTerm());
                    break;
                case Token.SYM_DIFF:
                    set = (Set<Long>) set.symmetricDifference(readTerm());
                    break;
                default:
                    tokenStream.putBack(t.character);
                    return set;
            }
        }
    }

    private Set<Long> readTerm() throws APException {
        Set<Long> set = readFactor();

        while(true) {
            Token t = tokenStream.skipAndRead();
            switch(t.kind) {
                case Token.INTERSECT: set = (Set<Long>) set.intersect(readFactor()); break;
                case Token.EOF:
                case Token.EOL:
//				tokenStream.putBack(t.character);
//				return set;
                default:
//				String excep = String.format("$s: expeting end of line or intersect :: readFactor() ", count);
//				throw new controller.APException(excep);
                    tokenStream.putBack(t.character);
                    return set;
            }
        }
    }

    private Set<Long> readFactor() throws APException {
        Set<Long> set = new Set<Long>();

        Token t = tokenStream.skipAndRead();
        switch(t.kind) {
            case Token.START_SET:
                set = readSet();
                return set;
            case Token.CHAR:
                Identifier identifier = readIdentifier(t.character);
                set = (Set<Long>) getSetFromIdentifier(identifier);
                return set;
            case Token.START_EXPR:
                set = readExpression();
                t = tokenStream.skipAndRead();
                if( t.kind != Token.END_EXPR ) {
                    String excep = String.format("%d: missing parentheses", count);
                    throw new APException(excep);
                }
                return set;
            default:
                String excep = String.format("$s: expecting set, identifier, or expression :: readFactor()", count);
                throw new APException(excep);
        }

    }

    private Set<Long> readSet() throws APException {
        Set<Long> set = new Set<Long>();

        while(true) {
            Token t = tokenStream.skipAndRead();
            switch(t.kind) {
                case Token.ZERO:
                    NaturalNumber naturalNumber = new NaturalNumber(t.character);
                    set.addElement(naturalNumber.getLong());
                    t = tokenStream.skipAndRead();
                    if( t.kind == Token.SEPERATOR ) { break; }
                    if (t.kind == Token.END_SET ) { tokenStream.putBack(t.character); break; }
                case Token.NATURAL:
                    set.addElement(readNaturalNumber(t.character));
                    t = tokenStream.skipAndRead();
                    if( t.kind == Token.SEPERATOR ) { break; }
                    if (t.kind == Token.END_SET ) { tokenStream.putBack(t.character); break; }
                case Token.END_SET:
                    return set;

                default:
                    String excep = String.format("$s: unexpected character at set :: readSet()", count);
                    throw new APException(excep);
            }
        }
    }


    private Identifier readIdentifier(char firstChar) throws APException {
        Identifier identifier = new Identifier(firstChar);
        while( true ) {
            Token t = tokenStream.readAny();
            if( t.kind == Token.CHAR || t.kind == Token.NATURAL || t.kind == Token.ZERO ) {
                identifier.add(t.character);
            }else {
                tokenStream.putBack(t.character);
                return identifier;
            }
        }
    }

    private Long readNaturalNumber(char first) throws APException {
        NaturalNumber naturalNumber = new NaturalNumber(first);

        while(true) {
            Token t = tokenStream.readAny();
            if( t.kind != Token.NATURAL && t.kind != Token.ZERO ) {
                tokenStream.putBack(t.character);
                break;
            }
            naturalNumber.add(t.character);
        }
        return naturalNumber.getLong();
    }


    public void insertIntoHash(Identifier identifier, Set<Long> set) {
        controller.insert(identifier, set);
    }
    public void printSet(Set set) {
        controller.print(set, count);
    }
    public Set<Long> getSetFromIdentifier(Identifier identifier) throws APException {
        Set<Long> set = controller.getSet(identifier);
        if (set == null) {
            String message = String.format("%d: unknown identifier", count);
            throw new APException(message); }
        return set;
    }
}
