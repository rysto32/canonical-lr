/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr;

import canonical_lr.tree.*;
import java.io.*;
import java.util.*;
import lr_runtime.*;
import rysto.util.*;

/**
 *
 * @author rstone
 */
public class CxxParserWriter implements ParserWriter {

    private final CxxLanguageSpec spec;
    private final ParseTable parseTable;
    private final Bijection<String, Integer> symbolNum;
    private final ProductionFactory prodFactory;
    private final Vocabulary v;
    private final String symbolName;
    private final String parserName;

    public CxxParserWriter(LanguageSpec spec, ParseTable parseTable, Bijection<String, Integer> symbolNum, ProductionFactory prodFactory, Vocabulary v, String symbolName, String parserName) {
        this.spec = (CxxLanguageSpec)spec;
        this.parseTable = parseTable;
        this.symbolNum = symbolNum;
        this.prodFactory = prodFactory;
        this.v = v;
        this.symbolName = symbolName;
        this.parserName = parserName;
    }

    private class WriterIOException extends RuntimeException {
        public final IOException ioexception;

        public WriterIOException(IOException cause) {
            super(cause);

            ioexception = cause;
        }

    }

    private class WriterGeneratorException extends RuntimeException {
        public final ParserGeneratorException generatorException;

        public WriterGeneratorException(ParserGeneratorException cause) {
            super(cause);

            generatorException = cause;
        }

    }

    private class CxxActionWriter implements ActionVisitor<Object, String> {
        private final Writer out;
        private final ProductionFactory prodFactory;
        private final Bijection<String, Integer> symbolNum;
        private boolean needHandleReturn = false;

        public CxxActionWriter(Writer out, ProductionFactory prodFactory, Bijection<String, Integer> symbolNum) {
            this.out = out;
            this.prodFactory = prodFactory;
            this.symbolNum = symbolNum;
        }

        @Override
        public Object visitAccept(Accept a, String stateNum) {
            try {
                out.write("        action = ");
                out.write(parserName);
                out.write("_ACCEPT;\n");
                out.write("        return 0;\n");
            } catch(IOException e) {
                throw new WriterIOException(e);
            }

            return null;
        }

        @Override
        public Object visitReduce(Reduce r, String stateNum) {
            try {
                Production production = prodFactory.productions.get(r.productionId);
                int numPops = production.rhs.size();

                out.write("    {\n");
                /*out.write("        fprintf(stderr, \"state=");
                out.write(stateNum);
                out.write(" lookahead=%s reduce ");
                out.write(Integer.toString(r.productionId));
                out.write("\\n\", symbolToString(lookahead->getSymbol()));\n");*/

                ListIterator<String> it = production.rhs.listIterator(numPops);
                while(it.hasPrevious()) {
                    it.previous();
                    out.write("        Token * arg");
                    out.write(Integer.toString(it.nextIndex()));
                    out.write(" = stack.pop();\n");
                }
                String lhsGrammarType = null;
                if(production.name != null) {
                    out.write("        ");
                    
                    lhsGrammarType = v.symbolType(production.lhs);
                    if(lhsGrammarType != null) {
                        out.write(spec.getType(lhsGrammarType));
                        out.write(" ");
                        out.write("lhs = ");
                    }

                    out.write(production.name);
                    out.write("(");

                    boolean needComma = false;
                    while(it.hasNext()) {
                        String symName = it.next();
                        String grammarType = v.symbolType(symName);

                        if(grammarType != null) {
                            if(needComma) {
                                out.write(", ");
                            }
                            out.write("arg");
                            out.write(Integer.toString(it.previousIndex()));
                            needComma = true;
                        }
                    }

                    out.write(");\n\n");
                }

                if(lhsGrammarType != null) {
                    out.write("        t = Token::make");
                    out.write(lhsGrammarType);
                    out.write("(");
                    out.write(production.lhs);
                    out.write(", lhs);\n");
                } else {
                    out.write("        t = new Token(");
                    out.write(production.lhs);
                    out.write(");\n");
                }
                out.write("        stack.push(t);\n");
                it = production.rhs.listIterator();
                while(it.hasNext()) {
                    it.next();
                    out.write("        delete arg");
                    out.write(Integer.toString(it.previousIndex()));
                    out.write(";\n");
                }

                if(numPops == 0) {
                    out.write("        backtrack = 0;\n");
                    out.write("        goto handleReturn;\n");
                    needHandleReturn = true;
                } else {
                    out.write("        return ");
                    out.write(Integer.toString(numPops-1));
                    out.write(";\n");
                }
                out.write("    }\n");
            } catch(IOException e) {
                throw new WriterIOException(e);
            } catch(ParserGeneratorException e) {
                throw new WriterGeneratorException(e);
            }

            return null;
        }

        @Override
        public Object visitReject(Reject r, String stateNum) {
            try {
                out.write("        action = ");
                out.write(parserName);
                out.write("_REJECT;\n");
                out.write("        return 0;\n");
            } catch(IOException e) {
                throw new WriterIOException(e);
            }

            return null;
        }

        @Override
        public Object visitShift(Shift s, String stateNum) {
            try {
                /*out.write("        std::cerr << \"state=");
                out.write(stateNum);
                out.write(" lookahead=\" << symbolToString(lookahead->getSymbol()) << \" shift ");
                out.write(Integer.toString(s.dest));
                out.write("\" << std::endl;\n");*/
                /*out.write("        fprintf(stderr, \"state=");
                out.write(stateNum);
                out.write(" lookahead=%s shift ");
                out.write(Integer.toString(s.dest));
                out.write("\\n\", symbolToString(lookahead->getSymbol()));\n");*/
                out.write("        shift();\n");
                out.write("        backtrack = state_");
                out.write(Integer.toString(s.dest));
                out.write("();\n");
                out.write("        goto handleReturn;\n");
                needHandleReturn = true;
            } catch(IOException e) {
                throw new WriterIOException(e);
            }

            return null;
        }
    }

    @Override
    public void outputParser(File parserFile) throws IOException, ParserGeneratorException {
        Writer out = new BufferedWriter(new FileWriter(parserFile));
        try {
//            out.write("/*\n");
//            out.write(parseTable.toString(symbolNum.inverseMap()));
//            ListIterator<Production> lt = prodFactory.productions.listIterator();
//            while(lt.hasNext()) {
//                Production p = lt.next();
//                out.write(Integer.toString(lt.previousIndex()));
//                out.write(": ");
//                out.write(p.toString());
//                out.write("\n");
//            }
//            out.write("*/\n\n");
            out.write("#include \"" + spec.getTargetLanguage().getSymbolFileName(symbolName) + "\"\n");
            out.write("#include <iostream>\n");
            out.write("#include <vector>\n\n");
            out.write("bool ");
            out.write(parserName);
            out.write("::accept(Scanner * s)\n");
            out.write("{\n");
            out.write("    ParserCleanup cleanup(*this);\n");
            out.write("    scanner = s;\n");
            out.write("    action = ");
            out.write(parserName);
            out.write("_CONTINUE;\n");
            out.write("    lookahead = scanner->getToken();\n\n");
            out.write("    state_0();\n");
            out.write("    return action == ");
            out.write(parserName);
            out.write("_ACCEPT;\n");
            out.write("}\n\n");

            ListIterator<Map<Integer, Action>> it = parseTable.getActionTable().listIterator();
            CxxActionWriter writer = new CxxActionWriter(out, prodFactory, symbolNum);
            while(it.hasNext()) {
                Map<Integer, Action> state = it.next();
                String stateNum = Integer.toString(it.previousIndex());
                MultiMap<Action, Integer> states = new MultiMap<Action, Integer>();
                writer.needHandleReturn = false;

                for(Map.Entry<Integer, Action> entry : state.entrySet()) {
                    states.put(entry.getValue(), entry.getKey());
                }

                out.write("int ");
                out.write(parserName);
                out.write("::state_");
                out.write(stateNum);
                out.write("()\n");
                out.write("{\n");
                out.write("    int backtrack = 0;\n");
                out.write("    Token * t = NULL;\n");
                out.write("    (void)backtrack;/* squelch unused variable warning */\n");
                out.write("    (void)t;/* squelch unused variable warning */\n");
                out.write("    switch(lookahead->getSymbol()) {\n");

                for(Map.Entry<Action, Set<Integer>> entry : states.entrySet()) {
                    for(Integer stateNumber : entry.getValue()) {
                        out.write("    case ");
                        out.write(symbolNum.getInverse(stateNumber));
                        out.write(":\n");
                    }

                    entry.getKey().acceptVisitor(writer, stateNum);
                }
                out.write("    default:\n");
                out.write("        action = ");
                out.write(parserName);
                out.write("_REJECT;\n");
                out.write("        return 0;\n");
                out.write("    }\n\n");

                Map<Integer, Integer> gotoTable = parseTable.getGotoTable().get(it.previousIndex());
                if(writer.needHandleReturn) {
                    out.write("handleReturn:\n");
                    out.write("    if(action != ");
                    out.write(parserName);
                    out.write("_CONTINUE) {\n");
                    out.write("        return 0;\n");
                    out.write("    } else if(backtrack == 0) {\n");
                    if(gotoTable != null) {
                        out.write("        goto gotoAction;\n");
                    } else {
                        out.write("        abort();\n");
                    }
                    out.write("    }\n");
                    out.write("    return backtrack - 1;\n\n");

                    if(gotoTable != null) {
                        out.write("gotoAction:\n");
                        out.write("    switch(stack.peek()->getSymbol()) {\n");
                        for(Map.Entry<Integer, Integer> entry : gotoTable.entrySet()) {
                            out.write("    case ");
                            out.write(symbolNum.getInverse(entry.getKey()));
                            out.write(":\n");
                            out.write("        backtrack = state_");
                            out.write(entry.getValue().toString());
                            out.write("();\n");
                            out.write("        goto handleReturn;\n");
                        }
                        out.write("    }\n");
                        out.write("    abort();\n");
                    }
                }
                out.write("}\n\n");
            }
            out.write("\n");
            
            out.write("const char * ");
            out.write(parserName);
            out.write("::symbolToString(int sym)\n");
            out.write("{\n");
            out.write("    switch(sym) {\n");
            for(String symbol : symbolNum.keySet()) {
                if(!ProductionFactory.START_SYMBOL_NAME.equals(symbol)) {
                    out.write("        case ");
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
            out.write("    }\n");
            out.write("    return 0;\n");
            out.write("}\n");
        } catch(WriterIOException e) {
            throw e.ioexception;
        } catch(WriterGeneratorException e) {
            throw e.generatorException;

        } finally {
            out.close();
        }
    }

    @Override
    public void outputSymbols(File symbolFile) throws IOException, ParserGeneratorException {
        Writer out = new BufferedWriter(new FileWriter(symbolFile));

        out.write("#ifndef " + symbolName.toUpperCase() + "_H\n");
        out.write("#define " + symbolName.toUpperCase() + "_H\n\n");

        out.write("#include <cstdlib>\n");
        out.write("#include <cstdio>\n");
        if(!spec.cppDirectives.isEmpty()) {
            out.write("//begin user-supplied preprocessor directives\n");
            for(String directive : spec.cppDirectives) {
                out.write(directive);
                out.write("\n");
            }
            out.write("//end user-supplied preprocessor directives\n\n");
        }

        for(Map.Entry<String, Integer> entry : symbolNum.entrySet()) {
            if(!ProductionFactory.START_SYMBOL_NAME.equals(entry.getKey())) {
                out.write("const int ");
                out.write(entry.getKey());
                out.write(" = ");
                out.write(Integer.toString(entry.getValue()));
                out.write(";\n");
            }
        }

        out.write("\n");

        out.write("class Token\n");
        out.write("{\n");
        out.write("private:\n");
        out.write("    friend class ");
        out.write("TokenStack");
        out.write(";\n\n");
        out.write("    int symbol;\n");
        out.write("    Token * next;\n");
        Bijection<String, String> typeNames = UtilExtensions.makeBijection();
        for(Map.Entry<String, String> entry : spec.typedefs.entrySet()) {
            typeNames.put(entry.getKey(), entry.getValue());
        }

        if(!spec.typedefs.isEmpty()) {
            out.write("    union {\n");
            for(Map.Entry<String, String> entry : typeNames.entrySet()) {
                out.write("        ");
                out.write(entry.getValue());
                out.write(" ");
                out.write(parserName);
                out.write(entry.getKey());
                out.write(";\n");
            }
            out.write("    };\n");
        }

        out.write("\n");
        for(Map.Entry<String, String> entry : typeNames.entrySet()) {
            out.write("    Token (int s, ");
            out.write(entry.getValue());
            out.write(" val) : symbol(s), ");
            out.write(parserName);
            out.write(entry.getKey());
            out.write("(val)\n");
            out.write("    {\n");
            out.write("    }\n\n");
        }

        out.write("\npublic:\n");
        out.write("    Token(int s) : symbol(s)\n");
        out.write("    {\n");
        out.write("    }\n\n");

        for(Map.Entry<String, String> entry : spec.typedefs.entrySet()) {
            out.write("    static Token * make");
            out.write(entry.getKey());
            out.write("(int s, ");
            out.write(entry.getValue());
            out.write(" val)\n");
            out.write("    {\n");
            out.write("        return new Token(s, val);\n");
            out.write("    }\n\n");

            out.write("    ");
            out.write(entry.getValue());
            out.write(" get");
            out.write(entry.getKey());
            out.write("() const\n");
            out.write("    {\n");
            out.write("        return ");
            out.write(parserName);
            out.write(entry.getKey());
            out.write(";\n");
            out.write("    }\n\n");
        }

        out.write("    int getSymbol() const\n");
        out.write("    {\n");
        out.write("        return symbol;\n");
        out.write("    }\n");
        out.write("};\n\n");

        out.write("class TokenStack\n");
        out.write("{\n");
        out.write("private:\n");
        out.write("    Token * bottom;\n\n");
        out.write("public:\n");
        out.write("    TokenStack() : bottom(NULL)\n");
        out.write("    {\n");
        out.write("    }\n\n");
        out.write("    ~TokenStack()\n");
        out.write("    {\n");
        out.write("        clear();\n");
        out.write("    }\n\n");
        out.write("    void push(Token * t)\n");
        out.write("    {\n");
        out.write("        t->next = bottom;\n");
        out.write("        bottom = t;\n");
        out.write("    }\n\n");
        out.write("    Token * pop()\n");
        out.write("    {\n");
        out.write("        Token * r = bottom;\n");
        out.write("        bottom = bottom->next;\n");
        out.write("        return r;\n");
        out.write("    }\n\n");
        out.write("    const Token * peek() const\n");
        out.write("    {\n");
        out.write("        return bottom;\n");
        out.write("    }\n\n");
        out.write("    void clear()\n");
        out.write("    {\n");
        out.write("        Token * t = bottom;\n");
        out.write("        Token * next;\n");
        out.write("        while(t)\n");
        out.write("        {\n");
        out.write("            next = t->next;\n");
        out.write("            delete t;\n");
        out.write("            t = next;\n");
        out.write("        }\n");
        out.write("        bottom = NULL;\n");
        out.write("    }\n");
        out.write("};\n\n");

        out.write("class Scanner\n");
        out.write("{\n");
        out.write("public:\n");
        out.write("    virtual Token * getToken() = 0;\n");
        out.write("};\n\n");

        out.write("class ");
        out.write(parserName);
        out.write("\n");
        out.write("{\n");
        out.write("public:\n");
        out.write("    ");
        out.write(parserName);
        out.write("() : lookahead(NULL)\n");
        out.write("    {\n");
        out.write("    }\n\n");

        out.write("    ~");
        out.write(parserName);
        out.write("()\n");
        out.write("    {\n");
        out.write("        cleanup();\n");
        out.write("    }\n\n");
        out.write("    bool accept(Scanner *);\n");
        out.write("    static const char * symbolToString(int sym);\n\n");
        out.write("private:\n");
        out.write("    enum Action { ");
        out.write(parserName);
        out.write("_ACCEPT, ");
        out.write(parserName);
        out.write("_REJECT, ");
        out.write(parserName);
        out.write("_CONTINUE};\n");
        out.write("    Scanner * scanner;\n");
        out.write("    TokenStack stack;\n");
        out.write("    Action action;\n");
        out.write("    Token * lookahead;\n");
        out.write("    friend class ParserCleanup;\n\n");

        out.write("    void cleanup()\n");
        out.write("    {\n");
        out.write("        stack.clear();\n");
        out.write("        delete lookahead;\n");
        out.write("        lookahead = NULL;\n");
        out.write("    }\n\n");

        out.write("    void shift()\n");
        out.write("    {\n");
        //out.write("        fprintf(stderr, \"shift %s\\n\", symbolToString(lookahead->getSymbol()));\n");
        out.write("        stack.push(lookahead);\n");
        out.write("        lookahead = scanner->getToken();\n");
        out.write("    }\n\n");
        
        ListIterator<Map<Integer, Action>> it = parseTable.getActionTable().listIterator();
        while(it.hasNext()) {
            it.next();
            out.write("    int state_");
            out.write(Integer.toString(it.previousIndex()));
            out.write("();\n");
        }
        out.write("\n");
        
        MultiMap<ReductionFunction, Production> funcs = 
                new MultiMap<ReductionFunction, Production>(LinkedHashMap.class);

        for(Production p : prodFactory.productions) {
            if(p.name != null) {
                String ret;
                String leftType = v.symbolType(p.lhs);

                if(leftType == null) {
                    ret = "void *";
                } else {
                    ret = spec.getType(leftType);
                }
                
                List<String> argTypes = new ArrayList<String>();
                for(String sym : p.rhs) {
                    String type = v.symbolType(sym);

                    if(type != null) {
                        argTypes.add("Token *");
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
            out.write("    ");
            out.write(f.returnType);
            out.write(" ");
            out.write(f.name);
            out.write("(");
            boolean needComma = false;

            ListIterator<String> jt = f.argTypes.listIterator();
            while(jt.hasNext()) {
                if(needComma)
                    out.write(", ");
                out.write(jt.next());
                out.write(" arg");
                out.write(Integer.toString(jt.previousIndex()));
                needComma = true;
            }

            out.write(");\n\n");
        }

        out.write("};\n\n");

        out.write("class ParserCleanup\n");
        out.write("{\n");
        out.write("    ");
        out.write(parserName);
        out.write("& parser;\n");
        out.write("    friend class ");
        out.write(parserName);
        out.write(";\n\n");
        out.write("    ParserCleanup(");
        out.write(parserName);
        out.write(" p) : parser(p)\n");
        out.write("    {\n");
        out.write("    }\n\n");
        out.write("    ~ParserCleanup()\n");
        out.write("    {\n");
        out.write("        parser.cleanup();\n");
        out.write("    }\n");
        out.write("};\n\n");

        out.write("#endif\n");

        out.close();
    }

}
