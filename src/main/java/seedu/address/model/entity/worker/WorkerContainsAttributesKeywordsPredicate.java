package seedu.address.model.entity.worker;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;

// @@author dalisc
/**
 * Tests that a {@code Entity}'s {@code Name} matches any of the keywords given.
 */
public class WorkerContainsAttributesKeywordsPredicate implements Predicate<Worker> {
    private ArgumentMultimap workerMap = new ArgumentMultimap();
    private final ArgumentMultimap argumentMultimap;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public WorkerContainsAttributesKeywordsPredicate(ArgumentMultimap argumentMultimap) {
        this.argumentMultimap = argumentMultimap;
    }

    /**
     * Add the name of the worker to the map, if it exists. Otherwise, add an empty string.
     * @param worker The body tested against.
     */
    public void addNameToWorkerMap(Worker worker) {
        try {
            if (worker.getName().toString().equals("")) {
                workerMap.put(PREFIX_NAME, "");
            } else {
                workerMap.put(PREFIX_NAME, worker.getName().toString());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in name.");
        }
    }

    /**
     * Add the sex of the worker to the map, if it exists. Otherwise, add an empty string.
     * @param worker The body tested against.
     */
    public void addSexToWorkerMap(Worker worker) {
        try {
            if (worker.getSex().toString().equals("")) {
                workerMap.put(PREFIX_SEX, "");
            } else {
                workerMap.put(PREFIX_SEX, worker.getSex().toString());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in sex.");
        }
    }

    /**
     * Add the date of birth of the worker to the map, if it exists. Otherwise, add an empty string.
     * @param worker The body tested against.
     */
    public void addDobToWorkerMap(Worker worker) {
        try {
            if (worker.getDateOfBirth().isEmpty()) {
                workerMap.put(PREFIX_DATE_OF_BIRTH, "");
            } else {
                workerMap.put(PREFIX_DATE_OF_BIRTH, formatter.format(worker.getDateOfBirth()));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in DOB.");
        }
    }

    /**
     * Add the phone number of the worker to the map, if it exists. Otherwise, add an empty string.
     * @param worker The body tested against.
     */
    public void addPhoneNumberToWorkerMap(Worker worker) {
        try {
            if (worker.getPhone() == null) {
                workerMap.put(PREFIX_PHONE_NUMBER, "");
            } else {
                workerMap.put(PREFIX_PHONE_NUMBER, formatter.format(worker.getPhone()));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in DOD.");
        }
    }

    /**
     * Add the date joined of the worker to the map, if it exists. Otherwise, add an empty string.
     * @param worker The body tested against.
     */
    public void addDateJoinedToWorkerMap(Worker worker) {
        try {
            if (worker.getDateJoined() == null) {
                workerMap.put(PREFIX_DATE_JOINED, "");
            } else {
                workerMap.put(PREFIX_DATE_JOINED, formatter.format(worker.getDateJoined()));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in DOA.");
        }
    }

    /**
     * Add the designation of the worker to the map, if it exists. Otherwise, add an empty string.
     * @param worker The body tested against.
     */
    public void addDesignationToWorkerMap(Worker worker) {
        try {
            if (worker.getDesignation().isEmpty()) {
                workerMap.put(PREFIX_DESIGNATION, "");
            } else {
                workerMap.put(PREFIX_DESIGNATION, worker.getDesignation().get());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in NRIC.");
        }
    }

    /**
     * Add the employment status of the worker to the map, if it exists. Otherwise, add an empty string.
     * @param worker The body tested against.
     */
    public void addEmploymentStatusToWorkerMap(Worker worker) {
        try {
            if (worker.getEmploymentStatus().isEmpty()) {
                workerMap.put(PREFIX_EMPLOYMENT_STATUS, "");
            } else {
                workerMap.put(PREFIX_EMPLOYMENT_STATUS, worker.getEmploymentStatus().get());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in religion.");
        }
    }


    @Override
    public boolean test(Worker worker) {
        addDobToWorkerMap(worker);
        addNameToWorkerMap(worker);
        addSexToWorkerMap(worker);
        addDateJoinedToWorkerMap(worker);
        addDesignationToWorkerMap(worker);
        addEmploymentStatusToWorkerMap(worker);
        addPhoneNumberToWorkerMap(worker);
        return check();
    }

    /**
     * Checks the attributes supplied by the user against the worker with the worker map.
     * @return True if all attributes supplied by the user is also contained in the worker. Otherwise, returns false.
     */
    private boolean check() {
        boolean pass = true;
        try {
            for (Map.Entry<Prefix, List<String>> entry : argumentMultimap.getMap().entrySet()) {
                if (entry.getValue() != null && !entry.getKey().toString().equals("")
                        && !entry.getKey().equals(PREFIX_FLAG)) {
                    if (!workerMap.getValue(entry.getKey()).get().equalsIgnoreCase(entry.getValue().get(0))) {
                        pass = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception in comparing maps");
        }
        return pass;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkerContainsAttributesKeywordsPredicate // instanceof handles nulls
                && argumentMultimap.equals(((WorkerContainsAttributesKeywordsPredicate) other).argumentMultimap));
        // state check
    }

}
