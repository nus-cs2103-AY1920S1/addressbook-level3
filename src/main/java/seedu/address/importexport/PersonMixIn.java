package seedu.address.importexport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import seedu.address.storage.JsonAdaptedVisit;

/**
 * Additional Jackson annotations specific for csv Person parsing
 */
abstract class PersonMixIn {
    /**
     * Visit fields are nested beyond two layers and cannot properly be represented in .csv
     */
    @JsonIgnore
    private List<JsonAdaptedVisit> visits;
}
