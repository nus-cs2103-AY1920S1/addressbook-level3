package seedu.address.storage.export;

/**
 * Abstract class for exporting Strings generated from nJoyAssistant into documents.
 */
public abstract class Exporter {
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    protected static final String EXPORT_DIRECTORY_PATH = System.getProperty("user.dir") + FILE_SEPARATOR + "exports"
            + FILE_SEPARATOR;
}
