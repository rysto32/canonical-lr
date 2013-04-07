    /*
 * GrammarSpecification.java
 *
 * Created on January 23, 2008, 11:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr.tree;

import canonical_lr.TargetLanguage;
import java.util.*;

public class GrammarSpecification extends GrammarNode {
    
    public final Map<TargetLanguage, LanguageSpec> languageSpecs;
    public final List<SymbolDeclarationNode> symbolList;
    public final String startSpec;
    public final List<ProductionNode> productionList;
    
    public GrammarSpecification(Map<TargetLanguage, LanguageSpec> langSpecs,
            List<SymbolDeclarationNode> symList, 
            String start, List<ProductionNode> prodList) 
    {
        languageSpecs = langSpecs;
        symbolList = symList;
        startSpec = start;
        productionList = prodList;
    }

    public void accept(GrammarVisitor v) {
        v.visitGrammarSpecification(this);
        
        for(SymbolDeclarationNode n : symbolList) {
            n.accept(v);
        }
        
        for(ProductionNode n : productionList) {
            n.accept(v);
        }
    }
    
}
