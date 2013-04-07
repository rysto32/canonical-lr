/*
 * ItemClosure.java
 *
 * Created on January 24, 2008, 5:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;

import canonical_lr.util.*;

/* Class representing a set of items, which is usally a LR(1) closure but not always */
public class ItemClosure implements Iterable<Item> {
    
    private final Set<Item> kernel = new MemoizedHashSet<Item>();
    
    /* We memoize all of the symbol found after dots in this set of items */
    private final Map<String, List<Item>> afterDot = new HashMap<String, List<Item>>();
    
    public ItemClosure() {
    }
    
    public ItemClosure(Iterable<? extends Item> i) {
        this();
        addAll(i);
    }
    
    private void addAfterDot(Item item) {
        String symbol = item.symbolAfterDot();
        
        if(symbol != null) {
            List<Item> list = afterDot.get(symbol);
            
            if(list == null) {
                list = new ArrayList<Item>();
                afterDot.put(symbol, list);
            }
            
            list.add(item);
        }
        
    }
    
    public boolean add(Item item) {
        if(kernel.add(item)) {
            addAfterDot(item);
            return true;
        } else {       
            return false;
        }
    }
    
    public boolean addAll(Iterable<? extends Item> items) {
        boolean changed = false;
        for(Item i : items) {
            changed |= add(i);
        }
        
        return changed;
    }
    
    public boolean contains(Item i) {
        return kernel.contains(i);
    }
    
    public Iterable<Item> dotsPreceding(String symbol) {
        List<Item> list = afterDot.get(symbol);
        
        if(list == null) {
            return Collections.emptyList();
        } else {
            return list;
        }
    }
    
    public Iterable<String> symbolsAfterDot() {
        return afterDot.keySet();
    }
    
    public boolean isEmpty() {
        return kernel.isEmpty();
    }
    
    @Override
    public int hashCode() {
        return kernel.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof ItemClosure)) {
            return false;
        }
        
        return kernel.equals(((ItemClosure)other).kernel);
    }

    @Override
    public Iterator<Item> iterator() {
        return kernel.iterator();
    }
    
    public Set<Item> kernelItems() {
        return kernel;
    }
    
    @Override
    public String toString() {
        return kernel.toString();
    }
}
