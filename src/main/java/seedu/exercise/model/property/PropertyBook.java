package seedu.exercise.model.property;

import static seedu.exercise.logic.parser.CliSyntax.setPropertyPrefixesSet;
import static seedu.exercise.model.util.DefaultPropertyBookUtil.getDefaultFullNames;
import static seedu.exercise.model.util.DefaultPropertyBookUtil.getDefaultPrefixes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.logic.parser.Prefix;

/**
 * Helps to keep track of all the existing prefixes and full names for both default and custom properties.
 * It also helps to keep track of all the existing custom properties that have been defined by the user.
 */
public class PropertyBook {
    public static final String MESSAGE_DUPLICATE_NAME_OR_PREFIX = "The full name or the prefix of the custom"
        + " clashes with another property/parameter";
    private static final Logger logger = LogsCenter.getLogger(PropertyBook.class);
    private static PropertyBook propertyBook;

    // Helps to ensure that the prefixes used in add/edit command and full names of default
    // properties are always present.
    private final Set<Prefix> defaultPrefixes = getDefaultPrefixes();
    private final Set<String> defaultFullNames = getDefaultFullNames();

    private final Set<Prefix> customPrefixes = new HashSet<>();
    private final Set<String> customFullNames = new HashSet<>();
    private final Set<CustomProperty> customProperties = new HashSet<>();

    private PropertyBook() {

    }

    public static PropertyBook getInstance() {
        if (propertyBook == null) {
            logger.info("PropertyBook first initialised");
            propertyBook = new PropertyBook();
        }
        return propertyBook;
    }

    /**
     * Adds in all the custom properties that are present in the given {@code Set<CustomProperty> customProperties}
     * into the PropertyBook.
     *
     * @param customProperties the custom properties to be added
     */
    public void addCustomProperties(Set<CustomProperty> customProperties) {
        this.customProperties.addAll(customProperties);
        setPrefixesAndFullNames(customProperties);
    }

    /**
     * Clears all of the custom properties in PropertyBook.
     */
    public void clearCustomProperties() {
        customProperties.clear();
        customPrefixes.clear();
        customFullNames.clear();
    }

    /**
     * Returns a new {@code Map<String, String>} containing valid custom properties that are present in the
     * PropertyBook. This ensures that if the user did not define a custom property in the storage and yet
     * edits the exercises' custom properties in the storage, the undefined custom properties will not show up.
     */
    public Map<String, String> removeInvalidCustomProperties(Map<String, String> customPropertiesMap) {
        Map<String, String> newMap = new TreeMap<>();
        for (String validProperty : customFullNames) {
            if (customPropertiesMap.containsKey(validProperty)) {
                newMap.put(validProperty, customPropertiesMap.get(validProperty));
            }
        }
        return newMap;
    }

    /**
     * Returns an immutable custom properties set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<CustomProperty> getCustomProperties() {
        return Collections.unmodifiableSet(customProperties);
    }

    /**
     * Returns an observable list of custom properties for display.
     */
    public ObservableList<CustomProperty> getObservableCustomProperties() {
        return FXCollections.observableList(new ArrayList<>(customProperties));
    }

    /**
     * Adds the newly defined custom property into the manager.
     */
    public void addCustomProperty(CustomProperty customProperty) {
        Prefix newPrefix = customProperty.getPrefix();
        String newFullName = customProperty.getFullName();
        addPrefix(newPrefix);
        addFullName(newFullName);
        customProperties.add(customProperty);
        updatePropertyPrefixes();
    }

    /**
     * Removes the custom property with the associated {@code fullName}, if such a custom property
     * exists.
     */
    public void removeCustomProperty(String fullName) {
        Optional<CustomProperty> toRemove = retrieveCustomProperty(fullName);
        if (toRemove.isPresent()) {
            removeCustomProperty(toRemove.get());
        }
    }

    /**
     * Removes the given {@code customProperty} and its associated prefix and full name from
     * PropertyBook.
     */
    private void removeCustomProperty(CustomProperty toRemove) {
        Prefix prefixToRemove = toRemove.getPrefix();
        String fullNameToRemove = toRemove.getFullName();
        removePrefix(prefixToRemove);
        removeFullName(fullNameToRemove);
        customProperties.remove(toRemove);
        updatePropertyPrefixes();
    }

    /**
     * Checks if the prefix has already been used by a property.
     */
    public boolean isPrefixUsed(Prefix prefix) {
        return customPrefixes.contains(prefix) || defaultPrefixes.contains(prefix);
    }

    /**
     * Checks if the full name has already been used by a property.
     */
    public boolean isFullNameUsed(String fullName) {
        return customFullNames.contains(fullName) || defaultFullNames.contains(fullName);
    }

    /**
     * Checks if the full name is used by a custom property.
     */
    public boolean isFullNameUsedByCustomProperty(String fullName) {
        return customFullNames.contains(fullName);
    }

    /**
     * Returns true if the given custom property is using any default/used custom names and prefixes.
     */
    public boolean hasClashingPrefixOrName(CustomProperty customProperty) {
        return isPrefixUsed(customProperty.getPrefix())
            || isFullNameUsed(customProperty.getFullName());
    }

    /**
     * Adds the prefix and full name of each of the custom property in {@code customProperties} into
     * the sets.
     */
    private void setPrefixesAndFullNames(Set<CustomProperty> customProperties) {
        for (CustomProperty property : customProperties) {
            this.addPrefix(property.getPrefix());
            this.addFullName(property.getFullName());
        }
    }

    /**
     * Adds the prefix of a newly defined custom property.
     */
    private void addPrefix(Prefix prefix) {
        customPrefixes.add(prefix);
    }

    /**
     * Adds the full name of a newly defined custom property.
     */
    private void addFullName(String fullName) {
        customFullNames.add(fullName);
    }

    /**
     * Removes the prefix of a custom property from {@code prefixes}.
     */
    private void removePrefix(Prefix prefix) {
        customPrefixes.remove(prefix);
    }

    /**
     * Removes the full name of a custom property from {@code fullNames}.
     */
    private void removeFullName(String fullName) {
        customFullNames.remove(fullName);
    }

    /**
     * Updates the property prefixes in {@code CliSyntax} class for use in add/edit command.
     */
    private void updatePropertyPrefixes() {
        Set<Prefix> combinedSet = new HashSet<>();
        combinedSet.addAll(defaultPrefixes);
        combinedSet.addAll(customPrefixes);
        setPropertyPrefixesSet(Collections.unmodifiableSet(combinedSet));
    }

    /**
     * Retrieves the custom property with the given {@code fullName}.
     */
    private Optional<CustomProperty> retrieveCustomProperty(String fullName) {
        Optional<CustomProperty> retrieved = Optional.empty();
        for (CustomProperty customProperty : customProperties) {
            if (customProperty.getFullName().equals(fullName)) {
                retrieved = Optional.of(customProperty);
                break;
            }
        }
        return retrieved;
    }
}
