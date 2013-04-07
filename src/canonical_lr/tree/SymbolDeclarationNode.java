/*
 * SymbolDeclarationNode.java
 *
 * Created on January 23, 2008, 11:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr.tree;

import java.util.*;

public class SymbolDeclarationNode extends GrammarNode {
    
    public final SymbolType symbolType;
    public final String valueType;
    public final List<String> symbols;
    
    public SymbolDeclarationNode(SymbolType symType, String valType, List<String> syms) {
        symbolType = symType;
        valueType = valType;
        symbols = syms;
    }

    public void accept(GrammarVisitor v) {
        v.visitSymbolDeclarationNode(this);
    }
    
}
