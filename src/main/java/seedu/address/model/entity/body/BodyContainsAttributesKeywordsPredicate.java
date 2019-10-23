package seedu.address.model.entity.body;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static seedu.address.logic.parser.CliSyntax.*;

//@@author dalisc

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
        try {
            if (body.getSex().toString().equals("")) {
                bodyMap.put(PREFIX_SEX, "");
            } else {
                bodyMap.put(PREFIX_SEX, body.getSex().toString());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in sex.");
        }
    }

    public void addDobToBodyMap(Body body) {
        try {
            if (body.getDateOfBirth().isEmpty()) {
                bodyMap.put(PREFIX_DATE_OF_BIRTH, "");
            } else {
                bodyMap.put(PREFIX_DATE_OF_BIRTH, formatter.format(body.getDateOfBirth()));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in DOB.");
        }
     }

    public void addDodToBodyMap(Body body) {
        try {
            if (body.getDateOfDeath() == null) {
                bodyMap.put(PREFIX_DATE_OF_DEATH, "");
            } else {
                bodyMap.put(PREFIX_DATE_OF_DEATH, formatter.format(body.getDateOfDeath()));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in DOD.");
        }
    }

    public void addDoaToBodyMap(Body body) {
        try {
            if (body.getDateOfAdmission() == null) {
                bodyMap.put(PREFIX_DATE_OF_ADMISSION, "");
            } else {
                bodyMap.put(PREFIX_DATE_OF_ADMISSION, formatter.format(body.getDateOfAdmission()));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in DOA.");
        }
    }

    public void addCauseOfDeathToBodyMap(Body body) {
        try {
            if (body.getCauseOfDeath().isEmpty()) {
                bodyMap.put(PREFIX_CAUSE_OF_DEATH, "");
            } else {
                bodyMap.put(PREFIX_CAUSE_OF_DEATH, body.getCauseOfDeath().get());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in COD.");
        }
    }

    public void addNricOfDeathToBodyMap(Body body) {
        try {
            if (body.getNric().isEmpty()) {
                bodyMap.put(PREFIX_NRIC, "");
            } else {
                bodyMap.put(PREFIX_NRIC, body.getNric().get().getNric());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in NRIC.");
        }
    }

    public void addRelationshipToBodyMap(Body body) {
        try {
            if (body.getRelationship().isEmpty()) {
                bodyMap.put(PREFIX_RELATIONSHIP, "");
            } else {
                bodyMap.put(PREFIX_RELATIONSHIP, body.getRelationship().get());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in relationship.");
        }
    }

    public void addReligionToBodyMap(Body body) {
        try {
            if (body.getReligion().isEmpty()) {
                bodyMap.put(PREFIX_RELIGION, "");
            } else {
                bodyMap.put(PREFIX_RELIGION, body.getReligion().get().toString());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in religion.");
        }
    }

    public void addFridgeIdToBodyMap(Body body) {
        try {
            if (body.getFridgeId().isEmpty()) {
                bodyMap.put(PREFIX_FRIDGE_ID, "");
            } else {
                bodyMap.put(PREFIX_FRIDGE_ID, Integer.toString(body.getFridgeId().get().getIdNum()));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in fridge.");
        }
    }

    public void addPhoneNokToBodyMap(Body body) {
        try {
            if (body.getKinPhoneNumber().isEmpty()) {
                bodyMap.put(PREFIX_PHONE_NOK, "");
            } else {
                bodyMap.put(PREFIX_PHONE_NOK, body.getKinPhoneNumber().get().getPhoneNumber());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in NOK phone.");
        }
    }

    public void addNameNokToBodyMap(Body body) {
        try {
            if (body.getNextOfKin().isEmpty()) {
                bodyMap.put(PREFIX_NAME_NOK, "");
            } else {
                bodyMap.put(PREFIX_NAME_NOK, body.getNextOfKin().get().toString());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in NOK name.");
        }
    }

    public void addOrgansForDonationToBodyMap(Body body) {
        try {
            if (body.getOrgansForDonation().isEmpty()) {
                bodyMap.put(PREFIX_ORGANS_FOR_DONATION, "");
            } else {
                body.getOrgansForDonation().get().forEach((organ) -> bodyMap.put(PREFIX_ORGANS_FOR_DONATION, organ));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in organs.");
        }
    }

    @Override
    public boolean test(Body body) {
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
        return check();
    }

    private boolean check() {
        boolean pass = true;
        try {
            for (Map.Entry<Prefix, List<String>> entry : argumentMultimap.getMap().entrySet()) {
                if (entry.getValue() != null && !entry.getKey().toString().equals("") &&
                        !entry.getKey().equals(PREFIX_FLAG)) {
                    if (entry.getKey().equals(PREFIX_ORGANS_FOR_DONATION)) {
                        if (!entry.getValue().containsAll(bodyMap.getMap().get(PREFIX_ORGANS_FOR_DONATION))) {
                            pass = false;
                            break;
                        }
                    } else if (!bodyMap.getValue(entry.getKey()).get().equals(entry.getValue().get(0))) {
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
                || (other instanceof BodyContainsAttributesKeywordsPredicate // instanceof handles nulls
                && argumentMultimap.equals(((BodyContainsAttributesKeywordsPredicate) other).argumentMultimap)); // state check
    }

}
