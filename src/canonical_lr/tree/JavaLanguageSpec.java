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
public class JavaLanguageSpec extends LanguageSpec {
     public final String packageName;
     public final List<String> imports;

    public JavaLanguageSpec(String packageName, List<String> imports, Map<String, String> typedefs) {
        super(typedefs);
        this.packageName = packageName;
        this.imports = imports;
    }

    @Override
    public TargetLanguage getTargetLanguage() {
        return TargetLanguage.java;
    }

    @Override
    public void writeHeader(Writer out) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
