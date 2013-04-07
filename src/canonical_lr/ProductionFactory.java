/*
 * ProductionFactory.java
 *
 * Created on January 15, 2008, 10:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;

import canonical_lr.tree.*;

/* Class that constructs productions, and calculates some other properties of the grammar  */
public class ProductionFactory {
    /* these is intentionally made the empty string -- we need a string here that
     * is guaranteed not to be used in the grammar as the name for any symbol
     * The empty string qualifies.
     */
    public static final String START_SYMBOL_NAME = "";
    public static final String EOF_DELIMITER = "PARSER_EOF";
    
    public final ArrayList<Production> productions = new ArrayList<Production>();
    private final Map<String, List<Production>> prodMap = new HashMap<String, List<Production>>();
    public final Set<String> nullable = new HashSet<String>();
    private final Vocabulary v;
    
    public final Production startProduction;
    
    public ProductionFactory(GrammarSpecification spec, Vocabulary vocab) throws ParserGeneratorException {
        v = vocab;
        startProduction = constructProductions(spec);
        findNullable();
    }
    
    public boolean isNullable(String left) {
        return nullable.contains(left);
    }
    
    public List<Production> productionsFrom(String lhs) {
        List<Production> list = prodMap.get(lhs);
        
        if(list == null) {
            return Collections.emptyList();
        } else {
            return list;
        }
    }
    
    private boolean calcNullable(Production p) {
        for(String symbol : p.rhs) {
            if(!isNullable(symbol)) {
                return false;
            }
        }
        
        return true;
    }
    
    private Production constructProductions(GrammarSpecification spec) throws ParserGeneratorException {        
        Set<String> undeclared = new HashSet<String>();
        for(ProductionNode prod : spec.productionList) {
            
            List<Production> prodList = new ArrayList<Production>(prod.right.size());
            prodMap.put(prod.lhs, Collections.unmodifiableList(prodList));
            
            if(!v.vocabulary().contains(prod.lhs)) {
                if(undeclared.add(prod.lhs)) {
                    System.err.println("Symbol " + prod.lhs + " not declared");
                }
            }
            
            for(RightHandSide rule : prod.right) {
                
                for(String sym : rule.symbols) {
                    if(!v.vocabulary().contains(sym)) {
                        if(undeclared.add(sym)) {
                            System.err.println("Symbol " + sym + " not declared");
                        }
                    }
                }
                
                List<String> rhs = Collections.unmodifiableList(rule.symbols);
                Production p = new Production(prod.lhs, rhs, rule.productionName, productions.size());
                prodList.add(p);
                productions.add(p);
            }
        }
        
        if(!undeclared.isEmpty()) {
            throw new ParserGeneratorException("Undeclared symbols");
        }
        
        Production start = new Production(START_SYMBOL_NAME, Arrays.asList(spec.startSpec), null, productions.size());
        prodMap.put(START_SYMBOL_NAME, Collections.singletonList(start));
        productions.add(start);
        
        productions.trimToSize();
        
        Set<String> noProduction = new HashSet<String>(v.nonTerminals);
        noProduction.removeAll(prodMap.keySet());
        
        for(String sym : noProduction) {
            System.err.println("Warning: No productions from non terminal " + sym);
        }
        
        return start;
    }
    
    private void findNullable() {
        boolean changed;     
        do {
            changed = false;
            
            for(Production p : productions) {
                if(!isNullable(p.lhs) && calcNullable(p)) {
                    nullable.add(p.lhs);
                    changed = true;
                }
            }
            
        } while(changed);
    }
}
