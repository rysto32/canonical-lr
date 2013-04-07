/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr.ant_task;

import java.io.*;
import canonical_lr.*;
import org.apache.tools.ant.*;


/**
 *
 * @author rstone
 */
public class AntTask extends Task {
    private final Options options = new Options();
    
    private File inputFile;
    private File outputDir;

    public void setTargetLanguage(TargetLanguage targetLanguage) {
        options.setTargetLanguage(targetLanguage);
    }

    public void setSymbolName(String symbolName) {
        options.setSymbolName(symbolName);
    }

    public void setParserName(String parserName) {
        options.setParserName(parserName);
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public TargetLanguage getTargetLanguage() {
        return options.getTargetLanguage();
    }

    public String getSymbolName() {
        return options.getSymbolName();
    }

    public String getParserName() {
        return options.getParserName();
    }

    public String getOutputDir() {
        return options.getOutputDir();
    }

    public String getInputFile() {
        return options.getInputFile();
    }    

    @Override
    public void execute() throws BuildException {
        if(inputFile == null) {
            throw new BuildException("Input file not specified");
        }
        
        if(outputDir == null) {
            throw new BuildException("Output directory not specified.");
        }
        
        try {
            canonical_lr.Main.createParser(inputFile, outputDir, options);
        } catch(ParserGeneratorException e) {
            throw new BuildException(e.getMessage());
        }
    }
}
