package seedu.mark.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;

/**
 * Jackson-friendly version of {@link OfflineDocument}.
 */
public class JsonAdaptedOfflineDocument {

    private final int numStray;
    private final List<JsonAdaptedParagraph> paragraphs = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedOfflineDocument} with the given offline document details.
     */
    @JsonCreator
    public JsonAdaptedOfflineDocument(@JsonProperty("numStray") int numStray,
                                      @JsonProperty("paragraphs") List<JsonAdaptedParagraph> ps) {
        this.numStray = numStray;
        if (ps != null) {
            this.paragraphs.addAll(ps);
        }
    }

    /**
     * Converts a given {@code OfflineDocument} into this class for Jackson use.
     */
    public JsonAdaptedOfflineDocument(OfflineDocument doc) {
        doc.updateStrayIndex();
        this.numStray = doc.getNumStrayNotes();
        this.paragraphs.addAll(doc.getCollection().stream()
                .map(JsonAdaptedParagraph::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted cache object into the model's {@code OfflineDocument} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted offline document.
     */
    public OfflineDocument toModelType() throws IllegalValueException {
        List<Paragraph> ps = new ArrayList<>();
        for (JsonAdaptedParagraph jap : paragraphs) {
            ps.add(jap.toModelType());
        }
        return new OfflineDocument(ps, numStray);
    }
}
