package seedu.address.export;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import seedu.address.storage.JsonAdaptedVisit;
import seedu.address.storage.JsonAdaptedVisitTodo;

/**
 * Additional Jackson annotations specific for csv Person parsing
 */
abstract class PersonMixIn {
    /**
     * Visit fields are nested beyond two layers and cannot properly be represented in .csv
     */
    @JsonIgnore
    private List<JsonAdaptedVisitTodo> visitTodos;
    @JsonIgnore
    private List<JsonAdaptedVisit> visits;
}
