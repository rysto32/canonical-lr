public class Sym {
    public static final int EOF = 0;
    public static final int A = 1;
    public static final int B = 2;
    public static final int test = 4;

public static String symToString(int sym) {
        switch(sym) {
            case EOF: return "EOF";
            case A: return "A";
            case B: return "B";
            case test: return "<test>";
        }
        throw new Error("Unrecognized symbol number " + sym);
    }
}
