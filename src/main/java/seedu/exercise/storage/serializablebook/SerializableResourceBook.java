package seedu.exercise.storage.serializablebook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Resource;
import seedu.exercise.storage.resource.JsonAdaptedResource;

/**
 * An immutable ResourceBook that is serializable to JSON format.
 * In particular, this resource book can be extended to create an immutable ResourceBook that holds
 * any {@code JsonAdaptedResource} of type {@code T}.
 */
public abstract class SerializableResourceBook<T extends JsonAdaptedResource<U>, U extends Resource> {

    public static final String MESSAGE_DUPLICATE_RESOURCE = "The list has duplicate exercises/regimes/schedules.";

    private static final Logger logger = LogsCenter.getLogger(SerializableResourceBook.class);

    private final List<T> jsonResources = new ArrayList<>();

    public SerializableResourceBook(List<T> jsonResources) {
        this.jsonResources.addAll(jsonResources);
    }

    public SerializableResourceBook(ReadOnlyResourceBook<U> source, Class<T> clazz) {
        jsonResources
            .addAll(source.getSortedResourceList()
                .stream()
                .map(U::toJsonType)
                .map(clazz::cast)
                .collect(Collectors.toList()));
    }

    /**
     * Converts the Jackson-friendly {@code SerializableResourceBook} into the model's {@code ReadOnlyResourceBook}.
     *
     * @throws IllegalValueException if there are any violations in the data constraints.
     */
    public ReadOnlyResourceBook<U> toModelType(Class<U> clazz, Comparator<U> comparator) throws IllegalValueException {
        ReadOnlyResourceBook<U> resourceBook = new ReadOnlyResourceBook<>(comparator);
        for (JsonAdaptedResource jsonResource : jsonResources) {
            U resourceModel = clazz.cast(jsonResource.toModelType());
            if (resourceBook.hasResource(resourceModel)) {
                logger.info("The list to convert has duplicates.");
                throw new IllegalValueException(MESSAGE_DUPLICATE_RESOURCE);
            }
            resourceBook.addResource(resourceModel);
        }
        return resourceBook;
    }

    public void setList(List<T> data) {
        jsonResources.addAll(data);
    }

}
