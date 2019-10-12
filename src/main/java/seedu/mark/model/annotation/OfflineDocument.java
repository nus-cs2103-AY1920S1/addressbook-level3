package seedu.mark.model.annotation;

import java.util.HashMap;

public class OfflineDocument {

    /** Version of cache it annotates.*/
    private String cacheVersion;
    /** Paragraphs with notes. */
    private HashMap<ParagraphIdentifier, Paragraph> annotations;

    public OfflineDocument() {

    }
}
