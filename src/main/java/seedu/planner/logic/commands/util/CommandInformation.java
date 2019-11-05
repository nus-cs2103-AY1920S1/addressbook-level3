package seedu.planner.logic.commands.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

public class CommandInformation {
    private final String command;
    private final String preamble;
    private final List<String> requiredPrefixWithDescriptionSingleTime;
    private final List<String> requiredPrefixWithDescriptionMultipleTime;
    private final List<String> optionalPrefixWithDescriptionSingleTime;
    private final List<String> optionalPrefixWithDescriptionMultipleTime;

    public CommandInformation(String command, String preamble,
                              List<String> requiredPrefixWithDescriptionSingleTime,
                              List<String> requiredPrefixWithDescriptionMultipleTime,
                              List<String> optionalPrefixWithDescriptionSingleTime,
                              List<String> optionalPrefixWithDescriptionMultipleTime) {
        requireAllNonNull(command, requiredPrefixWithDescriptionSingleTime,
                requiredPrefixWithDescriptionMultipleTime,
                optionalPrefixWithDescriptionSingleTime,
                optionalPrefixWithDescriptionMultipleTime);
        this.command = command;
        this.preamble = preamble;
        this.requiredPrefixWithDescriptionSingleTime = requiredPrefixWithDescriptionSingleTime;
        this.requiredPrefixWithDescriptionMultipleTime = requiredPrefixWithDescriptionMultipleTime;
        this.optionalPrefixWithDescriptionSingleTime = optionalPrefixWithDescriptionSingleTime;
        this.optionalPrefixWithDescriptionMultipleTime = optionalPrefixWithDescriptionMultipleTime;
    }

    public CommandInformation(String command,
                              List<String> requiredPrefixWithDescriptionSingleTime,
                              List<String> requiredPrefixWithDescriptionMultipleTime,
                              List<String> optionalPrefixWithDescriptionSingleTime,
                              List<String> optionalPrefixWithDescriptionMultipleTime) {
        this(command, null, requiredPrefixWithDescriptionSingleTime,
                requiredPrefixWithDescriptionMultipleTime,
                optionalPrefixWithDescriptionSingleTime,
                optionalPrefixWithDescriptionMultipleTime);
    }

    public String getCommand() {
        return command;
    }

    public Optional<String> getPreamble() {
        return Optional.ofNullable(preamble);
    }

    public boolean thereIsPreamble() {
        return (!(preamble == null));
    }

    /**
     * Returns a copy of {@code requiredPrefixWithDescriptionSingleTime}.
     */
    public List<String> getRequiredPrefixWithDescriptionSingleTime() {
        return new ArrayList<>(requiredPrefixWithDescriptionSingleTime);
    }

    public boolean thereIsRequiredPrefixWithDescriptionSingleTime() {
        return requiredPrefixWithDescriptionSingleTime.size() > 0;
    }

    /**
     * Returns a copy of {@code requiredPrefixWithDescriptionMultipleTime}.
     */
    public List<String> getRequiredPrefixWithDescriptionMultipleTime() {
        return new ArrayList<>(requiredPrefixWithDescriptionMultipleTime);
    }

    public boolean thereIsRequiredPrefixWithDescriptionMultipleTime() {
        return requiredPrefixWithDescriptionMultipleTime.size() > 0;
    }

    /**
     * Returns a copy of {@code optionalPrefixWithDescriptionSingleTime}.
     */
    public List<String> getOptionalPrefixWithDescriptionSingleTime() {
        return new ArrayList<>(optionalPrefixWithDescriptionSingleTime);
    }

    public boolean thereIsOptionalPrefixWithDescriptionSingleTime() {
        return optionalPrefixWithDescriptionSingleTime.size() > 0;
    }

    /**
     * Returns a copy of {@code optionalPrefixWithDescriptionMultipleTime}.
     */
    public List<String> getOptionalPrefixWithDescriptionMultipleTime() {
        return new ArrayList<>(optionalPrefixWithDescriptionMultipleTime);
    }

    public boolean thereIsOptionalPrefixWithDescriptionMultipleTime() {
        return optionalPrefixWithDescriptionMultipleTime.size() > 0;
    }
}
