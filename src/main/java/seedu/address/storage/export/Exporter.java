package seedu.address.storage.export;

/**
 * Abstract class for exporting Strings generated from nJoyAssistant into documents.
 */
public abstract class Exporter {
    protected static final String EXPORT_DIRECTORY_PATH = System.getProperty("user.dir") + "/exports/";
}
