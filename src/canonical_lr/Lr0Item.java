/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr;

/**
 *
 * @author rstone
 */
public class Lr0Item {
    /* The production */
    public final Production production;
    /* Where in the production the dot goes */
    public final int dotPosition;

    public Lr0Item(Item i) {
        this.production = i.production;
        this.dotPosition = i.dotPosition;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Lr0Item other = (Lr0Item) obj;
        if (this.production != other.production && (this.production == null || !this.production.equals(other.production))) {
            return false;
        }
        if (this.dotPosition != other.dotPosition) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.production != null ? this.production.hashCode() : 0);
        hash = 17 * hash + this.dotPosition;
        return hash;
    }

    
}
