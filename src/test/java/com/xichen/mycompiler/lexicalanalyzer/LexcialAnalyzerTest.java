package com.xichen.mycompiler.lexicalanalyzer;


import java.io.IOException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class LexcialAnalyzerTest {

    @Test
    public void testPrintTokens() throws IOException {
        String file = "C://Users/xichen/workspace/mycomplier/src/test/java/com/xichen/mycompiler/lexicalanalyzer/lexer00.txt";
        var analyzer = new LexcialAnalyzer(file);
        Token token = analyzer.getToken();
        while (token.getType() != TokenType.EOF) {
            System.out.println(token.toString());
            token = analyzer.getToken();
        }
        
    }
}
