/*
 * ClosureFactory.java
 *
 * Created on January 24, 2008, 5:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;

/* Computes the LR(1) closure of a given set of items.  Memoizes the result of
 * each closure operation for performance.
 *
 */
public class ClosureFactory {
    
    private final Map<ItemClosure, ItemClosure> closureMap = new HashMap<ItemClosure, ItemClosure>();
    private final ProductionFactory prodFactory;
    private final ItemFactory itemFactory;
    private final FirstCalculator first;

    private Map<ItemClosure, ItemClosure> mergeMap;
    
    private boolean countComputes = true;
    private int computeCount = 0;
    
    public ClosureFactory(ProductionFactory p, ItemFactory i, FirstCalculator f) {
        prodFactory = p;
        itemFactory = i;
        first = f;
    }

    public int getComputeCount() {
        return computeCount;
    }

    public void setCountComputes(boolean countComputes) {
        this.countComputes = countComputes;
    }
    
    private void tryItem(ItemClosure set, Queue<Item> queue, Item newItem) {
        if(!set.contains(newItem)) {
            queue.add(newItem);
            set.add(newItem);
        }
    }
    
    /* Do the actual work of computing the closure.  Algorithm is taken from the
     * Dragon Book.
     */
    public ItemClosure computeClosure(ItemClosure i) {
        ItemClosure set = new ItemClosure(i);
        Queue<Item> queue = new LinkedList<Item>();
        
        for(Item item : i) {
            queue.add(item);
        }
        
        while(!queue.isEmpty()) {
            Item item = queue.remove();
            
            List<String> sentential = item.sententialAfterSymbolAfterDot();
            for(Production p : prodFactory.productionsFrom(item.symbolAfterDot())) {
                for(String terminal : first.getFirstSet(sentential, item.lookahead)) {
                    Item newItem = itemFactory.getItem(p, 0, terminal);
                    tryItem(set, queue, newItem);
                    
                }
            }
           
        }
        
        if(countComputes) {
            computeCount++;
        }

        if(mergeMap != null) {
            if(mergeMap.containsKey(set)) {
                set = mergeMap.get(set);
            } else {
                //System.err.println("mergeMap does not contain " + set);
            }
        }
        
        closureMap.put(i, set);
        
        return set;
    }
    
    /* Gets the closure of the given item set. */
    public ItemClosure closure(ItemClosure i) {
        ItemClosure close = closureMap.get(i);
        
        if(close != null) {
            return close;
        }
        
        return computeClosure(i);
    }

    public void setMergeMap(Map<ItemClosure, ItemClosure> map) {
        for(Map.Entry<ItemClosure, ItemClosure> entry : closureMap.entrySet()) {
            ItemClosure i = map.get(entry.getValue());

            if(i != null) {
                entry.setValue(i);
            }
        }

        this.mergeMap = map;
    }
}
