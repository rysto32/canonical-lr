/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr;

import canonical_lr.tree.*;
import lr_runtime.*;
import rysto.util.*;

/**
 *
 * @author rstone
 */
public enum TargetLanguage {
    java {
        @Override
        public JavaParserWriter getParserWriter(LanguageSpec spec, ParseTable parseTable, Bijection<String, Integer> symbolNum, ProductionFactory prodFactory, Vocabulary v, String symbolName, String parserName) {
            return new JavaParserWriter(spec, parseTable, symbolNum, prodFactory, v, symbolName, parserName);
        }

        @Override
        public String getParserFileName(String name) {
            return name + ".java";
        }

        @Override
        public String getSymbolFileName(String name) {
            return name + ".java";
        }
    },
    
    cxx {
        @Override
        public ParserWriter getParserWriter(LanguageSpec spec, ParseTable parseTable, Bijection<String, Integer> symbolNum, ProductionFactory prodFactory, Vocabulary v, String symbolName, String parserName) {
            return new CxxParserWriter(spec, parseTable, symbolNum, prodFactory, v, symbolName, parserName);
        }

        @Override
        public String getParserFileName(String name) {
            return name + ".cpp";
        }

        @Override
        public String getSymbolFileName(String name) {
            return name + ".h";
        }
    };

    ;
    
    public abstract ParserWriter getParserWriter(LanguageSpec spec, ParseTable parseTable, Bijection<String, Integer> symbolNum, ProductionFactory prodFactory, Vocabulary v, String symbolName, String parserName);
    public abstract String getParserFileName(String name);
    public abstract String getSymbolFileName(String name);
}
