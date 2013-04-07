/*
 * ProductionNode.java
 *
 * Created on January 23, 2008, 11:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr.tree;

import java.util.*;

public class ProductionNode extends GrammarNode {
    
    public final String lhs;
    public final List<RightHandSide> right;
    
    public ProductionNode(String l, List<RightHandSide> r) {
        lhs = l;
        right = r;
    }

    public void accept(GrammarVisitor v) {
        v.visitProductionNode(this);
        
        for(RightHandSide n : right) {
            n.accept(v);
        }
    }
    
}
