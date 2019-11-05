package mams.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Stores a set of {@code Option}'s. All options stored must be unique.
 */
public class OptionsSet {
    /** Set of options **/
    private Set<Option> optionsSet = new HashSet<>();

    /**
     * Adds an {@code Option} to the the set.
     * @param opt option to be added.
     */
    public void add(Option opt) {
        optionsSet.add(opt);
    }

    /**
     * Checks if an option is present in the set.
     * @param opt option to be checked
     * @return true if present, else false.
     */
    public boolean isOptionPresent(Option opt) {
        return optionsSet.contains(opt);
    }

    /**
     * Checks if the OptionsSet object is empty, ie. contains no options.
     * @return
     */
    public boolean isEmpty() {
        return optionsSet.isEmpty();
    }

    /**
     * Checks if any of the options in the supplied array {@code options}
     * is present in the current {@code OptionsSet} object.
     * @param options variable length argument or array of options to check against
     * @return true is at least one option is present, false otherwise
     */
    public boolean isAnyOptionPresent(Option... options) {
        boolean present = false;
        for (Option opt : options) {
            if (optionsSet.contains(opt)) {
                present = true;
            }
        }
        return present;
    }

    /**
     * Checks if none of the options in the supplied array {@code options}
     * are present in the current {@code OptionsSet} object.
     * @param options variable length argument or array of options to check against
     * @return true is at least none of the specified options are present, false otherwise
     */
    public boolean areAllTheseOptionAbsent(Option... options) {
        return !isAnyOptionPresent(options);
    }

    /**
     * Checks if all the options in the supplied array {@code options}
     * are present in the current {@code OptionsSet} object.
     * @param options variable length argument or array of options to check against
     * @return true if all options in {@code options} is present, else false.
     */
    public boolean areAllTheseOptionsPresent(Option... options) {
        List<Option> optionsList = Arrays.asList(options);
        return optionsSet.containsAll(optionsList);
    }

    /**
     * Returns all the options in the OptionsSet that do not match
     * the supplied options.
     * @param options options to compare against
     * @return list of options that do not match the supplied options.
     */
    public List<Option> getAllOtherOptions(Option... options) {
        Set<Option> recognizedOptions = Set.of(options);
        ArrayList<Option> unrecognizedOptions = new ArrayList<>();

        for (Option opt : optionsSet) {
            if (!recognizedOptions.contains(opt)) {
                unrecognizedOptions.add(new Option(opt));
            }
        }
        return unrecognizedOptions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionsSet);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OptionsSet)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        OptionsSet otherOpt = (OptionsSet) obj;
        return otherOpt.optionsSet.equals(this.optionsSet);
    }
}
