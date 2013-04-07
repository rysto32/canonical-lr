/*
 * RightHandSide.java
 *
 * Created on January 23, 2008, 11:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr.tree;

import java.util.*;

public class RightHandSide extends GrammarNode {
    
    public final List<String> symbols;
    public final String productionName;
    
    public RightHandSide(List<String> syms, String name) {
        symbols = syms;
        productionName = name;
    }

    public void accept(GrammarVisitor v) {
        v.visitRightHandSide(this);
    }
    
}
