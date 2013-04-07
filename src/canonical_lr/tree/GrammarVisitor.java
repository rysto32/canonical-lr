/*
 * GrammarVisitor.java
 *
 * Created on January 24, 2008, 12:09 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr.tree;

/**
 *
 * @author rstone
 */
public interface GrammarVisitor {
    public void visitGrammarSpecification(GrammarSpecification n);
    public void visitProductionNode(ProductionNode n);
    public void visitRightHandSide(RightHandSide n);
    public void visitSymbolDeclarationNode(SymbolDeclarationNode n);
}
