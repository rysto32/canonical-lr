package canonical_lr;

public class Sym {
    public static final int EOF = 0;
    public static final int PACKAGE = 1;
    public static final int SEMI = 2;
    public static final int WITH = 3;
    public static final int TERMINAL = 4;
    public static final int NON = 5;
    public static final int COMMA = 6;
    public static final int IMPORT = 7;
    public static final int STATIC = 8;
    public static final int START = 9;
    public static final int PRODUCES = 10;
    public static final int BAR = 11;
    public static final int DOT = 12;
    public static final int COLON = 13;
    public static final int STAR = 14;
    public static final int END_OF_FILE = 15;
    public static final int OPEN_S = 16;
    public static final int CLOSE_S = 17;
    public static final int QUESTION = 18;
    public static final int OPEN_A = 19;
    public static final int CLOSE_A = 20;
    public static final int EXTENDS = 21;
    public static final int SUPER = 22;
    public static final int JAVA = 23;
    public static final int LANG = 24;
    public static final int CXX = 25;
    public static final int TYPE = 26;
    public static final int OPEN_BRACE = 27;
    public static final int CLOSE_BRACE = 28;
    public static final int COLON_COLON = 29;
    public static final int AMPERSAND = 30;
    public static final int ID = 31;
    public static final int CPP_DIRECTIVE = 32;
    public static final int grammar_spec = 33;
    public static final int package_spec = 34;
    public static final int import_spec = 35;
    public static final int import_statement = 36;
    public static final int symbol_list = 37;
    public static final int symbol = 38;
    public static final int declares_symbol = 39;
    public static final int start_spec = 40;
    public static final int production_list = 41;
    public static final int production = 42;
    public static final int rhs_list = 43;
    public static final int multipart_id = 44;
    public static final int java_type_id = 45;
    public static final int symbol_dec_list = 46;
    public static final int parameterized_type = 47;
    public static final int identifier = 48;
    public static final int production_name_opt = 49;
    public static final int rhs = 50;
    public static final int identifier_list = 51;
    public static final int import_id = 52;
    public static final int language_list = 53;
    public static final int lang = 54;
    public static final int java_type_spec = 55;
    public static final int java_type_def = 56;
    public static final int parameterized_type_list = 57;
    public static final int cpp_spec = 58;
    public static final int cxx_type_spec = 59;
    public static final int cxx_type_def = 60;
    public static final int cxx_type_id = 61;
    public static final int cxx_unscoped_type_id = 62;
    public static final int cxx_param_type_list = 63;

public static String symToString(int sym) {
        switch(sym) {
            case EOF: return "EOF";
            case PACKAGE: return "PACKAGE";
            case SEMI: return "SEMI";
            case WITH: return "WITH";
            case TERMINAL: return "TERMINAL";
            case NON: return "NON";
            case COMMA: return "COMMA";
            case IMPORT: return "IMPORT";
            case STATIC: return "STATIC";
            case START: return "START";
            case PRODUCES: return "PRODUCES";
            case BAR: return "BAR";
            case DOT: return "DOT";
            case COLON: return "COLON";
            case STAR: return "STAR";
            case END_OF_FILE: return "END_OF_FILE";
            case OPEN_S: return "OPEN_S";
            case CLOSE_S: return "CLOSE_S";
            case QUESTION: return "QUESTION";
            case OPEN_A: return "OPEN_A";
            case CLOSE_A: return "CLOSE_A";
            case EXTENDS: return "EXTENDS";
            case SUPER: return "SUPER";
            case JAVA: return "JAVA";
            case LANG: return "LANG";
            case CXX: return "CXX";
            case TYPE: return "TYPE";
            case OPEN_BRACE: return "OPEN_BRACE";
            case CLOSE_BRACE: return "CLOSE_BRACE";
            case COLON_COLON: return "COLON_COLON";
            case AMPERSAND: return "AMPERSAND";
            case ID: return "ID";
            case CPP_DIRECTIVE: return "CPP_DIRECTIVE";
            case grammar_spec: return "<grammar_spec>";
            case package_spec: return "<package_spec>";
            case import_spec: return "<import_spec>";
            case import_statement: return "<import_statement>";
            case symbol_list: return "<symbol_list>";
            case symbol: return "<symbol>";
            case declares_symbol: return "<declares_symbol>";
            case start_spec: return "<start_spec>";
            case production_list: return "<production_list>";
            case production: return "<production>";
            case rhs_list: return "<rhs_list>";
            case multipart_id: return "<multipart_id>";
            case java_type_id: return "<java_type_id>";
            case symbol_dec_list: return "<symbol_dec_list>";
            case parameterized_type: return "<parameterized_type>";
            case identifier: return "<identifier>";
            case production_name_opt: return "<production_name_opt>";
            case rhs: return "<rhs>";
            case identifier_list: return "<identifier_list>";
            case import_id: return "<import_id>";
            case language_list: return "<language_list>";
            case lang: return "<lang>";
            case java_type_spec: return "<java_type_spec>";
            case java_type_def: return "<java_type_def>";
            case parameterized_type_list: return "<parameterized_type_list>";
            case cpp_spec: return "<cpp_spec>";
            case cxx_type_spec: return "<cxx_type_spec>";
            case cxx_type_def: return "<cxx_type_def>";
            case cxx_type_id: return "<cxx_type_id>";
            case cxx_unscoped_type_id: return "<cxx_unscoped_type_id>";
            case cxx_param_type_list: return "<cxx_param_type_list>";
        }
        throw new Error("Unrecognized symbol number " + sym);
    }
}
