/*
 * Item.java
 *
 * Created on January 23, 2008, 2:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;

/* Class representing an LR(1) item */
public class Item {
    /* The production */
    public final Production production;
    /* Where in the production the dot goes */
    public final int dotPosition;

    public final String lookahead;
    
    public Item(Production p, int dot, String l) {
        production = p;
        dotPosition = dot;
        lookahead = l;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("[");
        b.append(production.lhs);
        b.append(" --> ");
        
        int i;
        for(i = 0; i < production.rhs.size(); i++) {
            if(i == dotPosition) {
                b.append("* ");
            }
            
            b.append(production.rhs.get(i));
            b.append(" ");
        }
        
        if(i == dotPosition) {
            b.append("* ");
        }
        
        b.append(", ");
        b.append(lookahead);
        b.append("]");
        
        return b.toString();
    }

    // returns the sentential after the dot(except for the first symbol after the dot)
    // so in S --> A B * C D E, this would return D E
    // worst method name ever...
    public List<String> sententialAfterSymbolAfterDot() {
        if(dotPosition < production.rhs.size()) {
            return production.rhs.subList(dotPosition + 1, production.rhs.size());
        } else {
            return Collections.emptyList();
        }
    }
    
    // returns the symbol after the dot in the item, or null if this is a final item
    public String symbolAfterDot() {
        if(dotPosition < production.rhs.size()) {
            return production.rhs.get(dotPosition);
        } else {
            return null;
        }
    }
    
    public boolean isFinal() {
        return dotPosition == production.rhs.size();
    }
    
    public boolean isKernel() {
        return dotPosition != 0 || 
                production.lhs.equals(ProductionFactory.START_SYMBOL_NAME);
    }
}
