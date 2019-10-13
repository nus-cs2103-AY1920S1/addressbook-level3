package seedu.exercise.model.exercise;

import static seedu.exercise.logic.parser.CliSyntax.setPrefixesSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.exercise.logic.parser.Prefix;

/**
 * Helps to keep track of all the existing prefixes and full names for both default and custom properties.
 * It also helps to keep track of all the existing custom properties that have been defined by the user.
 */
public class PropertyManager {
    private static final List<CustomProperty> CUSTOM_PROPERTIES = new ArrayList<>();
    private final Set<Prefix> prefixes = new HashSet<>();
    private final Set<String> fullNames = new HashSet<>();

    /**
     * Initialises an instance of {@code PropertyManager} object.
     *
     * @param prefixes         the set of prefixes to be added to the {@code PropertyManager}
     * @param fullNames        the set of full names to be added to the {@code PropertyManager}
     * @param customProperties the set of custom properties to be added to the {@code PropertyManager}
     */
    public PropertyManager(Set<Prefix> prefixes, Set<String> fullNames, List<CustomProperty> customProperties) {
        setPrefixes(prefixes);
        setFullNames(fullNames);
        setCustomProperties(customProperties);
    }

    /**
     * Returns an immutable custom properties list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public static List<CustomProperty> getCustomProperties() {
        return Collections.unmodifiableList(CUSTOM_PROPERTIES);
    }

    /**
     * Adds in all the custom properties that are present in the given {@code List<CustomProperty> customProperties}
     * into the PropertyManager only if there are no custom properties present.
     *
     * @param customProperties the custom properties to be added
     */
    public void setCustomProperties(List<CustomProperty> customProperties) {
        if (CUSTOM_PROPERTIES.isEmpty()) {
            CUSTOM_PROPERTIES.addAll(customProperties);
        }
    }

    /**
     * Checks if the prefix has already been used by a property.
     */
    public boolean isPrefixPresent(Prefix prefix) {
        return prefixes.contains(prefix);
    }

    /**
     * Checks if the full name has already been used by a property.
     */
    public boolean isFullNamePresent(String fullName) {
        return fullNames.contains(fullName);
    }

    /**
     * Adds the prefix for a newly defined custom property.
     */
    public void addPrefix(Prefix prefix) {
        prefixes.add(prefix);
    }

    /**
     * Adds the full name for a newly defined custom property.
     */
    public void addFullName(String fullName) {
        fullNames.add(fullName);
    }

    /**
     * Adds the newly defined custom property into the manager.
     */
    public void addCustomProperty(CustomProperty customProperty) {
        Prefix newPrefix = customProperty.getPrefix();
        String newFullName = customProperty.getFullName();
        addPrefix(newPrefix);
        addFullName(newFullName);
        CUSTOM_PROPERTIES.add(customProperty);
    }

    /**
     * Returns an immutable prefix set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Prefix> getPrefixes() {
        return Collections.unmodifiableSet(prefixes);
    }

    /**
     * Adds in all the prefixes that are present in the given {@code Set<Prefix> prefixes} object into the
     * Property Manager only if there are no prefixes present.
     *
     * @param prefixes the prefixes to be added
     */
    public void setPrefixes(Set<Prefix> prefixes) {
        if (this.prefixes.isEmpty()) {
            this.prefixes.addAll(prefixes);
        }
    }

    /**
     * Returns an immutable full names set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<String> getFullNames() {
        return Collections.unmodifiableSet(fullNames);
    }

    /**
     * Adds in all the full names that are present in the given {@code Set<String> fullNames} object into
     * the PropertyManager only if there are no full names present.
     *
     * @param fullNames the full names to be added
     */
    public void setFullNames(Set<String> fullNames) {
        if (this.fullNames.isEmpty()) {
            this.fullNames.addAll(fullNames);
        }
    }

    /**
     * Updates the property prefixes in {@code CliSyntax} class.
     */
    public void updatePropertyPrefixes() {
        setPrefixesSet(getPrefixes());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PropertyManager)) {
            return false;
        }

        PropertyManager anotherManager = (PropertyManager) other;
        return prefixes.equals(anotherManager.prefixes)
            && fullNames.equals(anotherManager.fullNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefixes, fullNames);
    }
}
