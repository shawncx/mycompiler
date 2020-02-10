package com.xichen.mycompiler.lexicalanalyzer;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LexcialAnalyzer {
    private BufferedReader reader;
    private int lineNumber = 1;
    private int columnNumber = 1;
    private char nextChar;
    private Token nextToken;

    private List<String> errors;

    public LexcialAnalyzer(String source) throws IOException {
        reader = new BufferedReader(new FileReader(source));
        errors = new ArrayList<>();
        moveToNextCharSkipNewLine();
    }

    public Token peek() {
        if (nextToken == null) {
            nextToken = getToken();
        }
        return nextToken;
    }

    public Token getToken() {
        if (nextToken != null) {
            Token token = nextToken;
            nextToken = null;
            return token;
        }

        try {
            while (Character.isWhitespace(nextChar)) {
                moveToNextChar();
            }

            if (nextChar == -1) {
                return new Token(TokenType.EOF, new TokenAttribute(), lineNumber, columnNumber);
            } else if (Character.isLetter(nextChar)) {
                return getIdentifierOrReservedWordToken();
            } else if (Character.isDigit(nextChar)) {
                return getNumberToken();
            } else if (nextChar == '\'') {
                return getCharToken();
            } else if (nextChar == '\"') {
                return getStringToken();
            } else if (TokenTypeUtilies.Instance.isPunctuation(nextChar)) {
                return new Token(TokenTypeUtilies.Instance.getPunctuation(nextChar), new TokenAttribute(), lineNumber, columnNumber);
            } else {
                return getOperatorToken();
            }
        } catch (IOException e) {
            return new Token(TokenType.EOF, new TokenAttribute(), lineNumber, columnNumber);
        }
        
    }

    private Token getIdentifierOrReservedWordToken() throws IOException {
        int stColNum = columnNumber;
        StringBuilder word = new StringBuilder().append(nextChar);
        moveToNextChar();
        while (Character.isLetterOrDigit(nextChar)) {
            word.append(nextChar);
            moveToNextChar();
        }

        String wordStr = word.toString();
        if (TokenTypeUtilies.Instance.isKeyword(wordStr)) {
            return new Token(TokenTypeUtilies.Instance.getKeyword(word.toString()), new TokenAttribute(), lineNumber, stColNum);
        } else if (TokenTypeUtilies.Instance.isBoolean(wordStr)) {
            return new Token(TokenType.BOOLEAN_CONST, new TokenAttribute(Boolean.valueOf(wordStr)), lineNumber, stColNum);
        } else {
            return new Token(TokenType.ID, new TokenAttribute(wordStr, null), lineNumber, stColNum);
        }
    }

    private Token getNumberToken() throws IOException {
        int stColNum = columnNumber;
        boolean hasDot = false;
        StringBuilder word = new StringBuilder().append(nextChar);
        moveToNextChar();
        while (Character.isDigit(nextChar)) {
            word.append(nextChar);
            moveToNextChar();
        }
        if (nextChar == '.') {
            // float case
            hasDot = true;
            word.append(nextChar);
            moveToNextChar();
            while (Character.isDigit(nextChar)) {
                word.append(nextChar);
                moveToNextChar();
            }
        }

        if (!Character.isWhitespace(nextChar)) {
            while (!Character.isWhitespace(nextChar)) {
                moveToNextChar();
            }
            return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, stColNum);
        } else if (hasDot) {
            return new Token(TokenType.FLOAT_CONST, new TokenAttribute(Float.parseFloat(word.toString())), lineNumber, stColNum);
        } else {
            return new Token(TokenType.INT_CONST, new TokenAttribute(Integer.parseInt(word.toString())), lineNumber, stColNum);
        }
    }

    private Token getCharToken() throws IOException {
        int stColNum = columnNumber;
        char c = (char)nextChar;
        moveToNextChar();
        if (nextChar != '\'') {
            while (!Character.isWhitespace(nextChar)) {
                moveToNextChar();
            }
            return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, stColNum);
        } else {
            moveToNextChar();
            return new Token(TokenType.CHAR_CONST, new TokenAttribute(c), lineNumber, stColNum + 1);
        }
    }

    private Token getStringToken() throws IOException {
        int stColNum = columnNumber;
        StringBuilder word = new StringBuilder().append(nextChar);
        moveToNextChar();
        while (nextChar != '"' && nextChar != '\n' && nextChar != '\r') {
            word.append(nextChar);
            moveToNextChar();
        }
        if (nextChar != '\n' && nextChar != '\r') {
            return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, stColNum);
        } else {
            return new Token(TokenType.STRING_CONST, new TokenAttribute(null, word.toString()), lineNumber, stColNum + 1);
        }
    }

    private Token getOperatorToken() throws IOException {
        int stColNum = columnNumber;
        switch (nextChar) {
        case '&':
            moveToNextChar();
            if (nextChar == '&') {
                moveToNextChar();
                return new Token(TokenType.AND, new TokenAttribute(), lineNumber, stColNum);
            } else {
                return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, stColNum);
            }
        case '|':
            moveToNextChar();
            if (nextChar == '|') {
                moveToNextChar();
                return new Token(TokenType.OR, new TokenAttribute(), lineNumber, stColNum);
            } else {
                return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, stColNum);
            }
        case '=':
            moveToNextChar();
            if (nextChar == '=') {
                moveToNextChar();
                return new Token(TokenType.EQ, new TokenAttribute(), lineNumber, stColNum);
            } else {
                return new Token(TokenType.ASSIGN, new TokenAttribute(), lineNumber, stColNum);
            }
        case '!':
            moveToNextChar();
            if (nextChar == '=') {
                moveToNextChar();
                return new Token(TokenType.NEQ, new TokenAttribute(), lineNumber, stColNum);
            } else {
                return new Token(TokenType.NOT, new TokenAttribute(), lineNumber, stColNum);
            }
        case '<':
            moveToNextChar();
            if (nextChar == '=') {
                moveToNextChar();
                return new Token(TokenType.LT_EQ, new TokenAttribute(), lineNumber, stColNum);
            } else {
                return new Token(TokenType.LT, new TokenAttribute(), lineNumber, stColNum);
            }
        case '>':
            moveToNextChar();
            if (nextChar == '=') {
                moveToNextChar();
                return new Token(TokenType.RT_EQ, new TokenAttribute(), lineNumber, stColNum);
            } else {
                return new Token(TokenType.RT, new TokenAttribute(), lineNumber, stColNum);
            }
        case '+':
            moveToNextChar();
            return new Token(TokenType.PLUS, new TokenAttribute(), lineNumber, stColNum);
        case '-':
            moveToNextChar();
            return new Token(TokenType.MINUS, new TokenAttribute(), lineNumber, stColNum);

        case '*':
            moveToNextChar();
            return new Token(TokenType.TIMES, new TokenAttribute(), lineNumber, stColNum);
        case '/':
            moveToNextChar();
            return new Token(TokenType.DIV, new TokenAttribute(), lineNumber, stColNum);
        case '%':
            moveToNextChar();
            return new Token(TokenType.MOD, new TokenAttribute(), lineNumber, stColNum);
        }
        return new Token(TokenType.UNKNOWN, new TokenAttribute(), lineNumber, stColNum);
    }

    private void moveToNextChar() throws IOException {
        moveToNextChar(false);
    }

    private void moveToNextCharSkipNewLine() throws IOException {
        moveToNextChar(true);
    }

    private void moveToNextChar(boolean skipNewLine) throws IOException {
        try {
            int code = reader.read();
            if (code == -1) {
                throw new EOFException();
            }
            nextChar = (char)code;
            if (skipNewLine) {
                while (nextChar == '\n' || nextChar == '\r') {
                    lineNumber++;
                    columnNumber = 1;
                    if (nextChar == '\n') {
                        nextChar = (char)reader.read();
                    } else if (nextChar == '\r') {
                        nextChar = (char)reader.read();
                        if (nextChar == '\n') {
                            // \r\n create one new line, do not need increase line number
                            nextChar = (char)reader.read();
                        }
                    }
                }
            }
            if (nextChar == '\t') {
                columnNumber += 3;
            } else {
                columnNumber++;
            }
        } catch (IOException e) {
            this.errors.add(e.getMessage());
            throw e;
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}