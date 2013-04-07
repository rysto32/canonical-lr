package canonical_lr;

import java.util.*;
import canonical_lr.tree.*;

public abstract class Parser extends lr_runtime.LrParser {
    private static final String [] ENCODED_TABLE = {
        "F1B88000000000000000DCB5F707455CD1F7FE848F1710909018818040E0E4E09E5806398896D04534CB639060828A411E84E421D09F179B483D25196A8451B28548AA49A86D25FC3225A8806D222A8A891CF30A3599967AE034DE83635BD9E035C6D9AD43BFD3FD76FE6D7BBF6FCA325668D9FCBD7BBFDDFEFEDDDFEBBCEBF0C8CCEC0B197D21E591EEA6D843FAD09FF6C068B334B428BAB52435E9B39F7C3733F91FBBC075D81951C688437B5B2DB88819D537B70BB385CD5196E692EA96EEC8454D81326D4B54ADE397D3A39E57D60BDB22ADE38B84E2BE3F3BE3FFCF07838772B971DD876A0F1D7328BB7881660C8C8EC6EBB241D673C03EDC5791CAD1C94368F4AB128D9D4384C2378DFEE8FBA3E65D9E1268B6181EE696B063E28146DAD2C1036444A92C1AEC6A6B696C86B75EC338DFB19BE68F06B9D38FFD401B6906BEA1F70A5321A531A07EC9FD3F4D78B173F5DE26BE766770B5AB241D0B19DC7C5D57DAD5D1A0FDBBF674166D3C7E6B8BC0388FBCF14269D94A3B5CD4DC7B54A966C6AD776FAF9BB460965D8191D81AEC844C8CD4822819A2EF46946AC8E03EE638136FB8821C1AF92E6330342B91FD94B4D2A0536753486BE98796AF4E50FE4888FE19DE1E6B1CE466E904321F73B03DD5C04E0E13735F1742D3AC890313ECD486CC02A992B1C320387801AC2C3FFA082348735504992CB9B1086CB0F6E550ABC587337086480F667030E61A3F110A201EDCA40A9C2CB9BF462D3922371A52F33050CA9256D412051109708CB04E302730119A25070959AF83A03EA4809C8F32738481F95397A4BB3D0EC6144F16C524ED9FC83BBEC8116AC459D491267BA6D30D85827FA14FD0560618A566C4B2180E10927DCAB7CB2780D29C9799FC0451B6A68535D480CF516AE3E569AF8398E1539B7F18C7DB27645166C5D891B8D9DB220CA9235A4E946F164EB4129F25C24B453853E6BDA0A97247E135AD6A4213A92E24A988826F402B7082C058ECB9822258ECB3928358EC015C9A0D93D924658ECFE154FA0D9F3226858EC952804979692FEB167ACB582321E6C0130604A454493A6D192708A808C304E502F109B18CF044146BE2D23D40CEC433BDEC437D529968535B6765979DECA231DECA21C42BA0B6AE29C02814FB65A5544B925CC5F80D9D0A1428914823C6F0B66BA4DE867A0CA3B350EDE94A04815547BE5D67A0CB30868B0F6E95B35DEB867AADA6145BE3B35DE17994943582D25D3C74752C7438017A10E3A41F95D8914EDAB60E3A7708586687BEA587B6B966AAFB692C0953B71BADE58B1EE587011A58484D170CA99001B6214CA9201B6A1489B2022CC48853B02C41348993A9BD9C79810AC7F13DC915C891175A7767366C46849DB092B71256F24ACE5849DB092B71256F24ACE5849DBACD3AA01C1C01CA6BF14F6F7A59C9ABDE26B63525C61A3D4BB00BB7DE20CEBF67164BACE2C8ED84A03247E63BB8529EECB6ADD8EB25D72F36DC254C0AA7357EE023D1E02725376CC1608E446D7A93366322FE4925061FC9A77964C8356D9A4A027CA7F55CA776019E48AE4850EBE414FC9E4992D34B20B0C86877F956D056FF3BA61D8532FB2B16884F898994223B66F260F76CC362C9F194152D3F32DABCFCF8F5E42DC34D842F35E8DF308021AC3EAAD20DCB3815AC4A4F37E928818A808C304E5027309F08CF0456045E044929ECB457401E2F4016F010D831EDC40DC078B21C25FB2D12ED718E785D632A8F2879C928D4E41F8E344EA43E0742C73260344C068881C0113812260344C068881C01138122609A360344C06809EE766B990B8C9FA28AEBF61778E2380C71D97FDF780A98DE0D4CE0862672431F804EFDD0D9C3E0D4C3109AF9C4B4AE92EC6FFE38DFBF06FFE38DFBF06F7966EE1A0207A98004F4F51059F89535B044957E73CA1ACA3FB5572030DBA48ED7004F6004F6004F6043313D55613E6A9694E9045D049748C9370B4D7133C2D3728E1F5D273DE534D7F57EA6FBE0220956A65079092BA790C765835C996DA6666954FCCC2DC2471505A6CE692D33D2F888E78347F3222AF1EBCDF888096A2E199ACA5100512051109708CB04E3027309F50159535E0452A41F9BEAA41CDB4B288C511930544E0411930548E6D7196DDC703E9FA97B0B97889C1B0786245B045704B0909AB0A5CA9B8093E224E096217EE432DDBF87EEB1005E1005EFC896A40DA5E925B6E6878D404887E8012F5C0FDBF22EEE9CBEC14408D94FCC94A4D175A0CFE86DCB77E41B4673D369489045404E102F209B18C704E70A212496D5A36DB3B3E15256E93A7E2C5A7686E900B01334AF1660B7D42A6329B0436188C794A7EC1E97B200FAA00C77200FD900C77200FD900C36200F2A4AA533CB880593019F3947763E190949F75D70026DC9076D01DD534CB4947069B4EBA80E5FE67E441256E8789E7EEB9B2B64CB5A64D9530D706A58310AAF4B7998965389648A618AE0882FC94F3C70FD862002548AA08AA184DFD378B851309A8183D8180834D0C8535A07A5D8F6C6170569E9B24833E46856473B2D23BA959A19308BD673092607D4635A7D4618DCA652B924AE6A3C025AE69E7573E7E54D829DA671A6501673B3F5BDC1CC40196AA56853D1E35C15023C0B2D096E3853E29C6BF1118A232D30B4A152EC1474539213F6DBE88F3BD295D94A0541A3F65D8AE823DAD42BC6B6E2787EE27BC6B904E5027309F08CF044B3BE27DCA79D82CCD51075C51ECEC02D00F4A372150D0B6EB0BA9C39C67102BD058C6148C614DCCF13E1658608E95AFDD921A711C2FBF493A5C218F167E49B9F0FE42436184D727512C398088B5EE7B43EC16C62B4F8EF2CD21317171D4714C5BB21FB170233576A5703C65DC596C065A826529E61E6BF9F2E93B3BD5222658E42F361F3F5C5F4EBE2472D61E2E7EB0A249B8F9F28A28C26673ADFD4CC7E2D3CBADE7BFE238DC4AA7712C36202C36B12C36B93A378E4126482A3B7CECA0F29D951A7DECA0B0CECA0F48D951E1B42583197B3BAC55C00DED6BDDEC41BC81827E43ED5B33DCB0EC9D55ABDBD52DCEAC36C360F717E28C526B87EB9D3F28F8A7BD7E402E89617092D8301EFF9C74F6E27C920342C7B005F1968F1FD58B46651017E95D71F41288B0C2D189BFD29DC49E7DC59ED1BB2C2E0CC30A9B5097115470533B0CC0BC286E12BC289045404E102F209B18C7045604E70A2702A3B3D127E3F1E7C6992CC52C05A6DCDD4AF6DB45B2BB943776AC603474812AD99E883BB49071ABC5025F53E29EC94C3F2D3A4C2083A1E0E3E8A2316989B9FB3A9EC7793CC7FA0C04FA8275AE61E78BB3FB1A124DE1993BBE8FF52B7A69EF98E794CEC1342CDB5BA08AA184D738D423C1AA7E94A6BC28ADED4DCB303D2CA1552E2507ECF792894400C9B494907A3EC48BCDB50E9FD27E295D92B241318A018A808C304E5027309F08AC08CF045E0449655A9519260E971C7F6E7A605D56090FC042C33ECBBFA4FCDACF62E7002AF1488E8C246C1688EF2040E711A483A018F814C49EF27B480A91780A97B1143F67A5F5AA77966E8E966918CCFDB3BF44C1A8983413178262E05C4C1A89834D1178A82E0F9883454178F44C1AC2579088F6C4F53B283CC77812AFE913C4351D9F054F9382AFC93BBE8AB8812E1BBB6D260D8B5C9B4E9AB5E799809AFD3DC2FEB146D3E91D279BA283FAF4192448ECF1395658EC3146461A3FBB4A541A3BD9C9048EC622BD37E684FA2E91A5AAC0E5C969611348A0C197135A68F7B68F607616D8D924ABCE4CCF1A360052ECE179EFC73C32ABFD268218A018A808C304E502F109B18CF0456045E0452AC0B9B4FFDAC3FCA5DC53CD2ACEA1EA47D213E1E17BB8709690258F82949D147A4B3C94FD6C088FB648B95D2E23FA8FF042530A9B15EC9794ACCD528FD4EA06B37142AC7B8E9B438DDFCFBCCE4352D232E22A8711A3F70EC17BCFAAB5F092AE1254D34A0E71CDA78415F0928059109F18AC188C01AEF9442F2A52F2556590E9DCA1D92F52B62DAFCB494224A5A8F63A5448EC27765C7E341F9F05C3E011904E102F209B18C7045604E70A270A215A6B477FF3E749F4C12C5351AAE2F280F7F255E6999D52DF3D22EE7224197AA08AA184D5EE1E78039E01488B9F76BC646263533F30BC646EFDC2B199F77BC236E50BCA56EF3C2BC89F1A5AC63FF9FF38D38FD6E58B1E1FB22A6CF0988E81136D220E8FB0E945A53B98613C4E58DDAA4FAF112A2E1664AE14C3E3FCE1FF587C384526F2059E56D360FCD82F427A07E237F068D6460660E17F0B5DCC3DA27C4EBADD3C04B2E1F82F5C7BAC8D5C80F00DE4E3DE53592C8FAB116C3E61BCDBDCD0871F43725B151228B64168D309A49DFC904F8ACB48FC06F3A157550CF562229C07DB0BF39617D22C3E21EB6CBEA097DCBF8CF9136FA50AB68DBDD487C729C1422AA8A85FC838A72EF03D8AA54DA4CA1A95CC95BDCA76A8F620D5104C08BAE0F8D84CF55A2545C0EF91B9BCACE7EA562A4908BB451B03F9BF029B2B660ECFC989C775F607337E567BEDC85144EFD35C06752EC4143ECBA4DD3B1CCEB13BBE6509FF9296C953BA83B6F09A85BB998F1A5DCDA769661CE62FFBA7E5C9AE3EBE849DDAA7AD8964BEEBFE8256DB0E2A020B5811075EAA4D9B41387D2759CE455250E940B58D32F953383C8BC8006487188D7B007DD3C37B1968602D53DE67B33D6C49293110251F507C5D696AA91F61B49D6453F5DE965EE60C660B25972212F24A57F3259E99B56E1C4D01FF52F52F9D47C72911DE18BA4737A2BF459374CD209DC5A3D2DB6871748CDC638AC9A7B060FD815EE442B8002FB2ECFD135AA9A3E3D9407E1967FFAA2BEF23E1FF4E0752ACD6CCA82375506ADDDD19ABD81F0FBE84DD12E0A24BBEAE1C0BF9474AC9E68A898D4C30A889C795F2F6C7B9F2E783DD83ABFDE2E8E05AD9288D3E361D8EF71C481BF09E9640000"
    };

    public Parser() {
        super(ENCODED_TABLE);
    }

    @Override
    protected int getProductionLength(int prodId) {
        switch(prodId) {
            case 0: return 4;
            case 1: return 2;
            case 2: return 1;
            case 3: return 7;
            case 4: return 6;
            case 5: return 2;
            case 6: return 0;
            case 7: return 2;
            case 8: return 0;
            case 9: return 4;
            case 10: return 2;
            case 11: return 0;
            case 12: return 4;
            case 13: return 3;
            case 14: return 0;
            case 15: return 2;
            case 16: return 0;
            case 17: return 3;
            case 18: return 4;
            case 19: return 1;
            case 20: return 3;
            case 21: return 2;
            case 22: return 1;
            case 23: return 3;
            case 24: return 4;
            case 25: return 2;
            case 26: return 3;
            case 27: return 2;
            case 28: return 3;
            case 29: return 1;
            case 30: return 4;
            case 31: return 2;
            case 32: return 1;
            case 33: return 4;
            case 34: return 2;
            case 35: return 0;
            case 36: return 3;
            case 37: return 1;
            case 38: return 2;
            case 39: return 2;
            case 40: return 0;
            case 41: return 1;
            case 42: return 1;
            case 43: return 1;
            case 44: return 1;
            case 45: return 1;
            case 46: return 1;
            case 47: return 1;
            case 48: return 1;
            case 49: return 1;
            case 50: return 3;
            case 51: return 1;
            case 52: return 1;
            case 53: return 3;
            case 54: return 4;
            case 55: return 3;
            case 56: return 1;
            case 57: return 1;
            case 58: return 3;
            case 59: return 3;
            case 60: return 1;
            case 61: return 3;
            case 62: return 1;
            case 63: return 4;
            case 64: return 2;
            case 65: return 2;
            case 66: return 1;
            case 67: return 3;
            case 68: return 1;
        }

        throw new Error("Bad production id " + prodId);
    }

    @Override
    protected int getLhsSymbol(int prodId) {
        switch(prodId) {
            case 0: return Sym.grammar_spec;//grammar_spec --> language_list symbol_list start_spec production_list 
            case 1: return Sym.language_list;//language_list --> language_list lang 
            case 2: return Sym.language_list;//language_list --> lang 
            case 3: return Sym.lang;//lang --> LANG JAVA OPEN_BRACE package_spec import_spec java_type_spec CLOSE_BRACE 
            case 4: return Sym.lang;//lang --> LANG CXX OPEN_BRACE cpp_spec cxx_type_spec CLOSE_BRACE 
            case 5: return Sym.cpp_spec;//cpp_spec --> cpp_spec CPP_DIRECTIVE 
            case 6: return Sym.cpp_spec;//cpp_spec --> 
            case 7: return Sym.cxx_type_spec;//cxx_type_spec --> cxx_type_spec cxx_type_def 
            case 8: return Sym.cxx_type_spec;//cxx_type_spec --> 
            case 9: return Sym.cxx_type_def;//cxx_type_def --> TYPE ID cxx_type_id SEMI 
            case 10: return Sym.java_type_spec;//java_type_spec --> java_type_spec java_type_def 
            case 11: return Sym.java_type_spec;//java_type_spec --> 
            case 12: return Sym.java_type_def;//java_type_def --> TYPE ID java_type_id SEMI 
            case 13: return Sym.package_spec;//package_spec --> PACKAGE multipart_id SEMI 
            case 14: return Sym.package_spec;//package_spec --> 
            case 15: return Sym.import_spec;//import_spec --> import_spec import_statement 
            case 16: return Sym.import_spec;//import_spec --> 
            case 17: return Sym.import_statement;//import_statement --> IMPORT import_id SEMI 
            case 18: return Sym.import_statement;//import_statement --> IMPORT STATIC import_id SEMI 
            case 19: return Sym.import_id;//import_id --> multipart_id 
            case 20: return Sym.import_id;//import_id --> multipart_id DOT STAR 
            case 21: return Sym.symbol_list;//symbol_list --> symbol_list symbol 
            case 22: return Sym.symbol_list;//symbol_list --> symbol 
            case 23: return Sym.symbol;//symbol --> TERMINAL ID declares_symbol 
            case 24: return Sym.symbol;//symbol --> NON TERMINAL ID declares_symbol 
            case 25: return Sym.symbol;//symbol --> TERMINAL declares_symbol 
            case 26: return Sym.symbol;//symbol --> NON TERMINAL declares_symbol 
            case 27: return Sym.declares_symbol;//declares_symbol --> symbol_dec_list SEMI 
            case 28: return Sym.symbol_dec_list;//symbol_dec_list --> symbol_dec_list COMMA identifier 
            case 29: return Sym.symbol_dec_list;//symbol_dec_list --> identifier 
            case 30: return Sym.start_spec;//start_spec --> START WITH identifier SEMI 
            case 31: return Sym.production_list;//production_list --> production_list production 
            case 32: return Sym.production_list;//production_list --> production 
            case 33: return Sym.production;//production --> identifier PRODUCES rhs_list SEMI 
            case 34: return Sym.production_name_opt;//production_name_opt --> COLON identifier 
            case 35: return Sym.production_name_opt;//production_name_opt --> 
            case 36: return Sym.rhs_list;//rhs_list --> rhs_list BAR rhs 
            case 37: return Sym.rhs_list;//rhs_list --> rhs 
            case 38: return Sym.rhs;//rhs --> identifier_list production_name_opt 
            case 39: return Sym.identifier_list;//identifier_list --> identifier_list identifier 
            case 40: return Sym.identifier_list;//identifier_list --> 
            case 41: return Sym.identifier;//identifier --> ID 
            case 42: return Sym.identifier;//identifier --> WITH 
            case 43: return Sym.identifier;//identifier --> TERMINAL 
            case 44: return Sym.identifier;//identifier --> NON 
            case 45: return Sym.identifier;//identifier --> START 
            case 46: return Sym.identifier;//identifier --> LANG 
            case 47: return Sym.identifier;//identifier --> JAVA 
            case 48: return Sym.identifier;//identifier --> CXX 
            case 49: return Sym.identifier;//identifier --> TYPE 
            case 50: return Sym.multipart_id;//multipart_id --> multipart_id DOT identifier 
            case 51: return Sym.multipart_id;//multipart_id --> identifier 
            case 52: return Sym.java_type_id;//java_type_id --> multipart_id 
            case 53: return Sym.java_type_id;//java_type_id --> java_type_id OPEN_S CLOSE_S 
            case 54: return Sym.java_type_id;//java_type_id --> multipart_id OPEN_A parameterized_type_list CLOSE_A 
            case 55: return Sym.parameterized_type_list;//parameterized_type_list --> parameterized_type_list COMMA parameterized_type 
            case 56: return Sym.parameterized_type_list;//parameterized_type_list --> parameterized_type 
            case 57: return Sym.parameterized_type;//parameterized_type --> java_type_id 
            case 58: return Sym.parameterized_type;//parameterized_type --> QUESTION EXTENDS java_type_id 
            case 59: return Sym.parameterized_type;//parameterized_type --> QUESTION SUPER java_type_id 
            case 60: return Sym.cxx_type_id;//cxx_type_id --> cxx_unscoped_type_id 
            case 61: return Sym.cxx_type_id;//cxx_type_id --> cxx_type_id COLON_COLON cxx_unscoped_type_id 
            case 62: return Sym.cxx_unscoped_type_id;//cxx_unscoped_type_id --> ID 
            case 63: return Sym.cxx_unscoped_type_id;//cxx_unscoped_type_id --> cxx_unscoped_type_id OPEN_A cxx_param_type_list CLOSE_A 
            case 64: return Sym.cxx_unscoped_type_id;//cxx_unscoped_type_id --> cxx_unscoped_type_id STAR 
            case 65: return Sym.cxx_unscoped_type_id;//cxx_unscoped_type_id --> cxx_unscoped_type_id AMPERSAND 
            case 66: return Sym.cxx_param_type_list;//cxx_param_type_list --> cxx_type_id 
            case 67: return Sym.cxx_param_type_list;//cxx_param_type_list --> cxx_param_type_list COMMA cxx_type_id 
        }

        throw new Error("Bad production id " + prodId);
    }

    @Override
    protected int getStartState() {
        return 0;
    }

    @Override
    protected int getNumSymbols() {
        return 63;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object performAction(int prodId, java.util.List<lr_runtime.Token> rhs) {
        lr_runtime.Token first;
        switch(prodId) {
            case 0:
                first = findFirst(rhs);
                return GrammarSpec(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (Map<TargetLanguage, LanguageSpec>)rhs.get(0).value, (List<SymbolDeclarationNode>)rhs.get(1).value, (String)rhs.get(2).value, (List<ProductionNode>)rhs.get(3).value);
            case 1:
                first = findFirst(rhs);
                return LanguageList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (Map<TargetLanguage, LanguageSpec>)rhs.get(0).value, (LanguageSpec)rhs.get(1).value);
            case 2:
                first = findFirst(rhs);
                return LanguageList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (LanguageSpec)rhs.get(0).value);
            case 3:
                first = findFirst(rhs);
                return JavaSpec(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(3).value, (List<String>)rhs.get(4).value, (Map<String, String>)rhs.get(5).value);
            case 4:
                first = findFirst(rhs);
                return CxxSpec(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<String>)rhs.get(3).value, (Map<String, String>)rhs.get(4).value);
            case 5:
                first = findFirst(rhs);
                return CppSpec(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<String>)rhs.get(0).value, (lr_runtime.Token<String>)rhs.get(1));
            case 6:
                first = findFirst(rhs);
                return CppSpec(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 7:
                first = findFirst(rhs);
                return TypeSpec(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (Map<String, String>)rhs.get(0).value, (TypeDefinition)rhs.get(1).value);
            case 8:
                first = findFirst(rhs);
                return TypeSpec(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 9:
                first = findFirst(rhs);
                return TypeDef(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (lr_runtime.Token<String>)rhs.get(1), (String)rhs.get(2).value);
            case 10:
                first = findFirst(rhs);
                return TypeSpec(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (Map<String, String>)rhs.get(0).value, (TypeDefinition)rhs.get(1).value);
            case 11:
                first = findFirst(rhs);
                return TypeSpec(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 12:
                first = findFirst(rhs);
                return TypeDef(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (lr_runtime.Token<String>)rhs.get(1), (String)rhs.get(2).value);
            case 13:
                first = findFirst(rhs);
                return PackagePresent(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(1).value);
            case 14:
                first = findFirst(rhs);
                return NoPackage(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 15:
                first = findFirst(rhs);
                return ImportList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<String>)rhs.get(0).value, (String)rhs.get(1).value);
            case 16:
                first = findFirst(rhs);
                return ImportListTerminator(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 17:
                first = findFirst(rhs);
                return Import(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(1).value);
            case 18:
                first = findFirst(rhs);
                return StaticImport(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(2).value);
            case 19:
                first = findFirst(rhs);
                return MultipartImportId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 20:
                first = findFirst(rhs);
                return WildcardImportId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 21:
                first = findFirst(rhs);
                return SymbolList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<SymbolDeclarationNode>)rhs.get(0).value, (SymbolDeclarationNode)rhs.get(1).value);
            case 22:
                first = findFirst(rhs);
                return SymbolListTerminator(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (SymbolDeclarationNode)rhs.get(0).value);
            case 23:
                first = findFirst(rhs);
                return TerminalDeclaration(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (lr_runtime.Token<String>)rhs.get(1), (List<String>)rhs.get(2).value);
            case 24:
                first = findFirst(rhs);
                return NonTerminalDeclaration(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (lr_runtime.Token<String>)rhs.get(2), (List<String>)rhs.get(3).value);
            case 25:
                first = findFirst(rhs);
                return TerminalDeclaration(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<String>)rhs.get(1).value);
            case 26:
                first = findFirst(rhs);
                return NonTerminalDeclaration(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<String>)rhs.get(2).value);
            case 27:
                first = findFirst(rhs);
                return SymbolDeclaration(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<String>)rhs.get(0).value);
            case 28:
                first = findFirst(rhs);
                return SymbolDeclList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<String>)rhs.get(0).value, (String)rhs.get(2).value);
            case 29:
                first = findFirst(rhs);
                return SymbolDeclListTerminator(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 30:
                first = findFirst(rhs);
                return StartSpec(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(2).value);
            case 31:
                first = findFirst(rhs);
                return ProductionList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<ProductionNode>)rhs.get(0).value, (ProductionNode)rhs.get(1).value);
            case 32:
                first = findFirst(rhs);
                return ProductionListTerminator(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (ProductionNode)rhs.get(0).value);
            case 33:
                first = findFirst(rhs);
                return Production(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value, (List<RightHandSide>)rhs.get(2).value);
            case 34:
                first = findFirst(rhs);
                return ProductionName(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(1).value);
            case 36:
                first = findFirst(rhs);
                return RhsList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<RightHandSide>)rhs.get(0).value, (RightHandSide)rhs.get(2).value);
            case 37:
                first = findFirst(rhs);
                return RhsListTerminator(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (RightHandSide)rhs.get(0).value);
            case 38:
                first = findFirst(rhs);
                return Rhs(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<String>)rhs.get(0).value, (String)rhs.get(1).value);
            case 39:
                first = findFirst(rhs);
                return IdentifierList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (List<String>)rhs.get(0).value, (String)rhs.get(1).value);
            case 40:
                first = findFirst(rhs);
                return IdentifierListTerminator(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 41:
                first = findFirst(rhs);
                return IdIdentifier(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (lr_runtime.Token<String>)rhs.get(0));
            case 42:
                first = findFirst(rhs);
                return WithIdentifier(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 43:
                first = findFirst(rhs);
                return TerminalIdentifier(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 44:
                first = findFirst(rhs);
                return NonIdentifier(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 45:
                first = findFirst(rhs);
                return StartIdentifier(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 46:
                first = findFirst(rhs);
                return LanguageIdentifier(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 47:
                first = findFirst(rhs);
                return JavaIdentifier(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 48:
                first = findFirst(rhs);
                return CxxIdentifier(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 49:
                first = findFirst(rhs);
                return TypeIdentifier(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn());
            case 50:
                first = findFirst(rhs);
                return MultipartId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value, (String)rhs.get(2).value);
            case 51:
                first = findFirst(rhs);
                return MultipartIdTerminator(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 52:
                first = findFirst(rhs);
                return MultipartTypeId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 53:
                first = findFirst(rhs);
                return ArrayTypeId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 54:
                first = findFirst(rhs);
                return ParameterizedTypeId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value, (String)rhs.get(2).value);
            case 55:
                first = findFirst(rhs);
                return ParameterizedTypeList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value, (String)rhs.get(2).value);
            case 56:
                first = findFirst(rhs);
                return ParameterizedTypeList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 57:
                first = findFirst(rhs);
                return TypeParamater(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 58:
                first = findFirst(rhs);
                return ExtendsTypeParameter(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(2).value);
            case 59:
                first = findFirst(rhs);
                return SuperTypeParameter(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(2).value);
            case 60:
                first = findFirst(rhs);
                return CxxTypeId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 61:
                first = findFirst(rhs);
                return CxxScopedTypeId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value, (String)rhs.get(2).value);
            case 62:
                first = findFirst(rhs);
                return CxxTypeId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (lr_runtime.Token<String>)rhs.get(0));
            case 63:
                first = findFirst(rhs);
                return CxxParameterizedTypeId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value, (String)rhs.get(2).value);
            case 64:
                first = findFirst(rhs);
                return CxxPointerTypeId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 65:
                first = findFirst(rhs);
                return CxxReferenceTypeId(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 66:
                first = findFirst(rhs);
                return CxxTypeList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value);
            case 67:
                first = findFirst(rhs);
                return CxxTypeList(first == null ? -1 : first.getLine(), first == null ? -1 : first.getColumn(), (String)rhs.get(0).value, (String)rhs.get(2).value);
        }
        return null;
    }

    /** Method called when the production:
      *   grammar_spec --> language_list symbol_list start_spec production_list 
      * is reduced.
      */
    protected abstract GrammarSpecification GrammarSpec(int line, int col, Map<TargetLanguage, LanguageSpec> arg0, List<SymbolDeclarationNode> arg1, String arg2, List<ProductionNode> arg3);

    /** Method called when the production:
      *   language_list --> language_list lang 
      * is reduced.
      */
    protected abstract Map<TargetLanguage, LanguageSpec> LanguageList(int line, int col, Map<TargetLanguage, LanguageSpec> arg0, LanguageSpec arg1);

    /** Method called when the production:
      *   language_list --> lang 
      * is reduced.
      */
    protected abstract Map<TargetLanguage, LanguageSpec> LanguageList(int line, int col, LanguageSpec arg0);

    /** Method called when the production:
      *   lang --> LANG JAVA OPEN_BRACE package_spec import_spec java_type_spec CLOSE_BRACE 
      * is reduced.
      */
    protected abstract LanguageSpec JavaSpec(int line, int col, String arg0, List<String> arg1, Map<String, String> arg2);

    /** Method called when the production:
      *   lang --> LANG CXX OPEN_BRACE cpp_spec cxx_type_spec CLOSE_BRACE 
      * is reduced.
      */
    protected abstract LanguageSpec CxxSpec(int line, int col, List<String> arg0, Map<String, String> arg1);

    /** Method called when the production:
      *   cpp_spec --> cpp_spec CPP_DIRECTIVE 
      * is reduced.
      */
    protected abstract List<String> CppSpec(int line, int col, List<String> arg0, lr_runtime.Token<String> arg1);

    /** Method called when the production:
      *   cpp_spec --> 
      * is reduced.
      */
    protected abstract List<String> CppSpec(int line, int col);

    /** Method called when the productions:
      *   java_type_spec --> java_type_spec java_type_def 
      *   cxx_type_spec --> cxx_type_spec cxx_type_def 
      * are reduced.
      */
    protected abstract Map<String, String> TypeSpec(int line, int col, Map<String, String> arg0, TypeDefinition arg1);

    /** Method called when the productions:
      *   cxx_type_spec --> 
      *   java_type_spec --> 
      * are reduced.
      */
    protected abstract Map<String, String> TypeSpec(int line, int col);

    /** Method called when the productions:
      *   java_type_def --> TYPE ID java_type_id SEMI 
      *   cxx_type_def --> TYPE ID cxx_type_id SEMI 
      * are reduced.
      */
    protected abstract TypeDefinition TypeDef(int line, int col, lr_runtime.Token<String> arg0, String arg1);

    /** Method called when the production:
      *   package_spec --> PACKAGE multipart_id SEMI 
      * is reduced.
      */
    protected abstract String PackagePresent(int line, int col, String arg0);

    /** Method called when the production:
      *   package_spec --> 
      * is reduced.
      */
    protected abstract String NoPackage(int line, int col);

    /** Method called when the production:
      *   import_spec --> import_spec import_statement 
      * is reduced.
      */
    protected abstract List<String> ImportList(int line, int col, List<String> arg0, String arg1);

    /** Method called when the production:
      *   import_spec --> 
      * is reduced.
      */
    protected abstract List<String> ImportListTerminator(int line, int col);

    /** Method called when the production:
      *   import_statement --> IMPORT import_id SEMI 
      * is reduced.
      */
    protected abstract String Import(int line, int col, String arg0);

    /** Method called when the production:
      *   import_statement --> IMPORT STATIC import_id SEMI 
      * is reduced.
      */
    protected abstract String StaticImport(int line, int col, String arg0);

    /** Method called when the production:
      *   import_id --> multipart_id 
      * is reduced.
      */
    protected abstract String MultipartImportId(int line, int col, String arg0);

    /** Method called when the production:
      *   import_id --> multipart_id DOT STAR 
      * is reduced.
      */
    protected abstract String WildcardImportId(int line, int col, String arg0);

    /** Method called when the production:
      *   symbol_list --> symbol_list symbol 
      * is reduced.
      */
    protected abstract List<SymbolDeclarationNode> SymbolList(int line, int col, List<SymbolDeclarationNode> arg0, SymbolDeclarationNode arg1);

    /** Method called when the production:
      *   symbol_list --> symbol 
      * is reduced.
      */
    protected abstract List<SymbolDeclarationNode> SymbolListTerminator(int line, int col, SymbolDeclarationNode arg0);

    /** Method called when the production:
      *   symbol --> TERMINAL ID declares_symbol 
      * is reduced.
      */
    protected abstract SymbolDeclarationNode TerminalDeclaration(int line, int col, lr_runtime.Token<String> arg0, List<String> arg1);

    /** Method called when the production:
      *   symbol --> NON TERMINAL ID declares_symbol 
      * is reduced.
      */
    protected abstract SymbolDeclarationNode NonTerminalDeclaration(int line, int col, lr_runtime.Token<String> arg0, List<String> arg1);

    /** Method called when the production:
      *   symbol --> TERMINAL declares_symbol 
      * is reduced.
      */
    protected abstract SymbolDeclarationNode TerminalDeclaration(int line, int col, List<String> arg0);

    /** Method called when the production:
      *   symbol --> NON TERMINAL declares_symbol 
      * is reduced.
      */
    protected abstract SymbolDeclarationNode NonTerminalDeclaration(int line, int col, List<String> arg0);

    /** Method called when the production:
      *   declares_symbol --> symbol_dec_list SEMI 
      * is reduced.
      */
    protected abstract List<String> SymbolDeclaration(int line, int col, List<String> arg0);

    /** Method called when the production:
      *   symbol_dec_list --> symbol_dec_list COMMA identifier 
      * is reduced.
      */
    protected abstract List<String> SymbolDeclList(int line, int col, List<String> arg0, String arg1);

    /** Method called when the production:
      *   symbol_dec_list --> identifier 
      * is reduced.
      */
    protected abstract List<String> SymbolDeclListTerminator(int line, int col, String arg0);

    /** Method called when the production:
      *   start_spec --> START WITH identifier SEMI 
      * is reduced.
      */
    protected abstract String StartSpec(int line, int col, String arg0);

    /** Method called when the production:
      *   production_list --> production_list production 
      * is reduced.
      */
    protected abstract List<ProductionNode> ProductionList(int line, int col, List<ProductionNode> arg0, ProductionNode arg1);

    /** Method called when the production:
      *   production_list --> production 
      * is reduced.
      */
    protected abstract List<ProductionNode> ProductionListTerminator(int line, int col, ProductionNode arg0);

    /** Method called when the production:
      *   production --> identifier PRODUCES rhs_list SEMI 
      * is reduced.
      */
    protected abstract ProductionNode Production(int line, int col, String arg0, List<RightHandSide> arg1);

    /** Method called when the production:
      *   production_name_opt --> COLON identifier 
      * is reduced.
      */
    protected abstract String ProductionName(int line, int col, String arg0);

    /** Method called when the production:
      *   rhs_list --> rhs_list BAR rhs 
      * is reduced.
      */
    protected abstract List<RightHandSide> RhsList(int line, int col, List<RightHandSide> arg0, RightHandSide arg1);

    /** Method called when the production:
      *   rhs_list --> rhs 
      * is reduced.
      */
    protected abstract List<RightHandSide> RhsListTerminator(int line, int col, RightHandSide arg0);

    /** Method called when the production:
      *   rhs --> identifier_list production_name_opt 
      * is reduced.
      */
    protected abstract RightHandSide Rhs(int line, int col, List<String> arg0, String arg1);

    /** Method called when the production:
      *   identifier_list --> identifier_list identifier 
      * is reduced.
      */
    protected abstract List<String> IdentifierList(int line, int col, List<String> arg0, String arg1);

    /** Method called when the production:
      *   identifier_list --> 
      * is reduced.
      */
    protected abstract List<String> IdentifierListTerminator(int line, int col);

    /** Method called when the production:
      *   identifier --> ID 
      * is reduced.
      */
    protected abstract String IdIdentifier(int line, int col, lr_runtime.Token<String> arg0);

    /** Method called when the production:
      *   identifier --> WITH 
      * is reduced.
      */
    protected abstract String WithIdentifier(int line, int col);

    /** Method called when the production:
      *   identifier --> TERMINAL 
      * is reduced.
      */
    protected abstract String TerminalIdentifier(int line, int col);

    /** Method called when the production:
      *   identifier --> NON 
      * is reduced.
      */
    protected abstract String NonIdentifier(int line, int col);

    /** Method called when the production:
      *   identifier --> START 
      * is reduced.
      */
    protected abstract String StartIdentifier(int line, int col);

    /** Method called when the production:
      *   identifier --> LANG 
      * is reduced.
      */
    protected abstract String LanguageIdentifier(int line, int col);

    /** Method called when the production:
      *   identifier --> JAVA 
      * is reduced.
      */
    protected abstract String JavaIdentifier(int line, int col);

    /** Method called when the production:
      *   identifier --> CXX 
      * is reduced.
      */
    protected abstract String CxxIdentifier(int line, int col);

    /** Method called when the production:
      *   identifier --> TYPE 
      * is reduced.
      */
    protected abstract String TypeIdentifier(int line, int col);

    /** Method called when the production:
      *   multipart_id --> multipart_id DOT identifier 
      * is reduced.
      */
    protected abstract String MultipartId(int line, int col, String arg0, String arg1);

    /** Method called when the production:
      *   multipart_id --> identifier 
      * is reduced.
      */
    protected abstract String MultipartIdTerminator(int line, int col, String arg0);

    /** Method called when the production:
      *   java_type_id --> multipart_id 
      * is reduced.
      */
    protected abstract String MultipartTypeId(int line, int col, String arg0);

    /** Method called when the production:
      *   java_type_id --> java_type_id OPEN_S CLOSE_S 
      * is reduced.
      */
    protected abstract String ArrayTypeId(int line, int col, String arg0);

    /** Method called when the production:
      *   java_type_id --> multipart_id OPEN_A parameterized_type_list CLOSE_A 
      * is reduced.
      */
    protected abstract String ParameterizedTypeId(int line, int col, String arg0, String arg1);

    /** Method called when the production:
      *   parameterized_type_list --> parameterized_type_list COMMA parameterized_type 
      * is reduced.
      */
    protected abstract String ParameterizedTypeList(int line, int col, String arg0, String arg1);

    /** Method called when the production:
      *   parameterized_type_list --> parameterized_type 
      * is reduced.
      */
    protected abstract String ParameterizedTypeList(int line, int col, String arg0);

    /** Method called when the production:
      *   parameterized_type --> java_type_id 
      * is reduced.
      */
    protected abstract String TypeParamater(int line, int col, String arg0);

    /** Method called when the production:
      *   parameterized_type --> QUESTION EXTENDS java_type_id 
      * is reduced.
      */
    protected abstract String ExtendsTypeParameter(int line, int col, String arg0);

    /** Method called when the production:
      *   parameterized_type --> QUESTION SUPER java_type_id 
      * is reduced.
      */
    protected abstract String SuperTypeParameter(int line, int col, String arg0);

    /** Method called when the production:
      *   cxx_type_id --> cxx_unscoped_type_id 
      * is reduced.
      */
    protected abstract String CxxTypeId(int line, int col, String arg0);

    /** Method called when the production:
      *   cxx_type_id --> cxx_type_id COLON_COLON cxx_unscoped_type_id 
      * is reduced.
      */
    protected abstract String CxxScopedTypeId(int line, int col, String arg0, String arg1);

    /** Method called when the production:
      *   cxx_unscoped_type_id --> ID 
      * is reduced.
      */
    protected abstract String CxxTypeId(int line, int col, lr_runtime.Token<String> arg0);

    /** Method called when the production:
      *   cxx_unscoped_type_id --> cxx_unscoped_type_id OPEN_A cxx_param_type_list CLOSE_A 
      * is reduced.
      */
    protected abstract String CxxParameterizedTypeId(int line, int col, String arg0, String arg1);

    /** Method called when the production:
      *   cxx_unscoped_type_id --> cxx_unscoped_type_id STAR 
      * is reduced.
      */
    protected abstract String CxxPointerTypeId(int line, int col, String arg0);

    /** Method called when the production:
      *   cxx_unscoped_type_id --> cxx_unscoped_type_id AMPERSAND 
      * is reduced.
      */
    protected abstract String CxxReferenceTypeId(int line, int col, String arg0);

    /** Method called when the production:
      *   cxx_param_type_list --> cxx_type_id 
      * is reduced.
      */
    protected abstract String CxxTypeList(int line, int col, String arg0);

    /** Method called when the production:
      *   cxx_param_type_list --> cxx_param_type_list COMMA cxx_type_id 
      * is reduced.
      */
    protected abstract String CxxTypeList(int line, int col, String arg0, String arg1);

}
