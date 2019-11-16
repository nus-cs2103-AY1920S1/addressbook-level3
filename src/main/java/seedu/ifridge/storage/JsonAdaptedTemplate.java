package seedu.ifridge.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * Jackson-friendly version of {@link UniqueTemplateItems}.
 */
class JsonAdaptedTemplate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "TemplateList Item's %s field is missing!";
    public static final String MESSAGE_DUPLICATE_TEMPLATE_ITEM = "Template contains duplicate template items(s).";

    private final String name;

    private final List<JsonAdaptedTemplateItem> templateItems = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTemplate} with the given template details and given template items.
     */
    @JsonCreator
    public JsonAdaptedTemplate(@JsonProperty("name") String name, @JsonProperty("templateItems")
                                List<JsonAdaptedTemplateItem> templateItems) {
        this.name = name;
        this.templateItems.addAll(templateItems);
    }

    /**
     * Converts a given {@code UniqueTemplateItems} into this class for Jackson use.
     */
    public JsonAdaptedTemplate(UniqueTemplateItems source) {
        name = source.getName().fullName;
        templateItems.addAll(source.getTemplate().stream().map(JsonAdaptedTemplateItem::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted uniqueTemplateItems object into the model's
     * {@code UniqueTemplateItems} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted template item.
     */
    public UniqueTemplateItems toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        return new UniqueTemplateItems(modelName);
    }

}
