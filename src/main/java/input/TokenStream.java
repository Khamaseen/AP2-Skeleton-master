package input;

import controller.APException;

import java.io.*;

public class TokenStream {
    private PushbackReader userInputReader;

    public TokenStream() {
        //File file = new File("C:\\Users\\d.gijzen\\Desktop\\Uni\\AP2-Skeleton-master\\src\\main\\java\\calc_in");
        File file = new File("C:\\Users\\d.gijzen\\Desktop\\Uni\\AP2-Skeleton-master\\src\\main\\java\\test2_in");
        //File file = new File("C:\\Users\\d.gijzen\\Desktop\\Uni\\AP2-Skeleton-master\\src\\main\\java\\ui\\endoffile");

        InputStream fis;
        try {
            fis = new FileInputStream(file);
            userInputReader = new PushbackReader(new InputStreamReader(fis));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //userInputReader = new PushbackReader(new InputStreamReader(System.in));
    }


    /**
     * close the inputStream
     */
    public void close() {
        try {
            userInputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * does skip whitespaces while returning a Token
     * @return
     * @throws APException
     */
    public Token skipAndRead() throws APException {
        int it;
        try {
            while( true ) {
                it = userInputReader.read();
                if( it == -1 || it == 0 ) {
                    return new Token();
                }
                if( !(11 < it && it < 15) && (it != 32)) {
                    return new Token((char) it);
                }
            }
        }catch (IOException e) {
            throw new APException(" stream reader " + e.getMessage());
        }
    }

    /**
     * does not skip whitespaces while returning a Token
     * @return
     * @throws APException
     */
    public Token readAny() throws APException{
        try {
            return new Token((char) userInputReader.read());
        }catch (IOException e) {
            throw new APException(e.getMessage());
        }
    }


    /**
     * puts a character back into the inputstream (this is actually buffered)
     * @param character
     * @throws APException
     */
    public void putBack(char character) throws APException {
        try {
            userInputReader.unread(character);
        }catch (IOException e) {
            throw new APException(e.getMessage());
        }
    }

    /**
     * reads to the end of a line
     * @throws APException
     */
    public void skipLine() throws APException {
        int it;
        try {
            while( true ) {
                it = userInputReader.read();
                char b = (char) it;
                if ( it == 12 || 15 == it || it == 10 ) { break; }
            }
        }catch (IOException e) {
            throw new APException(e.getMessage());
        }
    }

}
