/*
 * ItemFactory.java
 *
 * Created on January 23, 2008, 2:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;

/* Factory class for constructing items on demand.  Each item is a singleton */
public class ItemFactory {
    
    private final Map<Production, Map<Integer, Map<String, Item>>> items = new HashMap<Production, Map<Integer, Map<String, Item>>>();
    public final Item acceptItem;
    
    public ItemFactory(ProductionFactory prodFactory) {
        acceptItem = getItem(prodFactory.startProduction, prodFactory.startProduction.rhs.size(), ProductionFactory.EOF_DELIMITER);
    }
    
    
    public Item getItem(Production p, int dotPosition, String lookahead) {
        //we lazily construct items on the assumption that most will never be used
        
        Map<Integer, Map<String, Item>> dotMap = items.get(p);
        
        if(dotMap == null) {
            dotMap = new HashMap<Integer, Map<String, Item>>();
            items.put(p, dotMap);
        }
        
        Map<String, Item> lookaheadMap = dotMap.get(dotPosition);
        
        if(lookaheadMap == null) {
            lookaheadMap = new HashMap<String, Item>();
            dotMap.put(dotPosition, lookaheadMap);
        }
        
        Item item = lookaheadMap.get(lookahead);
        
        if(item == null) {
            item = new Item(p, dotPosition, lookahead);
            lookaheadMap.put(lookahead, item);
        }
        
        return item;
    }
}
