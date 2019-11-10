package seedu.planner.logic.commands.util;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

//@@author 1nefootstep
/**
 * Explains how to use a command.
 */
public class HelpExplanation {
    private final String name;
    private final String explanation;
    private final String syntax;
    private final String example;

    public HelpExplanation(String name, String explanation, String syntax, String example) {
        requireAllNonNull(name, explanation, syntax, example);
        this.name = name;
        this.explanation = explanation;
        this.syntax = syntax;
        this.example = example;
    }

    public String getExample() {
        return example;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getName() {
        return name;
    }

    public String getSyntax() {
        return syntax;
    }

    @Override
    public String toString() {
        return name + ": " + explanation + " Syntax: " + syntax + " \n" + "Example:" + example;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HelpExplanation)) {
            return false;
        }
        HelpExplanation otherHelp = (HelpExplanation) other;
        return otherHelp.name.equals(name)
                && otherHelp.explanation.equals(explanation)
                && otherHelp.syntax.equals(syntax)
                && otherHelp.example.equals(example);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, explanation, syntax, example);
    }
}
