/*
 * Vocabulary.java
 *
 * Created on January 23, 2008, 2:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;
import canonical_lr.tree.*;

public class Vocabulary {
    
    public final Set<String> terminals = new LinkedHashSet<String>();
    public final Set<String> nonTerminals = new LinkedHashSet<String>();
    private final Map<String, String> symbolType = new HashMap<String, String>();
    
    public Vocabulary(GrammarSpecification spec) throws ParserGeneratorException {
        findVocabulary(spec);
    }
    
    private void findVocabulary(GrammarSpecification spec) throws ParserGeneratorException{
        terminals.add(ProductionFactory.EOF_DELIMITER);
        
        for(SymbolDeclarationNode sym : spec.symbolList) {
            
            switch(sym.symbolType) {
                case NON_TERMINAL:
                    for(String symbol : sym.symbols) {
                        if(!nonTerminals.add(symbol) || terminals.contains(symbol)) {
                            throw new ParserGeneratorException("Symbol " + symbol + " declared twice");
                        }
                    }
                    break;
                    
                case TERMINAL:
                    for(String symbol : sym.symbols) {
                        if(!terminals.add(symbol) || nonTerminals.contains(symbol)) {
                            throw new ParserGeneratorException("Symbol " + symbol + " declared twice");
                        }
                    }
                    break;
                    
                default:
                    throw new Error("Unknown symbol type " + sym.symbolType);
            }
            
            for(String s : sym.symbols) {
                symbolType.put(s, sym.valueType);
            }
        }
        
        nonTerminals.add(ProductionFactory.START_SYMBOL_NAME);
    }
    
    public Set<String> vocabulary() {
        return symbolType.keySet();
    }
    
    public String symbolType(String sym) {
        return symbolType.get(sym);
    }
    
    public boolean isTerminal(String sym) {
        return terminals.contains(sym);
    }
    
    public boolean isNonTerminal(String sym) {
        return nonTerminals.contains(sym);
    }
}
