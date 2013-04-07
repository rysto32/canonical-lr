/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr.tree;

import canonical_lr.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author rstone
 */
public abstract class LanguageSpec {
    public final Map<String, String> typedefs;

    public LanguageSpec(Map<String, String> typedefs) {
        this.typedefs = typedefs;
    }

    public abstract void writeHeader(Writer out) throws IOException;
    public abstract TargetLanguage getTargetLanguage();

    public String getType(String name) throws ParserGeneratorException {
        String type = typedefs.get(name);

        if(type == null)
            throw new ParserGeneratorException(name + " does not name a type");

        return type;
    }
}
