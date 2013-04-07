/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package canonical_lr;


import java.io.*;

/**
 *
 * @author rstone
 */
public interface ParserWriter {
    public void outputParser(File parserFile) throws IOException, ParserGeneratorException;
    public void outputSymbols(File symbolFile)  throws IOException, ParserGeneratorException;
}
