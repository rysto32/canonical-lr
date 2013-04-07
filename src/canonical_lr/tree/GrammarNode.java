/*
 * GrammarNode.java
 *
 * Created on January 23, 2008, 11:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr.tree;

import java.util.*;

public abstract class GrammarNode {
    
    public GrammarNode() {
    }
    
    public abstract void accept(GrammarVisitor v);
}
