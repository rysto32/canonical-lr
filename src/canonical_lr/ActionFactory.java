/*
 * ActionFactory.java
 *
 * Created on January 23, 2008, 3:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;
import lr_runtime.*;  

/* Factory class that creates actions on-demand.  Actions are singletons: only
 * one action for each production/state is created.
 */
public class ActionFactory {
    
    private final Map<Integer, Shift> shift = new HashMap<Integer, Shift>();
    private final Map<Integer, Reduce> reduce = new HashMap<Integer, Reduce>();
    
    public ActionFactory() {
    }
    
    public Shift makeShift(Integer state) {
        Shift s = shift.get(state);
        
        if(s == null) {
            s = new Shift(state);
            shift.put(state, s);
        }
        
        return s;
    }
    
    public Reduce makeReduce(int productionId) {
        Reduce r = reduce.get(productionId);
        
        if(r == null) {
            r = new Reduce(productionId);
            reduce.put(productionId, r);
        }
        
        return r;
    }
    
    public Accept makeAccept() {
        return Accept.INSTANCE;
    }
}
