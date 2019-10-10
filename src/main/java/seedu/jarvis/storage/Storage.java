package seedu.jarvis.storage;

/**
 * API of the Storage component.
 * This extends al the different models for all the features.
 * {@code AddressBookStorage} is the API for AddressBook.
 * {@code UserPrefsStorage} is the API for User Preferences and GUI settings.
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {}
