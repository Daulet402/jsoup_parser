package techSolutions.app;

import techSolutions.parser.GroupParser;
import techSolutions.parser.IParser;
import techSolutions.parser.LosstParser;

import java.io.IOException;

public class ParserApp {
    public static void main(String[] args) {

        IParser losstParser = new GroupParser();
        try {
            losstParser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}