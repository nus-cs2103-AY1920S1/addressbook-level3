package mams.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mams.logic.commands.exceptions.CommandException;
import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;
import mams.model.appeal.Appeal;

/**
 * Mass rejects appeals in Mams
 */
public class MassReject extends Reject {

    private final List<String> validIds;
    private final List<String> invalidIds;
    private final List<String> alreadyApproved = new ArrayList<>();
    private final List<String> alreadyRejected = new ArrayList<>();
    private final List<String> rejectedSuccessfully = new ArrayList<>();
    private final List<String> cannotFindIdList;

    public MassReject(List<String> validIds, List<String> invalidIds) {
        this.validIds = validIds;
        this.invalidIds = invalidIds;
        this.cannotFindIdList = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        List<Appeal> lastShownList = model.getFilteredAppealList();
        boolean foundId;
        for (String appealId : validIds) {
            foundId = false;
            for (Appeal appeal : lastShownList) {
                if (appealId.equalsIgnoreCase(appeal.getAppealId())) {
                    Appeal rejectedAppeal;
                    Appeal appealToReject = appeal;
                    if (appealToReject.isResolved() == false) {
                        rejectedAppeal = new Appeal(appealToReject.getAppealId(),
                                appealToReject.getAppealType(),
                                appealToReject.getStudentId(),
                                appealToReject.getAcademicYear(),
                                appealToReject.getStudentWorkload(),
                                appealToReject.getAppealDescription(),
                                appealToReject.getPreviousModule(),
                                appealToReject.getNewModule(),
                                appealToReject.getModuleToAdd(),
                                appealToReject.getModuleToDrop(),
                                true,
                                "REJECTED",
                                "");
                        model.setAppeal(appealToReject, rejectedAppeal);
                        rejectedSuccessfully.add(appealId);
                    } else if (appealToReject.isResolved()
                            && appealToReject.getResult().equalsIgnoreCase("APPROVED")) {
                        alreadyApproved.add(appeal.getAppealId());
                    } else if (appealToReject.isResolved()
                            && appealToReject.getResult().equalsIgnoreCase("REJECTED")) {
                        alreadyRejected.add(appeal.getAppealId());
                    }
                    foundId = true;
                    break;
                }
            }
            if (!foundId) {
                cannotFindIdList.add(appealId);
            }

        }
        return new CommandResult(resultGenerator());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MassReject)) {
            return false;
        }

        // state check
        MassReject e = (MassReject) other;
        List<String> compareValid = e.getValidList();
        List<String> compareInvalid = e.getInvalidIds();

        Collections.sort(compareValid);
        Collections.sort(compareInvalid);
        Collections.sort(this.validIds);
        Collections.sort(this.invalidIds);

        return validIds.equals(compareValid);

    }

    /**
     * gets list of valid appeal IDs
     * @return
     */
    public List<String> getValidList() {
        return validIds;
    }
    /**
     * gets list of invalid appeal IDs
     * * @return
     */
    public List<String> getInvalidIds() {
        return invalidIds;
    }


    /**
     * generates feedback message
     * @return
     */
    private String resultGenerator() {
        String result = "";
        if (rejectedSuccessfully.isEmpty()) {
            result += MESSAGE_NO_APPEALS_REJECTED;
        } else {
            result += "Successfully rejected: " + rejectedSuccessfully.toString();
        }
        if (!alreadyApproved.isEmpty()) {
            result += "\nAlready approved: " + alreadyApproved.toString();
        }
        if (!alreadyRejected.isEmpty()) {
            result += "\nAlready rejected: " + alreadyRejected.toString();
        }
        if (!invalidIds.isEmpty()) {
            result += "\nInvalid appeal IDs: " + invalidIds.toString();
        }
        if (!cannotFindIdList.isEmpty()) {
            result += "\nThese appeal IDs do not exist: " + cannotFindIdList.toString();
        }
        return result;
    }


}
