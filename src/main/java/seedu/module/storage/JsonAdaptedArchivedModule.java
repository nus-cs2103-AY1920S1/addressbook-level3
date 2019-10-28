package seedu.module.storage;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.module.commons.core.LogsCenter;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.SemesterDetailList;

/**
 * Jackson-friendly version of {@link ArchivedModule}. This class serves only as a reader.
 */
class JsonAdaptedArchivedModule {
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedArchivedModule.class);

    private final String moduleCode;
    private final String title;
    private final String description;
    private final String prerequisite;
    private final String preclusion;
    private final List<JsonAdaptedSemesterDetail> semesterDetails;

    /**
     * Constructs a {@code JsonAdaptedArchivedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedArchivedModule(@JsonProperty("moduleCode") String moduleCode, @JsonProperty("title") String title,
            @JsonProperty("description") String description, @JsonProperty("prerequisite") String prerequisite,
            @JsonProperty("preclusion") String preclusion,
            @JsonProperty("semesterData") List<JsonAdaptedSemesterDetail> semesterDetails) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.prerequisite = prerequisite;
        this.preclusion = preclusion;
        this.semesterDetails = Optional.ofNullable(semesterDetails).orElse(new ArrayList<>());
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code ArchivedModule} object.
     */
    public ArchivedModule toModelType() {
        return new ArchivedModule(moduleCode, title, description, prerequisite, preclusion,
            fromListToSemesterDetailList(semesterDetails));
    }

    /**
     * Converts the list of {@code JsonAdaptedSemesterDetail} to {@code SemesterDetailList}.
     */
    private SemesterDetailList fromListToSemesterDetailList(List<JsonAdaptedSemesterDetail> semesterDetails) {
        return new SemesterDetailList(semesterDetails.stream()
            .map(semesterDetail -> {
                try {
                    return semesterDetail.toModelType();
                } catch (DateTimeParseException e) {
                    logger.info(
                        String.format("ArchivedModule %s has bad semester data.", moduleCode));
                    return null;
                }
            })
            .filter(semesterDetail -> semesterDetail != null)
            .collect(Collectors.toList())
        );
    }

}
