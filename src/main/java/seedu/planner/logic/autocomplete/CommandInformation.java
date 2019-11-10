package seedu.planner.logic.autocomplete;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

//@@author 1nefootstep
/**
 * CommandInformation represents the format of a command.
 */
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
        if (preamble == null) {
            this.preamble = null;
        } else {
            this.preamble = " " + preamble;
        }
        this.requiredPrefixWithDescriptionSingleTime =
                addSpaceAtStartOfEveryElement(requiredPrefixWithDescriptionSingleTime);
        this.requiredPrefixWithDescriptionMultipleTime =
                addSpaceAtStartOfEveryElement(requiredPrefixWithDescriptionMultipleTime);
        this.optionalPrefixWithDescriptionSingleTime =
                addSpaceAtStartOfEveryElement(optionalPrefixWithDescriptionSingleTime);
        this.optionalPrefixWithDescriptionMultipleTime =
                addSpaceAtStartOfEveryElement(optionalPrefixWithDescriptionMultipleTime);
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

    public CommandInformation(String command, String preamble) {
        this(command, preamble, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public CommandInformation(String command) {
        this(command, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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

    /**
     * Returns a list of the original list but with every element preceding with an extra space.
     */
    private List<String> addSpaceAtStartOfEveryElement(List<String> ls) {
        List<String> copiedList = new ArrayList<>(ls);
        ListIterator<String> iterator = copiedList.listIterator();
        while (iterator.hasNext()) {
            String curr = iterator.next();
            iterator.set(" " + curr);
        }
        return copiedList;
    }
}
