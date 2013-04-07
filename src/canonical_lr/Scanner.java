/*
 * Scanner.java
 *
 * Created on January 23, 2008, 10:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;
import java.io.*;

import lr_runtime.*;

import static canonical_lr.Sym.*;
import static canonical_lr.Scanner.State.*;
import static canonical_lr.Scanner.State.COLON_COLON;

public class Scanner implements lr_runtime.Scanner {

    private static final int BUFFER_SIZE = 128;

    private static final Map<String, Integer> keywords;

    static {
        keywords = new HashMap<String, Integer>();
        keywords.put("package", PACKAGE);
        keywords.put("with", WITH);
        keywords.put("terminal", TERMINAL);
        keywords.put("non", NON);
        keywords.put("start", START);
        keywords.put("extends", EXTENDS);
        keywords.put("super", SUPER);
        keywords.put("import", IMPORT);
        keywords.put("static", STATIC);
        keywords.put("language", LANG);
        keywords.put("java", JAVA);
        keywords.put("cxx", CXX);
        keywords.put("type", TYPE);
        keywords.put("EOF", END_OF_FILE);

    }

    enum State {
        BEGIN, IDENTIFIER, COLON_STATE, COLON_COLON, SLASH, LINE_COMMENT, 
        MULTILINE_COMMENT, MULTILINE_COMMENT_STAR, CPP
    }

    private final Reader in;
    private final char[] buffer = new char[BUFFER_SIZE];

    StringBuilder identifier = new StringBuilder();

    private State state;

    private int bufferPosition = 0;
    private int bufferEnd = 0; 
    
    private int line = 1;

    public Scanner(Reader r) {
        in = r;
        state = BEGIN;
    }

    private Symbol symbol(int sym) {
        if(sym == ID) {
            throw new Error();
        }

        return symbol(sym, null);
    }

    private Symbol symbol(int sym, Object v) {
        if(sym == ID) {
            if(keywords.containsKey(v)) {
                sym = keywords.get(v);
            }
        }

        Symbol t = new Symbol(sym, v, line);

        //System.err.println(t);

        return t;
    }

    @Override
    public Symbol nextSymbol() throws ScannerException {
        while(true) {
            if(bufferPosition == bufferEnd) {
                bufferPosition = 0;


                try {
                    bufferEnd = in.read(buffer);
                } catch(IOException e) {
                    throw new Error(e);
                }
            }

            if(bufferEnd == -1) {
                return symbol(EOF);
            }

            switch(state) {
                case BEGIN:
                {
                    if(Character.isWhitespace(buffer[bufferPosition])) {
                        if(buffer[bufferPosition] == '\n') {
                            line++;
                        }

                    } else if(Character.isLetter(buffer[bufferPosition]) || buffer[bufferPosition] == '_') {
                        state = IDENTIFIER;
                        identifier.delete(0, identifier.length());
                        identifier.append(buffer[bufferPosition]);
                    } else if(buffer[bufferPosition] == '#') {
                        state = CPP;
                        identifier.delete(0, identifier.length());
                        identifier.append(buffer[bufferPosition]);
                    } else {
                        switch(buffer[bufferPosition]) {
                            case ':':
                                state = COLON_STATE;
                                break;

                            case '?':
                                bufferPosition++;
                                return symbol(QUESTION);

                            case ',':
                                bufferPosition++;
                                return symbol(COMMA);

                            case '|':
                                bufferPosition++;
                                return symbol(BAR);

                            case '[':
                                bufferPosition++;
                                return symbol(OPEN_S);

                            case ']':
                                bufferPosition++;
                                return symbol(CLOSE_S);

                            case '<':
                                bufferPosition++;
                                return symbol(OPEN_A);

                            case '>':
                                bufferPosition++;
                                return symbol(CLOSE_A);

                            case ';':
                                bufferPosition++;
                                return symbol(SEMI);

                            case '/':
                                state = SLASH;
                                break;

                            case '.':
                                bufferPosition++;
                                return symbol(DOT);

                            case '*':
                                bufferPosition++;
                                return symbol(STAR);

                            case '{':
                                bufferPosition++;
                                return symbol(OPEN_BRACE);

                            case '}':
                                bufferPosition++;
                                return symbol(CLOSE_BRACE);

                            default:
                                System.err.println("Illegal character " + buffer[bufferPosition] + " at line " + line);
                                bufferPosition++;
                                throw new ScannerException();
                        }
                    }

                    bufferPosition++;
                    break;
                }

                case IDENTIFIER:
                {
                    if(Character.isLetterOrDigit(buffer[bufferPosition]) || buffer[bufferPosition] == '_') {
                        identifier.append(buffer[bufferPosition]);
                        bufferPosition++;
                    } else {
                        state = BEGIN;
                        return symbol(ID, identifier.toString());
                    }

                    break;
                }

                case COLON_STATE:
                {
                    if(buffer[bufferPosition] == ':') {
                        bufferPosition++;
                        state = COLON_COLON;
                    } else {
                        state = BEGIN;
                        bufferPosition++;
                        return symbol(COLON);
                    }

                    break;
                }

                case COLON_COLON:
                {
                    if(buffer[bufferPosition] != '=') {
                        state = BEGIN;
                        return symbol(Sym.COLON_COLON);
                    } else {
                        state = BEGIN;
                        bufferPosition++;

                        return symbol(PRODUCES);
                    }
                }

                case SLASH:
                {
                    switch(buffer[bufferPosition]) {
                        case '/':
                            state = LINE_COMMENT;
                            break;

                        case '*':
                            state = MULTILINE_COMMENT;
                            break;

                        default:
                            bufferPosition++;
                            state = BEGIN;
                            System.err.println("Illegal character '/'");
                            throw new ScannerException();
                    }

                    bufferPosition++;

                    break;
                }

                case LINE_COMMENT:
                {
                    if(buffer[bufferPosition] == '\n') {
                        state = BEGIN;
                        line++;
                    } 

                    bufferPosition++;

                    break;
                }

                case MULTILINE_COMMENT:
                {
                    //System.err.println("Skip '" + buffer[bufferPosition] + "'");
                    if(buffer[bufferPosition] == '*') {
                        state = MULTILINE_COMMENT_STAR;
                    } else if(buffer[bufferPosition] == '\n') {
                        line++;
                    } 

                    bufferPosition++;

                    break;
                }

                case MULTILINE_COMMENT_STAR:
                {
                    //System.err.println("Skip '" + buffer[bufferPosition] + "'");
                    if(buffer[bufferPosition] == '/') {
                        state = BEGIN;
                    } else if(buffer[bufferPosition] != '*') {
                        if(buffer[bufferPosition] == '\n') {
                            line++;
                        } 
                        
                        state = MULTILINE_COMMENT;
                    }

                    bufferPosition++;

                    break;
                }
                case CPP:
                {
                    if(buffer[bufferPosition] == '\n') {
                        state = BEGIN;
                        return symbol(CPP_DIRECTIVE, identifier.toString());

                    } else {
                        identifier.append(buffer[bufferPosition]);
                    }

                    bufferPosition++;
                    break;
                }
            }
        }
    }

}
