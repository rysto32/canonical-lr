/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr;

import lr_runtime.*;

/**
 *
 * @author rstone
 */
public class SymbolFactory implements TokenFactory{

    public Token makeToken(int s, Object v, int l, int c) {
        return new Symbol(s, v, l);
    }

}
