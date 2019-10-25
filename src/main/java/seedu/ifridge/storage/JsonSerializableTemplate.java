package seedu.ifridge.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * An Immutable GroceryList that is serializable to JSON format.
 */
class JsonSerializableTemplate {

    public static final String MESSAGE_DUPLICATE_TEMPLATE_ITEM = "Template contains duplicate template items(s).";
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Template's %s field is missing!";

    private final String name;

    private final List<JsonAdaptedTemplateItem> template = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTemplate} with the given template items.
     */
    @JsonCreator
    public JsonSerializableTemplate(@JsonProperty("name") String name, @JsonProperty("template")
                                    List<JsonAdaptedTemplateItem> templateItems) {
        this.name = name;
        this.template.addAll(templateItems);
    }

    /**
     * Converts a given {@code UniqueTemplateItems} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTemplate}.
     */
    public JsonSerializableTemplate(UniqueTemplateItems source) {
        name = source.getName().fullName;
        template.addAll(source.getTemplate().stream().map(JsonAdaptedTemplateItem::new)
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
        UniqueTemplateItems templateInput = new UniqueTemplateItems(modelName);

        for (JsonAdaptedTemplateItem jsonAdaptedTemplateItem : template) {
            TemplateItem item = jsonAdaptedTemplateItem.toModelType();
            if (templateInput.contains(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TEMPLATE_ITEM);
            }
            templateInput.add(item);
        }

        return templateInput;
    }

}
