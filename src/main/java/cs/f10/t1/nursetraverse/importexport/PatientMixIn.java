package cs.f10.t1.nursetraverse.importexport;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cs.f10.t1.nursetraverse.storage.JsonAdaptedVisit;

/**
 * Additional Jackson annotations specific for csv Patient parsing
 */
abstract class PatientMixIn {
    /**
     * Visit fields are nested beyond two layers and cannot properly be represented in .csv
     */
    @JsonIgnore
    private List<JsonAdaptedVisit> visits;
}
