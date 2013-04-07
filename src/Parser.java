public abstract class Parser extends lr_runtime.LrParser {
    private static final String [] ENCODED_TABLE = {
        "F1B88000000000000000B53F69185B8B8814437A82EB8A43FA42337355F2021B8A8353421392725517BC987CF04B659E1313039F0307726279466E7E1852A48140C72B21BC215FB4B423374FD723B8B4CAD781833D3FB42F1A2DCF842DEB98506D515004B4481426A70213D37C2A2A4CA409EBA86CB42B33F872EC5666064F460692ECCAA4DA82060606069276101904D42880D4E1985C91043C859DF6D1C3C269071999189CD818B272F3135CD08E4BCF22F460EC29C82A4D2EC8CFC949A820B77060010E927E00292004CC4073C27213F2D5FC33FA4253D35B848E1D2852FDB1BDD289046F3B69526E496A65411380024D9F596E625A615BDA99A2BCD35E14733130308DD7C8043C40092CC2833233DA42343A57DC5771BC010A95E3C0C2929A5C5252C02C090800915AF05B21418158C057C00278133C95C4066173880EAA003B9D03CB0C8075DC002FC029DF149A925A9C9ADF746F9AACBBDDB450B7687A0A82F18280A833FC41883A17A9B0216124332D1393935B0A4C866F1C7320A46AF41CE1720AF144A03846A2880588FC77C20260F754500081D31EE0D9200000"
    };

    public Parser() {
        super(ENCODED_TABLE);
    }

    @Override
    protected int getProductionLength(int prodId) {
        switch(prodId) {
            case 0: return 1;
            case 1: return 1;
            case 2: return 1;
        }

        throw new Error("Bad production id " + prodId);
    }

    @Override
    protected int getLhsSymbol(int prodId) {
        switch(prodId) {
            case 0: return Sym.test;//test --> A 
            case 1: return Sym.test;//test --> B 
        }

        throw new Error("Bad production id " + prodId);
    }

    @Override
    protected int getStartState() {
        return 0;
    }

    @Override
    protected int getNumSymbols() {
        return 3;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object performAction(int prodId, java.util.List<lr_runtime.Token> rhs) {
        lr_runtime.Token first;
        switch(prodId) {
            case 0:
                first = findFirst(rhs);
                return Test(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 1:
                first = findFirst(rhs);
                return Test(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
        }
        return null;
    }

    /** Method called when the productions:
      *   test --> B 
      *   test --> A 
      * are reduced.
      */
    protected abstract Object Test(int line, int col);

}
