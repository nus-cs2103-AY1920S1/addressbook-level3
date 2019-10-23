package seedu.address.model.entity.worker;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;

import javax.swing.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;

/**
 * Tests that a {@code Entity}'s {@code Name} matches any of the keywords given.
 */
public class WorkerContainsAttributesKeywordsPredicate implements Predicate<Worker> {
    private final ArgumentMultimap argumentMultimap;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    ArgumentMultimap workerMap = new ArgumentMultimap();

    public WorkerContainsAttributesKeywordsPredicate(ArgumentMultimap argumentMultimap) {
        this.argumentMultimap = argumentMultimap;
    }
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

    private boolean check() {
        boolean pass = true;
        try {
            for (Map.Entry<Prefix, List<String>> entry : argumentMultimap.getMap().entrySet()) {
                if (entry.getValue() != null && !entry.getKey().toString().equals("") &&
                        !entry.getKey().equals(PREFIX_FLAG)) {
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
                && argumentMultimap.equals(((WorkerContainsAttributesKeywordsPredicate) other).argumentMultimap)); // state check
    }

}
