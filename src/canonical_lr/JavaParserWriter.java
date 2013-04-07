/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr;
;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import canonical_lr.tree.*;
import lr_runtime.*;
import rysto.util.*;

/**
 *
 * @author rstone
 */
public class JavaParserWriter implements ParserWriter {

    private final JavaLanguageSpec spec;
    private final ParseTable parseTable;
    private final Bijection<String, Integer> symbolNum;
    private final ProductionFactory prodFactory;
    private final Vocabulary v;
    private final String symbolName;
    private final String parserName;

    public JavaParserWriter(LanguageSpec spec, ParseTable parseTable, Bijection<String, Integer> symbolNum, ProductionFactory prodFactory, Vocabulary v, String symbolName, String parserName) {
        this.spec = (JavaLanguageSpec)spec;
        this.parseTable = parseTable;
        this.symbolNum = symbolNum;
        this.prodFactory = prodFactory;
        this.v = v;
        this.symbolName = symbolName;
        this.parserName = parserName;
    }

    public static final int ENCODED_STRING_LENGTH = 1 << 15;

    @Override
    public void outputSymbols(File symbolFile) throws IOException {
        Writer out = new BufferedWriter(new FileWriter(symbolFile));
        String classPackage = spec.packageName;

        if(classPackage != null) {
            out.write("package ");
            out.write(classPackage);
            out.write(";\n\n");
        }
        out.write("public class ");
        out.write(symbolName);
        out.write(" {\n");
        for(Map.Entry<String, Integer> entry : symbolNum.entrySet()) {
            if(!ProductionFactory.START_SYMBOL_NAME.equals(entry.getKey())) {
                out.write("    public static final int ");
                out.write(entry.getKey());
                out.write(" = ");
                out.write(Integer.toString(entry.getValue()));
                out.write(";\n");
            }
        }
        
        out.write("\n");
        out.write("public static String symToString(int sym) {\n");
        out.write("        switch(sym) {\n");
        for(String symbol : symbolNum.keySet()) {
            if(!ProductionFactory.START_SYMBOL_NAME.equals(symbol)) {
                out.write("            case ");
                out.write(symbol);
                out.write(": return \"");
                if(v.isNonTerminal(symbol)) {
                    out.write("<");
                }
                out.write(symbol);
                if(v.isNonTerminal(symbol)) {
                    out.write(">");
                }
                out.write("\";\n");
            }
        }
        out.write("        }\n");
        out.write("        throw new Error(\"Unrecognized symbol number \" + sym);\n");
        out.write("    }\n");
        out.write("}\n");

        out.close();
    }

    private static void encodeParseTable(Writer out, ParseTable parseTable) throws IOException {
        StringEncoder encode = new StringEncoder();
        ObjectOutputStream objectOut = new ObjectOutputStream(new GZIPOutputStream(encode));
        objectOut.writeObject(parseTable);
        objectOut.close();

        String encoded = encode.toString();
        int i;
        out.write("    private static final String [] ENCODED_TABLE = {\n");

        for(i = 0; (i + ENCODED_STRING_LENGTH) < encoded.length(); i += ENCODED_STRING_LENGTH) {
            out.write("        \"");
            out.write(encoded.substring(i, i + ENCODED_STRING_LENGTH));
            out.write("\", \n");
        }

        out.write("        \"");
        out.write(encoded.substring(i, encoded.length()));
        out.write("\"\n    };\n\n");
    }
    
    private static boolean isJavaBuiltin(String type) {
        return type.equals("boolean") || type.equals("int") || type.equals("long") ||
                type.equals("byte") || type.equals("float") || type.equals("double");
    }
    
    private static String getJavaWrapperClassName(String type) {
        if(type.equals("boolean")) {
            return "Boolean";
        } else if (type.equals("int")) {
            return "Integer";
        } else if (type.equals("long")) {
            return "Long";
        } else if (type.equals("byte")) {
            return "Byte";
        } else if (type.equals("float")) {
            return "Float";
        } else if (type.equals("double")) {
            return "Double";
        } else {
            throw new Error(type);
        }
    }

    @Override
    public void outputParser(File parserFile) throws IOException, ParserGeneratorException
    {
        String classPackage = spec.packageName;
        List<String> imports = spec.imports;

        Writer out = new BufferedWriter(new FileWriter(parserFile));

        if(classPackage != null) {
            out.write("package ");
            out.write(classPackage);
            out.write(";\n\n");
        }
        
        for(String im : imports) {
            out.write(im);
            out.write(";\n");
        }
        
        if(!imports.isEmpty()) {
            out.write("\n");
        }
        
        out.write("public abstract class ");
        out.write(parserName);
        out.write(" extends lr_runtime.LrParser {\n");
        encodeParseTable(out, parseTable);
        out.write("    public ");
        out.write(parserName);
        out.write("() {\n");
        out.write("        super(ENCODED_TABLE);\n");
        out.write("    }\n\n");

        out.write("    @Override\n");
        out.write("    protected int getProductionLength(int prodId) {\n");
        out.write("        switch(prodId) {\n");
        for(int i = 0; i < prodFactory.productions.size(); i++) {
            out.write("            case ");
            out.write(Integer.toString(i));
            out.write(": return ");
            out.write(Integer.toString(prodFactory.productions.get(i).rhs.size()));
            out.write(";\n");
        }
        out.write("        }\n\n");
        out.write("        throw new Error(\"Bad production id \" + prodId);\n");
        out.write("    }\n\n");

        out.write("    @Override\n");
        out.write("    protected int getLhsSymbol(int prodId) {\n");
        out.write("        switch(prodId) {\n");
        for(int i = 0; i < prodFactory.productions.size(); i++) {
            if(!ProductionFactory.START_SYMBOL_NAME.equals(prodFactory.productions.get(i).lhs)) {
                out.write("            case ");
                out.write(Integer.toString(i));
                out.write(": return ");
                out.write(symbolName);
                out.write(".");
                out.write(prodFactory.productions.get(i).lhs);
                out.write(";//");
                out.write(prodFactory.productions.get(i).toString());
                out.write("\n");
            }
        }
        out.write("        }\n\n");
        out.write("        throw new Error(\"Bad production id \" + prodId);\n");
        out.write("    }\n\n");

        out.write("    @Override\n");
        out.write("    protected int getStartState() {\n");
        out.write("        return 0;\n");
        out.write("    }\n\n");

        out.write("    @Override\n");
        out.write("    protected int getNumSymbols() {\n");
        out.write("        return ");
        out.write(Integer.toString(v.vocabulary().size()));
        out.write(";\n");
        out.write("    }\n\n");

        out.write("    @SuppressWarnings(\"unchecked\")\n");
        out.write("    @Override\n");
        out.write("    protected Object performAction(int prodId, java.util.List<lr_runtime.Token> rhs) {\n");
        out.write("        lr_runtime.Token first;\n");
        out.write("        switch(prodId) {\n");

        for(int i = 0; i < prodFactory.productions.size(); i++) {
            Production p = prodFactory.productions.get(i);

            if(p.name != null) {
                out.write("            case ");
                out.write(Integer.toString(i));
                out.write(":\n"); 
                out.write("                first = findFirst(rhs);\n");
                out.write("                return ");
                out.write(p.name);
                out.write("(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn()");

                int index = 0;
                for(String sym : p.rhs) {
                    String type = v.symbolType(sym);

                    if(type != null) {
                        type = spec.getType(type);
                        out.write(", ");
                        
                        out.write("(");
                        if(v.isTerminal(sym)) {
                            out.write("lr_runtime.Token<");
                        }
                        out.write(type);
                        if(v.isTerminal(sym)) {
                            out.write(">");
                        }
                        out.write(")");
                        
                        if(isJavaBuiltin(type)) {
                            out.write("(");
                            out.write(getJavaWrapperClassName(type));
                            out.write(")");
                        }
                        
                        out.write("rhs.get(");
                        out.write(Integer.toString(index));
                        out.write(")");
                        
                        if(v.isNonTerminal(sym)) {
                            out.write(".value");
                        }
                    }

                    index++;
                }

                out.write(");\n");
            }
        }

        out.write("        }\n");
        out.write("        return null;\n");
        out.write("    }\n\n");
        
        MultiMap<ReductionFunction, Production> funcs = 
                new MultiMap<ReductionFunction, Production>(LinkedHashMap.class);

        for(Production p : prodFactory.productions) {
            if(p.name != null) {
                String ret;
                String leftType = v.symbolType(p.lhs);

                if(leftType == null) {
                    ret = "Object";
                } else {
                    ret = spec.getType(leftType);
                }
                
                StringBuilder typeName = new StringBuilder();
                List<String> argTypes = new ArrayList<String>();
                for(String sym : p.rhs) {
                    String type = v.symbolType(sym);

                    if(type != null) {
                        type = spec.getType(type);
                        typeName.delete(0, typeName.length());

                        if(v.isTerminal(sym)) {
                            typeName.append("lr_runtime.Token<");
                        }
                        typeName.append(type);
                        
                        if(v.isTerminal(sym)) {
                            typeName.append(">");
                        }
                        
                        argTypes.add(typeName.toString());
                    }
                }
                
                funcs.put(new ReductionFunction(ret, p.name, argTypes), p);
            }
        }
             
        for(Map.Entry<ReductionFunction, Set<Production>> entry : funcs.entrySet()) {
            ReductionFunction f = entry.getKey();
            boolean singular = entry.getValue().size() == 1;
            out.write("    /** Method called when the production" + (singular ? "" : "s") + ":\n");
            for(Production p : entry.getValue()) {
                out.write("      *   ");
                out.write(p.toString());
                out.write("\n");
            }
            out.write("      * " + (singular ? "is" : "are") + " reduced.\n");
            out.write("      */\n");
            out.write("    protected abstract ");
            out.write(f.returnType);
            out.write(" ");
            out.write(f.name);
            out.write("(int line, int col");

            ListIterator<String> it = f.argTypes.listIterator();
            while(it.hasNext()) {
                out.write(", ");
                out.write(it.next());
                out.write(" arg");
                out.write(Integer.toString(it.previousIndex()));
            }

            out.write(");\n\n");
        }

        out.write("}\n");

        out.close();
    }
}
