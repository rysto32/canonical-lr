/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;

/**
 *
 * @author rstone
 */
public class ReductionFunction {
    public final String returnType;
    public final String name;
    public final List<String> argTypes;

    public ReductionFunction(String returnType, String name, List<String> argTypes) {
        this.returnType = returnType;
        this.name = name;
        this.argTypes = argTypes;
    }

    @Override
    public String toString() {
        return returnType + " " + name + argTypes;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }

        if(!getClass().equals(o.getClass())) {
            return false;
        }

        ReductionFunction other = (ReductionFunction)o;

        if(!returnType.equals(other.returnType)) {
            return false;
        }

        if(!name.equals(other.name)) {
            return false;
        }

        return argTypes.equals(other.argTypes);
    }

    @Override
    public int hashCode() {
        return returnType.hashCode() + 7 * name.hashCode() + 31 * argTypes.hashCode();
    }
}
