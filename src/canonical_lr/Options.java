/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr;

import rysto.getopt.*;

/**
 *
 * @author rstone
 */
public class Options {
    private String inputFile;
    private String outputDir;
    private String parserName = "Parser";
    private String symbolName = "Sym";
    private TargetLanguage targetLanguage = TargetLanguage.java;
    private boolean lalr = false;
    private boolean force = false;

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getParserName() {
        return parserName;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public TargetLanguage getTargetLanguage() {
        return targetLanguage;
    }

    public boolean isLalr() {
        return lalr;
    }

    public boolean isForce() {
        return force;
    }

    @Option(shortOption='o', longOption="outputDir", required=true)
    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    @Option(shortOption='p', longOption="parserName")
    public void setParserName(String parserName) {
        this.parserName = parserName;
    }

    @Option(shortOption='s', longOption="symbolName")
    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    @Option(shortOption='l', longOption="language")
    public void setTargetLanguage(TargetLanguage targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    @Option(shortOption='i', longOption="inputFile", required=true)
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    @Option(longOption="lalr")
    public void setLalr(boolean lalr) {
        this.lalr = lalr;
    }

    @Option(shortOption='f', longOption="force")
    public void setForce(boolean f) {
        this.force = f;
    }

}
