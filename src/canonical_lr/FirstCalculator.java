/*
 * FirstCalculator.java
 *
 * Created on January 23, 2008, 2:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;

/* Calculates and memoizes the first sets of every symbol in the vocabulary */
public class FirstCalculator {

    private final Map<String, Set<String>> firstSets = new HashMap<String, Set<String>>();
    private final ProductionFactory factory;

    public FirstCalculator(Vocabulary v, ProductionFactory f) throws ParserGeneratorException {
        factory = f;
        calculateFirstSets(v);
    }

    /* Calculate the first set of the sentiential form.  If the entire sentential
     * is nullable then extra is added to the first set.
     */
    public Iterable<String> getFirstSet(Iterable<String> sentential, String extra) {
        Set<String> set = new HashSet<String>();
        
        boolean nullable = true;

        for(String symbol : sentential) {
            set.addAll(firstSets.get(symbol));

            if(!factory.isNullable(symbol)) {
                nullable = false;
                break;
            }
        }
        
        if(nullable) {
            set.add(extra);
        }

        return set;
    }

    /* Get the (pre-computed) first set of the given symbol */
    public Set<String> getFirstSet(String symbol) {
        return firstSets.get(symbol);
    }

    /* Calculates the first sets of every symbol  */
    private void calculateFirstSets(Vocabulary v) throws ParserGeneratorException {
        for(String symbol : v.nonTerminals) {
            firstSets.put(symbol, new HashSet<String>());
        }

        for(String terminal : v.terminals) {
            firstSets.put(terminal, Collections.singleton(terminal));
        }

        /* This algorithm sucks but it really doesn't matter.  We just keep going
         * until we do an iteration that doesn't change any first sets.
         * 
         * Algorithm is taken from the Dragon Book(I think)
         */
        boolean changed;

        do {
            changed = false;

            for(Production p : factory.productions) {

                for(String symbol : p.rhs) {

                    if(!v.vocabulary().contains(symbol)) {
                        throw new ParserGeneratorException("Undeclared symbol " + symbol);
                    }

                    changed |= firstSets.get(p.lhs).addAll(firstSets.get(symbol));

                    if(!factory.isNullable(symbol)) {
                        break;
                    }
                }
            }

        } while(changed);
    }

    @Override
    public String toString() {
        return firstSets.toString();
    }
}
