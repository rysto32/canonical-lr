/*
 * GotoFactory.java
 *
 * Created on January 24, 2008, 5:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;
import rysto.util.MultiMap;

/* Class that calculates(and memoizes) the LR(1) goto set of item sets */
public class GotoFactory {
    
    private final Map<ItemClosure, Map<String, ItemClosure>> gotoMap = new HashMap<ItemClosure, Map<String, ItemClosure>>();
    
    private final ClosureFactory closureFactory;
    private final ItemFactory itemFactory;

    private MultiMap<ItemClosure, ItemClosure> mergeMap;
    
    private boolean countComputes = true;
    private int computeCount = 0;
    
    public GotoFactory(ClosureFactory c, ItemFactory i) {
        closureFactory = c;
        itemFactory = i;
    }

    public int getComputeCount() {
        return computeCount;
    }

    public void setCountComputes(boolean countComputes) {
        this.countComputes = countComputes;
        
        closureFactory.setCountComputes(countComputes);
    }
    
    /* Calculate the goto set of i over symbol x.  Algorithm is from the Dragon Book */
   private ItemClosure computeGoto(ItemClosure i, String x) {
        ItemClosure set = new ItemClosure();
        Iterable<Item> dots = i.dotsPreceding(x);
        for(Item item : dots) {
            if(!x.equals(item.symbolAfterDot())) {
                throw new Error();
            }
            
            set.add(itemFactory.getItem(item.production, item.dotPosition + 1, item.lookahead));
        }
        
        if(countComputes) {
            computeCount++;
        }
        
        ItemClosure close = closureFactory.closure(set);
        gotoMap.get(i).put(x, close);
        return close;
    }

   private ItemClosure gotoFromCache(ItemClosure i, String x) {
        Map<String, ItemClosure> map = gotoMap.get(i);

        if(map == null) {
            map = new HashMap<String, ItemClosure>();
            gotoMap.put(i, map);
        }

        return map.get(x);
   }
    
   /* Return the goto set of i over symbol x*/
    public ItemClosure gotoSet(ItemClosure i, String x) {
        ItemClosure goTo = gotoFromCache(i, x);

        if(goTo != null) {
            return goTo;
        }

        if(mergeMap != null) {
            goTo = new ItemClosure();
            for(ItemClosure c : mergeMap.get(i)) {
                ItemClosure g = gotoFromCache(c, x);
                if(g == null) {
                    g = computeGoto(c, x);
                }

                goTo.addAll(g);
            }

            goTo = closureFactory.closure(goTo);
            gotoMap.get(i).put(x, goTo);

            return goTo;

        } else {
            return computeGoto(i, x);
        }
    }

    public void setMergeMap(MultiMap<ItemClosure, ItemClosure> mergeMap) {
        this.mergeMap = mergeMap;
        gotoMap.clear();
    }
}
