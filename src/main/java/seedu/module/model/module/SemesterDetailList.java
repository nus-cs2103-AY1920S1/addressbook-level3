package seedu.module.model.module;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.module.commons.core.index.Index;

/**
 * Represents a list of semester details pertaining to a module.
 * There are a total of 4 "semesters", 2 semesters and 2 special terms.
 *
 * See http://www.nus.edu.sg/registrar/calendar.html
 */
public class SemesterDetailList {

    private List<SemesterDetail> semesterDetails;

    /**
     * Constructs a SemesterDetailList. Contains 4 elements.
     * Each SemesterDetail defaults to unoffered status.
     */
    public SemesterDetailList() {
        this.semesterDetails = new ArrayList<>(4);
        for (int i = 1; i <= 4; i++) {
            this.semesterDetails.add(new SemesterDetail(i));
        }
    }

    /**
     * Constructs and sets the SemesterDetailList with new semester details.
     * @param semesterDetails list of semester details, may be an incomplete list.
     */
    public SemesterDetailList(List<SemesterDetail> semesterDetails) {
        this();
        for (SemesterDetail s : semesterDetails) {
            // Sets the corresponding semester details
            if (this.semesterDetails.contains(s)) {
                this.semesterDetails.set(Index.fromOneBased(s.getSemester()).getZeroBased(), s);
            }
        }
    }

    /**
     * Returns the SemesterDetailList as an Observable List.
     */
    public ObservableList<SemesterDetail> getAsObservableList() {
        ObservableList<SemesterDetail> observableList = FXCollections.observableArrayList();
        observableList.setAll(semesterDetails);
        return observableList;
    }
}
