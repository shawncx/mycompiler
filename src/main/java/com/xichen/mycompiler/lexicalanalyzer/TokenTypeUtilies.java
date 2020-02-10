package com.xichen.mycompiler.lexicalanalyzer;

import java.util.HashMap;
import java.util.Map;

public enum TokenTypeUtilies {

    Instance;

    private Map<String, TokenType> keywordTokens;
    private Map<Character, TokenType> punctuationTokens;
    private Map<String, TokenType> operatorTokens;

    private TokenTypeUtilies() {
        keywordTokens = new HashMap<>();
        keywordTokens.put("int", TokenType.INT);
        keywordTokens.put("float", TokenType.FLOAT);
        keywordTokens.put("char", TokenType.CHAR);
        keywordTokens.put("boolean", TokenType.BOOLEAN);
        keywordTokens.put("if", TokenType.IF);
        keywordTokens.put("else", TokenType.ELSE);
        keywordTokens.put("while", TokenType.WHILE);
        keywordTokens.put("main", TokenType.MAIN);

        punctuationTokens = new HashMap<>();
        punctuationTokens.put('(', TokenType.LPAREN);
        punctuationTokens.put(')', TokenType.RPAREN);
        punctuationTokens.put('[', TokenType.LBRACKET);
        punctuationTokens.put(']', TokenType.RBRACKET);
        punctuationTokens.put('{', TokenType.LBRACE);
        punctuationTokens.put('}', TokenType.RBRACE);
        punctuationTokens.put(';', TokenType.SEMI);
        punctuationTokens.put(',', TokenType.COMMA);
        punctuationTokens.put('=', TokenType.ASSIGN);
        punctuationTokens.put('-', TokenType.NEGATIVE);
        punctuationTokens.put('!', TokenType.NOT);

        operatorTokens = new HashMap<>();
        operatorTokens.put("&&", TokenType.AND);
        operatorTokens.put("||", TokenType.OR);
        operatorTokens.put("==", TokenType.EQ);
        operatorTokens.put("!=", TokenType.NEQ);
        operatorTokens.put("<", TokenType.LT);
        operatorTokens.put(">", TokenType.RT);
        operatorTokens.put("<=", TokenType.LT_EQ);
        operatorTokens.put(">=", TokenType.RT_EQ);
        operatorTokens.put("+", TokenType.PLUS);
        operatorTokens.put("-", TokenType.MINUS);
        operatorTokens.put("*", TokenType.TIMES);
        operatorTokens.put("/", TokenType.DIV);
        operatorTokens.put("%", TokenType.MOD);
    }

    public boolean isBoolean(String tokenStr) {
        return "true".equals(tokenStr) || "false".equals(tokenStr);
    }

    public TokenType getKeyword(String tokenStr) {
        return keywordTokens.getOrDefault(tokenStr, null);
    }

    public boolean isKeyword(String tokenStr) {
        return keywordTokens.containsKey(tokenStr);
    }
    
    public TokenType getPunctuation(int charNum) {
        return getPunctuation((char)charNum);
    }

    public TokenType getPunctuation(char tokenChar) {
        return punctuationTokens.getOrDefault(tokenChar, null);
    }

    public boolean isPunctuation(int charNum) {
        return isPunctuation((char)charNum);
    }

    public boolean isPunctuation(char tokenChar) {
        return punctuationTokens.containsKey(tokenChar);
    }

    public TokenType getOperator(String tokenStr) {
        return operatorTokens.getOrDefault(tokenStr, null);
    }

    public boolean isOperator(String tokenStr) {
        return operatorTokens.containsKey(tokenStr);
    }
}