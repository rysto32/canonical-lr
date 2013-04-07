/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr.tree;

import canonical_lr.TargetLanguage;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 *
 * @author rstone
 */
public class CxxLanguageSpec extends LanguageSpec {
    public final List<String> cppDirectives;

    public CxxLanguageSpec(List<String> cppDirectives, Map<String, String> typedefs) {
        super(typedefs);
        this.cppDirectives = cppDirectives;
    }

    @Override
    public TargetLanguage getTargetLanguage() {
        return TargetLanguage.cxx;
    }

    @Override
    public void writeHeader(Writer out) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
