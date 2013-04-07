/*
 * MemoizedHashSet.java
 *
 * Created on January 24, 2008, 5:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr.util;

import java.util.*;

// A hash set that memoizes its own hash value
public class MemoizedHashSet<E> extends HashSet<E> {
    
    private static final long serialVersionUID = -8973331468702066672L;
    
    private boolean hashValid = false;
    private int hash;
    
    public MemoizedHashSet() {
        super();
    }

    public MemoizedHashSet(Collection<? extends E> c) {
        super(c);
    }

    public MemoizedHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public MemoizedHashSet(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public Iterator<E> iterator() {
        final Iterator<E> it = super.iterator();
        return new Iterator<E>() {
            @Override
            public void remove() {
                hashValid = false;
                it.remove();
            }

            @Override
            public E next() {
                return it.next();
            }

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }
            
        };
    }

    @Override
    public void clear() {
        hashValid = false;
        super.clear();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        hashValid = false;
        
        return super.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        hashValid = false;
        
        return super.removeAll(c);
    }

    @Override
    public boolean add(E e) {
        hashValid = false;
        
        return super.add(e);
    }

    @Override
    public boolean remove(Object o) {
        hashValid = false;
        
        return super.remove(o);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        hashValid = false;
        
        return super.addAll(c);
    }

    @Override
    public int hashCode() {
        if(hashValid) {
            return hash;
        } else {
            hash = super.hashCode();
            hashValid = true;
            
            return hash;
        }
    }
    
    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
