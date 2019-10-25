package seedu.ifridge.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.ReadOnlyTemplateList;
import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * An Immutable GroceryList that is serializable to JSON format.
 */
@JsonRootName(value = "templateList")
class JsonSerializableTemplateList {

    public static final String MESSAGE_DUPLICATE_TEMPLATE = "TemplateList contains duplicate template(s).";

    private final List<JsonSerializableTemplate> templateList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTemplateList} with the given template items.
     */
    @JsonCreator
    public JsonSerializableTemplateList(@JsonProperty("templateList") List<JsonSerializableTemplate> templates) {
        this.templateList.addAll(templates);
    }

    /**
     * Converts a given {@code ReadOnlyTemplateList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTemplateList}.
     */
    public JsonSerializableTemplateList(ReadOnlyTemplateList source) {
        templateList.addAll(source.getTemplateList().stream().map(JsonSerializableTemplate::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this template list into the model's {@code TemplateList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TemplateList toModelType() throws IllegalValueException {
        TemplateList templates = new TemplateList();
        for (JsonSerializableTemplate jsonSerializableTemplate : templateList) {
            UniqueTemplateItems template = jsonSerializableTemplate.toModelType();
            if (templates.hasTemplate(template)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TEMPLATE);
            }
            templates.addTemplate(template);
        }
        return templates;
    }

}
