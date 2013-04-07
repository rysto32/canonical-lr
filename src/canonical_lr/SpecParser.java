/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;

import lr_runtime.*;
import canonical_lr.tree.*;

/**
 * Parser that generates a parse tree of the grammar that we parsed.
 */
public class SpecParser extends Parser {

    public SpecParser() {
        super();
    }
    
    private static <T> List<T> makeList(T elem, Class<T> clazz) {
        List<T> list = makeList(clazz);
        list.add(elem);
        return list;
    }
    
    private static <T> List<T> makeList(Class<T> clazz) {
        return Collections.checkedList(new ArrayList<T>(), clazz);
    }
    
    private static <T> List<T> addToList(List<T> list, T elem) {
        list.add(elem);
        return list;
    }

    @Override
    protected String ArrayTypeId(int line, int col, String arg0) {
        return arg0 +"[]";
    }

    @Override
    protected String ExtendsTypeParameter(int line, int col, String arg0) {
        return "? extends " + arg0;
    }

    @Override
    protected String IdIdentifier(int line, int col, Token<String> arg0) {
        return arg0.value;
    }

    @Override
    protected List<String> IdentifierList(int line, int col, List<String> arg0, String arg1) {
        return addToList(arg0, arg1);
    }

    @Override
    protected List<String> IdentifierListTerminator(int line, int col) {
        return makeList(String.class);
    }

    @Override
    protected String Import(int line, int col, String arg0) {
        return "import " + arg0;
    }

    @Override
    protected List<String> ImportList(int line, int col, List<String> arg0, String arg1) {
        return addToList(arg0, arg1);
    }

    @Override
    protected List<String> ImportListTerminator(int line, int col) {
        return makeList(String.class);
    }

    @Override
    protected String MultipartId(int line, int col, String arg0, String arg1) {
        return arg0 + "." + arg1;
    }

    @Override
    protected String MultipartIdTerminator(int line, int col, String arg0) {
        return arg0;
    }

    @Override
    protected String MultipartImportId(int line, int col, String arg0) {
        return arg0;
    }

    @Override
    protected String MultipartTypeId(int line, int col, String arg0) {
        return arg0;
    }

    @Override
    protected String NoPackage(int line, int col) {
        return null;
    }

    @Override
    protected String NonIdentifier(int line, int col) {
        return "non";
    }

    @Override
    protected SymbolDeclarationNode NonTerminalDeclaration(int line, int col, List<String> arg0) {
        return new SymbolDeclarationNode(SymbolType.NON_TERMINAL, null, arg0);
    }

    @Override
    protected String PackagePresent(int line, int col, String arg0) {
        return arg0;
    }

    @Override
    protected String ParameterizedTypeId(int line, int col, String arg0, String arg1) {
        return arg0 + "<" + arg1 + ">";
    }

    @Override
    protected ProductionNode Production(int line, int col, String arg0, List<RightHandSide> arg1) {
        return new ProductionNode(arg0, arg1);
    }

    @Override
    protected List<ProductionNode> ProductionList(int line, int col, List<ProductionNode> arg0, ProductionNode arg1) {
        return addToList(arg0, arg1);
    }

    @Override
    protected List<ProductionNode> ProductionListTerminator(int line, int col, ProductionNode arg0) {
        return makeList(arg0, ProductionNode.class);
    }

    @Override
    protected String ProductionName(int line, int col, String arg0) {
        return arg0;
    }

    @Override
    protected RightHandSide Rhs(int line, int col, List<String> arg0, String arg1) {
        return new RightHandSide(arg0, arg1);
    }

    @Override
    protected List<RightHandSide> RhsList(int line, int col, List<RightHandSide> arg0, RightHandSide arg1) {
        return addToList(arg0, arg1);
    }

    @Override
    protected List<RightHandSide> RhsListTerminator(int line, int col, RightHandSide arg0) {
        return makeList(arg0, RightHandSide.class);
    }

    @Override
    protected String StartIdentifier(int line, int col) {
        return "start";
    }

    @Override
    protected String StartSpec(int line, int col, String arg0) {
        return arg0;
    }

    @Override
    protected String StaticImport(int line, int col, String arg0) {
        return "import static " + arg0;
    }

    @Override
    protected String SuperTypeParameter(int line, int col, String arg0) {
        return "? super " + arg0;
    }

    @Override
    protected List<String> SymbolDeclaration(int line, int col, List<String> arg0) {
        return arg0;
    }

    @Override
    protected List<SymbolDeclarationNode> SymbolList(int line, int col, 
            List<SymbolDeclarationNode> arg0, SymbolDeclarationNode arg1) 
    {
        return addToList(arg0, arg1);
    }

    @Override
    protected List<String> SymbolDeclList(int line, int col, List<String> arg0, String arg1) {
        return addToList(arg0, arg1);
    }

    @Override
    protected List<SymbolDeclarationNode> SymbolListTerminator(int line, int col, SymbolDeclarationNode arg0) {
        return makeList(arg0, SymbolDeclarationNode.class);
    }

    @Override
    protected List<String> SymbolDeclListTerminator(int line, int col, String arg0) {
        return makeList(arg0, String.class);
    }

    @Override
    protected SymbolDeclarationNode TerminalDeclaration(int line, int col, List<String> arg0) {
        return new SymbolDeclarationNode(SymbolType.TERMINAL, null, arg0);
    }

    @Override
    protected String TerminalIdentifier(int line, int col) {
        return "terminal";
    }

    @Override
    protected String TypeParamater(int line, int col, String arg0) {
        return arg0;
    }

    @Override
    protected String WithIdentifier(int line, int col) {
        return "with";
    }

    @Override
    protected String WildcardImportId(int line, int col, String arg0) {
        return arg0 + ".*";
    }

    @Override
    protected String CxxIdentifier(int line, int col) {
        return "cxx";
    }

    @Override
    protected GrammarSpecification GrammarSpec(int line, int col, Map<TargetLanguage, LanguageSpec> arg0, List<SymbolDeclarationNode> arg1, String arg2, List<ProductionNode> arg3) {
        return new GrammarSpecification(arg0, arg1, arg2, arg3);
    }

    @Override
    protected String JavaIdentifier(int line, int col) {
        return "java";
    }

    @Override
    protected LanguageSpec JavaSpec(int line, int col, String arg0, List<String> arg1, Map<String, String> arg2) {
        return new JavaLanguageSpec(arg0, arg1, arg2);
    }

    @Override
    protected Map<TargetLanguage, LanguageSpec> LanguageList(int line, int col, Map<TargetLanguage, LanguageSpec> arg0, LanguageSpec arg1) {
        arg0.put(arg1.getTargetLanguage(), arg1);
        return arg0;
    }

    @Override
    protected Map<TargetLanguage, LanguageSpec> LanguageList(int line, int col, LanguageSpec arg0) {
        Map<TargetLanguage, LanguageSpec> map = new EnumMap<TargetLanguage, LanguageSpec>(TargetLanguage.class);
        map.put(arg0.getTargetLanguage(), arg0);
        return map;
    }

    @Override
    protected TypeDefinition TypeDef(int line, int col, Token<String> arg0, String arg1) {
        return new TypeDefinition(arg0.value, arg1);
    }

    @Override
    protected Map<String, String> TypeSpec(int line, int col, Map<String, String> arg0, TypeDefinition arg1) {
        arg0.put(arg1.name, arg1.type);
        return arg0;
    }

    @Override
    protected Map<String, String> TypeSpec(int line, int col) {
        return new HashMap<String, String>();
    }

    @Override
    protected String LanguageIdentifier(int line, int col) {
        return "lang";
    }

    @Override
    protected SymbolDeclarationNode NonTerminalDeclaration(int line, int col, Token<String> arg0, List<String> arg1) {
        return new SymbolDeclarationNode(SymbolType.NON_TERMINAL, arg0.value, arg1);
    }

    @Override
    protected SymbolDeclarationNode TerminalDeclaration(int line, int col, Token<String> arg0, List<String> arg1) {
        return new SymbolDeclarationNode(SymbolType.TERMINAL, arg0.value, arg1);
    }

    @Override
    protected String TypeIdentifier(int line, int col) {
        return "type";
    }

    @Override
    protected String ParameterizedTypeList(int line, int col, String arg0, String arg1) {
        return arg0 + ", " + arg1;
    }

    @Override
    protected String ParameterizedTypeList(int line, int col, String arg0) {
        return arg0;
    }

    @Override
    protected List<String> CppSpec(int line, int col, List<String> arg0, Token<String> arg1) {
        arg0.add(arg1.value);
        return arg0;
    }

    @Override
    protected List<String> CppSpec(int line, int col) {
        return new ArrayList<String>();
    }

    @Override
    protected LanguageSpec CxxSpec(int line, int col, List<String> arg0, Map<String, String> arg1) {
        return new CxxLanguageSpec(arg0, arg1);
    }

    @Override
    protected String CxxParameterizedTypeId(int line, int col, String arg0, String arg1) {
        return arg0 + "<" + arg1 + ">";
    }

    @Override
    protected String CxxPointerTypeId(int line, int col, String arg0) {
        return arg0 + "*";
    }

    @Override
    protected String CxxReferenceTypeId(int line, int col, String arg0) {
        return arg0 + "&";
    }

    @Override
    protected String CxxScopedTypeId(int line, int col, String arg0, String arg1) {
        return arg0 + "::" + arg1;
    }

    @Override
    protected String CxxTypeId(int line, int col, String arg0) {
        return arg0;
    }

    @Override
    protected String CxxTypeId(int line, int col, Token<String> arg0) {
        return arg0.value;
    }

    @Override
    protected String CxxTypeList(int line, int col, String arg0) {
        return arg0;
    }

    @Override
    protected String CxxTypeList(int line, int col, String arg0, String arg1) {
        return arg0 + ", " + arg1;
    }

    @Override
    protected String convertSym(int sym) {
        return Symbol.symbolString(sym);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void suggestRepair(List<Token> list, NavigableSet<Integer> changes) {
        System.err.print("  Did you mean:\n  ");
        List<Integer> tokenWidth = new ArrayList<Integer>(list.size());
        int start = changes.first();
        for(Token t : list.subList(Math.max(start - 3, 0), list.size())) {
            String s = t.toString();
            System.err.print(s);
            System.err.print(" ");
            
            tokenWidth.add(s.length());
        }
        System.err.print("\n  ");
        
        for(int i = 0; i < tokenWidth.size(); i++) {
           
            int width = tokenWidth.get(i);
            for(int j = 0; j < width; j++) {
                if(changes.contains(i)) {
                    System.err.print("^");
                } else {
                    System.err.print(" ");
                }
            }
            
            System.err.print(" ");
        }
        
        System.err.println("");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void reportError(Token t, int state, List<String> possible) {
        System.err.println("Error @ line " + t.getLine() + " column " + t.getColumn() + ": " +
                "caused by token " + t + "(" + t.getSym() + ") possible=" + possible);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void reportGiveUp(Token t) {
        System.err.println("Error @ line " + t.getLine() + " column " + t.getColumn() + ": " +
                "Too many parse errors; bailing out");
    }
}
