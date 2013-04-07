/*
 * Main.java
 *
 * Created on January 23, 2008, 2:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package canonical_lr;

import java.util.*;
import java.io.*;

import lr_runtime.*;
import canonical_lr.tree.*;

import rysto.getopt.*;
import rysto.util.*;

/**
 *
 * @author rstone
 */
public class Main {

    /** Creates a new instance of Main */
    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Options options = new Options();
            
            GetOpt.parse(args, options);
            
            File inputFile = new File(options.getInputFile());
            File outputDir = new File(options.getOutputDir());
            
            createParser(inputFile, outputDir, options);
        } catch(InvalidConversionException e) {
            System.err.println("Option " + e.getOptionName() + " given value " + e.getOptionValue() + " but expected type " + e.getExpectedType());
            System.exit(-1);
        } catch(NoSuchOptionException e) {
            System.err.println("Invalid option " + e.getOptionName());
            System.exit(-1);
        } catch(NoValueException e) {
            System.err.println("Option " + e.getOptionName() + " not passed a value.");
            System.exit(-1);
        } catch(RequiredOptionUnspecifiedException e) {
            System.err.println("Required option " + e.getValidName() + " not specified.");
            System.exit(-1);
        } catch(ParserGeneratorException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    public static void createParser(File inputFile, File outputDir, Options options) throws ParserGeneratorException {
        TargetLanguage lang = options.getTargetLanguage();
        long start = System.currentTimeMillis();
        
        if(!inputFile.exists()) {
            String pwd;
            try {
                pwd = new File(".").getCanonicalPath();
                throw new ParserGeneratorException("Input file " + inputFile.getPath() + " does not exist(pwd=" + pwd + ")");
            } catch(IOException e) {
                throw new Error(e);
            }
        }
        
        if(!outputDir.isDirectory()) {
            throw new ParserGeneratorException("Output dir " + outputDir.getPath() + " must be a directory.");
        }

        File parserFile = new File(outputDir, lang.getParserFileName(options.getParserName()));
        File symbolFile = new File(outputDir, lang.getSymbolFileName(options.getSymbolName()));

        if(inputFile.lastModified() <= parserFile.lastModified() &&
                inputFile.lastModified() <= symbolFile.lastModified() &&
                !options.isForce())
        {
            System.err.println(parserFile.getName() + " and " + symbolFile.getName() + " are up to date");
            return;
        }

        Scanner lex;
        try {
            lex = new Scanner(new BufferedReader(new FileReader(inputFile)));
        } catch(IOException e) {
            throw new ParserGeneratorException(e);
        }
        
        LrParser p = new SpecParser();
        GrammarSpecification spec = (GrammarSpecification)p.parse(lex, new SymbolFactory());
        
        if(spec == null) {
            throw new ParserGeneratorException("Parse failed.");
        }

        Vocabulary v = new Vocabulary(spec);
        ProductionFactory prodFactory = new ProductionFactory(spec, v);
        ItemFactory itemFactory = new ItemFactory(prodFactory);
        FirstCalculator first = new FirstCalculator(v, prodFactory);
        ClosureFactory closureFactory = new ClosureFactory(prodFactory, itemFactory, first);
        GotoFactory gotoFactory = new GotoFactory(closureFactory, itemFactory);

        List<ItemClosure> itemList = lr1Items(prodFactory, itemFactory, v, closureFactory, gotoFactory);
        long now = System.currentTimeMillis();
        long diff = now - start;
        System.out.println("Took "  + (diff/1000) + "." + (diff % 1000) + " seconds to compute LR(1) items.");

        if(options.isLalr()) {
            itemList = computeLalr(prodFactory, itemFactory, v, closureFactory, gotoFactory, itemList);
            diff = System.currentTimeMillis() - now;
            System.out.println("Took "  + (diff/1000) + "." + (diff % 1000) + " seconds to compute LALR(1) items.");
        }
        
        gotoFactory.setCountComputes(true);
        
        Map<ItemClosure, Integer> itemNum = new HashMap<ItemClosure, Integer>();

        int i;
        for(i = 0; i < itemList.size(); i++) {
            itemNum.put(itemList.get(i), i);
        }

        Bijection<String, Integer> symbolNum = UtilExtensions.makeBijection(new LinkedHashMap<String, Integer>(), new LinkedHashMap<Integer, String>());
        Map<Production, Integer> productionNum = new HashMap<Production, Integer>();

        i = 0;
        for(String sym : v.terminals) {
            symbolNum.put(sym, i);

            i++;
        }

        for(String sym : v.nonTerminals) {
            symbolNum.put(sym, i);

            i++;
        }

        i = 0;
        for(Production prod : prodFactory.productions) {
            productionNum.put(prod, i);
            i++;
        }

        ParseTable parseTable = new ParseTable(itemList);
        ActionFactory actionFactory = new ActionFactory();
        
        MultiMap<ParseConflictException, String> conflicts = new MultiMap<ParseConflictException, String>();

        for(i = 0; i < itemList.size(); i++) {
            ItemClosure itemSet = itemList.get(i);

            for(Item item : itemSet) {
                try {
                    if(item.equals(itemFactory.acceptItem)) {
                        parseTable.addAction(i, symbolNum.get(item.lookahead), actionFactory.makeAccept());

                    } else if(item.isFinal()) {
                        parseTable.addAction(i, symbolNum.get(item.lookahead),
                                actionFactory.makeReduce(productionNum.get(item.production)));

                    } else {
                        String symbol = item.symbolAfterDot();

                        if(v.isTerminal(symbol)) {
                            ItemClosure goTo = gotoFactory.gotoSet(itemSet, symbol);

                            if(!goTo.isEmpty()) {
                                Integer end = itemNum.get(goTo);
                                if(end == null) {
                                    System.err.println(itemSet.toString());
                                    System.err.println(symbol);
                                    throw new Error(goTo.toString());
                                }
                                parseTable.addAction(i, symbolNum.get(symbol), actionFactory.makeShift(end));
                            }
                        }
                    }


                } catch(ParseConflictException e) {
                    conflicts.put(e, symbolNum.getInverse(e.lookahead));
                }
            }

            for(String symbol : v.nonTerminals) {
                ItemClosure goTo = gotoFactory.gotoSet(itemSet, symbol);

                if(!goTo.isEmpty()) {
                    Integer end = itemNum.get(goTo);
                    if(end == null) {
                        throw new Error(goTo.toString());
                    }
                    parseTable.addGoto(i, symbolNum.get(symbol), end);
                }
            }
        }
        
        if(conflicts.size() > 0) {
            Map<Integer,  List<Integer>> paths = ShortestPath.shortestPath(parseTable, prodFactory, itemList.size());
            for(Map.Entry<ParseConflictException, Set<String>> entry : conflicts.entrySet()) {
                System.err.println("Error in state " + entry.getKey().state);
                System.err.println("Lookahead: " + entry.getValue());
                System.err.println("\tActions: " + entry.getKey().old.toString(prodFactory.productions));
                System.err.println("\t         " + entry.getKey().action.toString(prodFactory.productions));
                System.err.print("\tShortest path to conflict:\n\t");
                List<Integer> path = paths.get(entry.getKey().state);
                for(Integer sym : path) {
                    System.err.print(symbolNum.getInverse(sym));
                    System.err.print(" ");
                }
                
                System.err.println();
            }
            
            throw new ParserGeneratorException(conflicts.size() + " conflicts found");
        }

        LanguageSpec langSpec = spec.languageSpecs.get(lang);

        if(langSpec == null) {
            throw new ParserGeneratorException("Grammar has no definitions for language " + lang.name());
        }

        ParserWriter writer = lang.getParserWriter(langSpec, parseTable, symbolNum, prodFactory, v, options.getSymbolName(), options.getParserName());
        try {
            writer.outputParser(parserFile);
            writer.outputSymbols(symbolFile);
        } catch(IOException e) {
            throw new ParserGeneratorException(e);
        }

        long end = System.currentTimeMillis();

        System.out.println("Had to compute " + gotoFactory.getComputeCount() + " gotos and " + closureFactory.getComputeCount() + " closures.");
        System.out.println("It took " + ((end - start)/1000) + "." + ((end - start)%1000) + " seconds");
    }

    public static List<ItemClosure> lr1Items(ProductionFactory prodFactory,
            ItemFactory itemFactory, Vocabulary v,
            ClosureFactory closureFactory, GotoFactory gotoFactory)
    {
        Set<ItemClosure> C = new HashSet<ItemClosure>();

        final ItemClosure firstSet = closureFactory.closure(new ItemClosure(
                Collections.singleton(itemFactory.getItem(prodFactory.startProduction, 
                0, ProductionFactory.EOF_DELIMITER))));

        C.add(firstSet);

        Queue<ItemClosure> queue = new LinkedList<ItemClosure>();
        queue.add(firstSet);

        while(!queue.isEmpty()) {

            ItemClosure i = queue.remove();
            for(String symbol : i.symbolsAfterDot()) {
                ItemClosure goTo = gotoFactory.gotoSet(i, symbol);

                if(!goTo.isEmpty() && !C.contains(goTo)) {
                    queue.add(goTo);
                    C.add(goTo);
                }
            }
        }

        System.out.print(prodFactory.productions.size() + " productions, ");
        System.out.print(v.nonTerminals.size() + " non-terminals and ");
        System.out.println(v.terminals.size() + " terminals");
        System.out.println("Giving a total of " + C.size() + " unique parse states");

        /* Note that it's critical that firstSet -- our start state -- be at index 0 */
        List<ItemClosure> itemList = new ArrayList<ItemClosure>(C.size());
        itemList.add(firstSet);
        C.remove(firstSet);
        itemList.addAll(C);

        return itemList;
    }

    public static List<ItemClosure> computeLalr(ProductionFactory prodFactory,
            ItemFactory itemFactory, Vocabulary v,
            ClosureFactory closureFactory, GotoFactory gotoFactory, List<ItemClosure> lr1Items)
    {
        MultiMap<MemoizedHashSet<Lr0Item>, ItemClosure> mergeableStates = new MultiMap<MemoizedHashSet<Lr0Item>, ItemClosure>();
        MemoizedHashSet<Lr0Item> initialState = new MemoizedHashSet<Lr0Item>();
        Iterator<ItemClosure> it = lr1Items.iterator();
        ItemClosure closure = it.next();
        Map<ItemClosure, ItemClosure> mergeMap = new HashMap<ItemClosure, ItemClosure>();
        MultiMap<ItemClosure, ItemClosure> mergeMultiMap = new MultiMap<ItemClosure, ItemClosure>();

        for(Item i : closure) {
            initialState.add(new Lr0Item(i));
        }
        mergeableStates.put(initialState,  closure);

        while(it.hasNext()) {
            closure = it.next();
            MemoizedHashSet<Lr0Item> set = new MemoizedHashSet<Lr0Item>();

            for(Item i : closure) {
                set.add(new Lr0Item(i));
            }

            mergeableStates.put(set, closure);
        }

        List<ItemClosure> lalr = new ArrayList<ItemClosure>(mergeableStates.size());

        for(Map.Entry<MemoizedHashSet<Lr0Item>, Set<ItemClosure>> entry : mergeableStates.entrySet()) {
            Set<ItemClosure> set = entry.getValue();
            ItemClosure lalrState = new ItemClosure();

            for(ItemClosure c : set) {
                lalrState.addAll(c);
            }

            lalrState = closureFactory.closure(lalrState);
            if(entry.getKey().equals(initialState)) {
                lalr.add(0, lalrState);
            } else {
                lalr.add(lalrState);
            }

            //System.out.println("Merged the following sets:\n");
            for(ItemClosure c : set) {
                mergeMap.put(c, lalrState);
                mergeMultiMap.put(lalrState, c);
                /*System.out.println("\t" + c.toString());
                System.out.println("****\n");*/
            }
        }
        System.out.println("Reduced to " + lalr.size() + " unique LALR(1) states");

        int notMapped = 0;
        for(ItemClosure c : lr1Items) {
            if(!mergeMap.containsKey(c)) {
                notMapped++;
            }
        }

        //System.err.println("States are " + mergeMultiMap.keySet());

        //System.out.println(notMapped + " states were not mapped to a new one.");

        closureFactory.setMergeMap(mergeMap);
        gotoFactory.setMergeMap(mergeMultiMap);

        return lalr;
    }
}
