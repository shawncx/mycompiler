package com.xichen.mycompiler.lexicalanalyzer;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TokenAttribute {
	private int intVal; // int value of the token
	private float floatVal; // float value of the token
	private char charVal; // char value of the token
    private boolean booleanVal; // boolean value of the token
    private String stringVal; // string value of the token
    private String id; // id of the token

    public TokenAttribute() {
    }

    public TokenAttribute(String id, String stringVal) {
        this.id = id;
        this.stringVal = stringVal;
    }

    public TokenAttribute(float floatVal) {
        this.floatVal = floatVal;
    }

    public TokenAttribute(boolean val) {
        this.booleanVal = val;
    }

    public TokenAttribute(int intVal) {
        this.intVal = intVal;
    }

    public TokenAttribute(char charVal) {
        this.charVal = charVal;
    }

    public int getIntVal() {
        return this.intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public float getFloatVal() {
        return this.floatVal;
    }

    public void setFloatVal(float floatVal) {
        this.floatVal = floatVal;
    }

    public char getCharVal() {
        return this.charVal;
    }

    public void setCharVal(char charVal) {
        this.charVal = charVal;
    }

    public boolean isBooleanVal() {
        return this.booleanVal;
    }

    public boolean getBooleanVal() {
        return this.booleanVal;
    }

    public void setBooleanVal(boolean booleanVal) {
        this.booleanVal = booleanVal;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStringVal() {
        return this.stringVal;
    }

    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}