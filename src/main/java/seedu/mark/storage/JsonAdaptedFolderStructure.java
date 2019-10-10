package seedu.mark.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.folderstructure.FolderStructure;

/**
 * Jackson-friendly version of {@link FolderStructure}.
 */
public class JsonAdaptedFolderStructure {

    private final String name;
    private List<JsonAdaptedFolderStructure> subfolders = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFolderStructure} with the given folder structure.
     *
     * @param name       the name
     * @param subfolders the subfolders
     */
    @JsonCreator
    public JsonAdaptedFolderStructure(@JsonProperty("name") String name,
            @JsonProperty("subfolders") List<JsonAdaptedFolderStructure> subfolders) {
        this.name = name;
        if (subfolders != null) {
            this.subfolders.addAll(subfolders);
        }
    }

    /**
     * Instantiates a new Json adapted folder structure.
     *
     * @param source the source
     */
    public JsonAdaptedFolderStructure(FolderStructure source) {
        this.name = source.getFolder().folderName;
        this.subfolders = source.getSubfolders().stream()
                .map(JsonAdaptedFolderStructure::new).collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted bookmark object
     * into the model's {@code FolderStrucutre} object.
     *
     */
    public FolderStructure toModelType() {
        return new FolderStructure(new Folder(name), subfolders.stream().map(
                JsonAdaptedFolderStructure::toModelType).collect(
                Collectors.toList()));
    }

    @Override
    public String toString() {
        return name + "\n" + subfolders.toString();
    }
}
