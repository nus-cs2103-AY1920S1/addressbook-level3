package seedu.address.model.entity.body;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.entity.Sex;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Tests that a {@code Entity}'s {@code Name} matches any of the keywords given.
 */
public class BodyContainsAttributesKeywordsPredicate implements Predicate<Body> {
    private final ArgumentMultimap argumentMultimap;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    ArgumentMultimap bodyMap = new ArgumentMultimap();

    public BodyContainsAttributesKeywordsPredicate(ArgumentMultimap argumentMultimap) {
        this.argumentMultimap = argumentMultimap;
    }

    public void addNameToBodyMap(Body body) {
        try {
            if (body.getName().toString().equals("")) {
                bodyMap.put(PREFIX_NAME, "");
            } else {
                bodyMap.put(PREFIX_NAME, body.getName().toString());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in name.");
        }
    }

    public void addSexToBodyMap(Body body) {
        if (body.getSex().toString().equals("")) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_NAME, body.getSex().toString());
        }
    }

    public void addDobToBodyMap(Body body) {
        if (body.getDateOfBirth() == null) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_NAME, formatter.format(body.getDateOfBirth()));
        }
    }

    public void addDodToBodyMap(Body body) {
        if (body.getDateOfDeath() == null) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_NAME, formatter.format(body.getDateOfDeath()));
        }
    }

    public void addDoaToBodyMap(Body body) {
        if (body.getDateOfAdmission() == null) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_NAME, formatter.format(body.getDateOfAdmission()));
        }
    }

    public void addCauseOfDeathToBodyMap(Body body) {
        if (body.getCauseOfDeath().isEmpty()) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_CAUSE_OF_DEATH, body.getCauseOfDeath().get());
        }
    }

    public void addNricOfDeathToBodyMap(Body body) {
        if (body.getNric().isEmpty()) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_NRIC, body.getNric().get().getNric());
        }
    }

    public void addRelationshipToBodyMap(Body body) {
        if (body.getRelationship().isEmpty()) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_RELATIONSHIP, body.getRelationship().get());
        }
    }

    public void addReligionToBodyMap(Body body) {
        if (body.getReligion().get().toString().equals("")) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_RELIGION, body.getReligion().get().toString());
        }
    }

    public void addFridgeIdToBodyMap(Body body) {
        if (Integer.toString(body.getFridgeId().get().getIdNum()).equals("")) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_FRIDGE_ID, Integer.toString(body.getFridgeId().get().getIdNum()));
        }
    }

    public void addPhoneNokToBodyMap(Body body) {
        if (body.getKinPhoneNumber().get().getPhoneNumber().equals("")) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_PHONE_NOK, body.getKinPhoneNumber().get().getPhoneNumber());
        }
    }

    public void addNameNokToBodyMap(Body body) {
        if (body.getNextOfKin().get().toString().equals("")) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            bodyMap.put(PREFIX_NAME_NOK, body.getNextOfKin().get().toString());
        }
    }

    public void addOrgansForDonationToBodyMap(Body body) {
        if (body.getOrgansForDonation().isEmpty()) {
            bodyMap.put(PREFIX_NAME, "");
        } else {
            body.getOrgansForDonation().get().forEach((organ) -> bodyMap.put(PREFIX_ORGANS_FOR_DONATION, organ));
        }
    }

    @Override
    public boolean test(Body body) {
        boolean pass = true;

        addCauseOfDeathToBodyMap(body);
        addDoaToBodyMap(body);
        addDobToBodyMap(body);
        addDodToBodyMap(body);
        addFridgeIdToBodyMap(body);
        addNameNokToBodyMap(body);
        addNameToBodyMap(body);
        addNricOfDeathToBodyMap(body);
        addOrgansForDonationToBodyMap(body);
        addPhoneNokToBodyMap(body);
        addRelationshipToBodyMap(body);
        addReligionToBodyMap(body);
        addSexToBodyMap(body);

        for (Map.Entry<Prefix, List<String>> entry : argumentMultimap.getMap().entrySet()) {
            if (entry.getKey().equals(PREFIX_ORGANS_FOR_DONATION)) {
                if (!entry.getValue().containsAll(bodyMap.getMap().get(PREFIX_ORGANS_FOR_DONATION))) {
                    pass = false;
                    break;
                }
            } else if (!bodyMap.getValue(entry.getKey()).equals(entry.getValue())) {
                pass = false;
                break;
            }
        }
        return pass;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BodyContainsAttributesKeywordsPredicate // instanceof handles nulls
                && argumentMultimap.equals(((BodyContainsAttributesKeywordsPredicate) other).argumentMultimap)); // state check
    }

}
