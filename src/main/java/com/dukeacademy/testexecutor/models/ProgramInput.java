package com.dukeacademy.testexecutor.models;

import static java.util.Objects.requireNonNull;

/**
 * Represents input to be fed into a program executed by the application.
 */
public class ProgramInput {
    private String input;

    public ProgramInput(String input) {
        requireNonNull(input);
        this.input = input;
    }

    public String getInput() {
        return this.input;
    }

    /**
     * Returns true the object is another instance of ProgramInput with the same input.
     * @param object the object to be compared
     * @return true if they are equal.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof ProgramInput) {
            ProgramInput other = (ProgramInput) object;
            return other.input.equals(this.input);
        } else {
            return false;
        }
    }
}
