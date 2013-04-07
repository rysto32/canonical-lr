/*
 * Production.java
 *
 * Created on January 15, 2008, 10:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;

/* Represents a production from the grammar that we parsed */
public class Production {
    
    public final String lhs;
    public final List<String> rhs;
    public final int id;
    
    public final String name;
    
    public Production(String l, List<String> r, String n, int i) {
        lhs = l;
        rhs = r;
        name = n;
        id = i;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(lhs);
        b.append(" --> ");
        
        int i;
        for(i = 0; i < rhs.size(); i++) {
            b.append(rhs.get(i));
            b.append(" ");
        }
        
        return b.toString();
    }
}
