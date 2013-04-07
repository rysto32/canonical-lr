/*
 * Symbol.java
 *
 * Created on January 24, 2008, 12:58 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;
import lr_runtime.Token;

import static canonical_lr.Sym.*;

@SuppressWarnings("unchecked")
public class Symbol extends Token<Object> {
    
    public Symbol(int sym, int line) {
        super(sym, null, line, -1);
    }
    
    public Symbol(int sym, Object v, int line) {
        super(sym, v, line, -1);
    }
    
    @Override
    public String toString() {
        String s = symbolString(sym);
        
        if(sym == ID) {
            return s + "(" + value + ")";
        } else {
            return s;
        }
    }
    
    public static String symbolString(int sym) {
        
        switch(sym) {
            case PACKAGE: return "package";
            case COLON: return ":";
            case OPEN_S: return "[";
            case CLOSE_A: return ">";
            case PRODUCES: return "::=";
            case QUESTION: return "?";
            case NON: return "non";
            case COMMA: return ",";
            case TERMINAL: return "terminal";
            case CLOSE_S: return "]";
            case START: return "start";
            case OPEN_A: return "<";
            case SUPER: return "super";
            case ID: return "ID";
            case DOT: return ".";
            case EOF: return "EOF";
            case EXTENDS: return "extends";
            case SEMI: return ";";
            case BAR: return "|";
            case WITH: return "with";
            case IMPORT: return "import";
            case STATIC: return "static";
            case STAR: return "*";
            case OPEN_BRACE: return "{";
            case CLOSE_BRACE: return "}";
            case END_OF_FILE: return "EOF";
            
            default:
                return Sym.symToString(sym);
        }
    }
}
