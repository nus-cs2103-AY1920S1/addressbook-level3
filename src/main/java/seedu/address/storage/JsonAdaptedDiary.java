package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;
import seedu.address.model.diary.Page;

/**
 * Jackson-friendly version of {@link Diary}.
 */
class JsonAdaptedDiary {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diary's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedPage> pages = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDiary} with the given diary details.
     */
    @JsonCreator
    public JsonAdaptedDiary(@JsonProperty("name") String name, @JsonProperty("pages") List<JsonAdaptedPage> pages) {
        this.name = name;
        if (pages != null) {
            this.pages.addAll(pages);
        }
    }

    /**
     * Converts a given {@code Diary} into this class for Jackson use.
     */
    public JsonAdaptedDiary(Diary source) {
        name = source.getName().fullName;
        pages.addAll(source.getPages().stream()
                .map(JsonAdaptedPage::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted diary object into the model's {@code Diary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted diary.
     */
    public Diary toModelType() throws IllegalValueException {
        final ArrayList<Page> modelPages = new ArrayList<>();
        for (JsonAdaptedPage page : pages) {
            modelPages.add(page.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        return new Diary(modelName, modelPages);
    }

}
