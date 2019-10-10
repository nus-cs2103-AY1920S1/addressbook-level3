package seedu.jarvis.model;

/**
 * The API of the {@code Model} component.
 * This extends all the different models for all the features.
 * {@code AddressModel} is the API for AddressBook interactions.
 * {@code UserPrefsModel} is the API for User Preferences and GUI settings.
 * {@code HistoryModel} is the API for undoing and redoing commands.
 */
public interface Model extends AddressModel, UserPrefsModel, HistoryModel {}
