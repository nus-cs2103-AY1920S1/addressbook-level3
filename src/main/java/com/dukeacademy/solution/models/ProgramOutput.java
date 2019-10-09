package com.dukeacademy.solution.models;

public class ProgramOutput {
    private String output;

    public ProgramOutput() {
        this.output = "";
    }

    public String getOutput() {
        return this.output;
    }

    public void append(String output) {
        this.output += output;
    }

    public void appendNewLine() {
        this.output += "\n";
    }
}
