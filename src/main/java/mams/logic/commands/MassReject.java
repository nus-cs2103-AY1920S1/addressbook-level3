package mams.logic.commands;

import java.util.ArrayList;
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

    public MassReject(List<String> validIds, List<String> invalidIds) {
        this.validIds = validIds;
        this.invalidIds = invalidIds;
    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        List<Appeal> lastShownList = model.getFilteredAppealList();

        for (String appealId : validIds) {
            for (Appeal appeal : lastShownList) {
                if (appealId.equalsIgnoreCase(appeal.getAppealId())) {
                    Appeal approvedAppeal;
                    Appeal appealToApprove = appeal;
                    if (appealToApprove.isResolved() == false) {
                        approvedAppeal = new Appeal(appealToApprove.getAppealId(),
                                appealToApprove.getAppealType(),
                                appealToApprove.getStudentId(),
                                appealToApprove.getAcademicYear(),
                                appealToApprove.getStudentWorkload(),
                                appealToApprove.getAppealDescription(),
                                appealToApprove.getPreviousModule(),
                                appealToApprove.getNewModule(),
                                appealToApprove.getModuleToAdd(),
                                appealToApprove.getModuleToDrop(),
                                true,
                                "REJECTED",
                                "");
                        model.setAppeal(appealToApprove, approvedAppeal);
                        rejectedSuccessfully.add(appealId);
                    } else if (appealToApprove.isResolved() == true
                            && appealToApprove.getResult().equalsIgnoreCase("APPROVED")) {
                        alreadyApproved.add(appeal.getAppealId());
                    } else if (appealToApprove.isResolved() == true
                            && appealToApprove.getResult().equalsIgnoreCase("REJECTED")) {
                        alreadyRejected.add(appeal.getAppealId());
                    }
                    break;
                }
            }
        }
        return new CommandResult(resultGenerator());
    }

    /**
     * Generates response for user
     * @return
     */
    private String resultGenerator() {
        String result = "";
        if (rejectedSuccessfully.isEmpty()) {
            result += "No appeals were rejected";
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
        return result;
    }


}
