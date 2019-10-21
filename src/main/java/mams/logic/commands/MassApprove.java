package mams.logic.commands;

import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.appeal.Appeal;

import java.util.ArrayList;
import java.util.List;

public class MassApprove extends Approve{

    private final List<String> validIds;
    private final List<String> invalidIds;
    private final List<String> alreadyApproved = new ArrayList<>();
    private final List<String> alreadyRejected = new ArrayList<>();
    private final List<String> approvedSuccessfully = new ArrayList<>();

    public MassApprove(List<String> validIds, List<String> invalidIds) {
        this.validIds = validIds;
        this.invalidIds = invalidIds;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
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
                                appealToApprove.getModule_to_add(),
                                appealToApprove.getModule_to_drop(),
                                true,
                                "APPROVED",
                                "");
                        model.setAppeal(appealToApprove, approvedAppeal);
                        approvedSuccessfully.add(appealId);
                    } else if (appealToApprove.isResolved() == true && appealToApprove.getResult().equalsIgnoreCase("APPROVED")) {
                        alreadyApproved.add(appeal.getAppealId());
                    } else if (appealToApprove.isResolved() == true && appealToApprove.getResult().equalsIgnoreCase("REJECTED")) {
                        alreadyRejected.add(appeal.getAppealId());
                    }
                    break;
                }
            }
        }
        return new CommandResult(resultGenerator());
    }
    private String resultGenerator() {
        String result = "";
        if(approvedSuccessfully.isEmpty()) {
            result += "No appeals were approved";
        } else {
            result += "Successfully approved: " + approvedSuccessfully.toString();
        }
        if(!alreadyApproved.isEmpty()) {
            result += "\nAlready approved: " + alreadyApproved.toString();
        }
        if(!alreadyRejected.isEmpty()) {
            result += "\nAlready rejected: " + alreadyRejected.toString();
        }
        if(!invalidIds.isEmpty()) {
            result += "\nInvalid appeal IDs: " + invalidIds.toString();
        }
        return result;
    }
}
