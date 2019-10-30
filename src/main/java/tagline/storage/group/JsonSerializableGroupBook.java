//@@author e0031374
package tagline.storage.group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.group.Group;
import tagline.model.group.GroupBook;
import tagline.model.group.ReadOnlyGroupBook;

/**
 * An Immutable GroupBook that is serializable to JSON format.
 */
@JsonRootName(value = "groupbook")
public class JsonSerializableGroupBook {

    public static final String MESSAGE_DUPLICATE_GROUP = "Groups list contains duplicate group(s).";

    private final List<JsonAdaptedGroup> groups = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableGroupBook} with the given groups.
     */
    @JsonCreator
    public JsonSerializableGroupBook(@JsonProperty("groups") List<JsonAdaptedGroup> groups) {
        this.groups.addAll(groups);
    }

    /**
     * Converts a given {@code ReadOnlyGroupBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableGroupBook}.
     */
    public JsonSerializableGroupBook(ReadOnlyGroupBook source) {
        groups.addAll(source.getGroupList().stream().map(JsonAdaptedGroup::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code GroupBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public GroupBook toModelType() throws IllegalValueException {
        GroupBook groupBook = new GroupBook();
        for (JsonAdaptedGroup jsonAdaptedGroup : groups) {
            Group group = jsonAdaptedGroup.toModelType();
            if (groupBook.hasGroup(group)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GROUP);
            }
            groupBook.addGroup(group);
        }
        return groupBook;
    }

}
