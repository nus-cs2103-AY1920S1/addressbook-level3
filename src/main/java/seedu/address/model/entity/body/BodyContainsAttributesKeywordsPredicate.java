package seedu.address.model.entity.body;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_ADMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;

//@@author dalisc

/**
 * Tests that a {@code Body}'s attributes matches any of the attributes given.
 */
public class BodyContainsAttributesKeywordsPredicate implements Predicate<Body> {
    private ArgumentMultimap bodyMap = new ArgumentMultimap();
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private final ArgumentMultimap argumentMultimap;

    public BodyContainsAttributesKeywordsPredicate(ArgumentMultimap argumentMultimap) {
        this.argumentMultimap = argumentMultimap;
    }

    /**
     * Add the name of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
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

    /**
     * Add the sex of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
    public void addSexToBodyMap(Body body) {
        try {
            if (body.getSex().toString().equals("")) {
                bodyMap.put(PREFIX_SEX, "");
            } else {
                bodyMap.put(PREFIX_SEX, body.getSex().toString().toLowerCase());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in sex.");
        }
    }

    /**
     * Add the date of birth of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
    public void addDobToBodyMap(Body body) {
        try {
            if (body.getDateOfBirth().isEmpty()) {
                bodyMap.put(PREFIX_DATE_OF_BIRTH, "");
            } else {
                bodyMap.put(PREFIX_DATE_OF_BIRTH, formatter.format(body.getDateOfBirth().get()));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in DOB.");
        }
    }

    /**
     * Add the date of death of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
    public void addDodToBodyMap(Body body) {
        try {
            if (body.getDateOfDeath() == null) {
                bodyMap.put(PREFIX_DATE_OF_DEATH, "");
            } else {
                bodyMap.put(PREFIX_DATE_OF_DEATH, formatter.format(body.getDateOfDeath().get()));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in DOD.");
        }
    }

    /**
     * Add the date of admission of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
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

    /**
     * Add the cause of death of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
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

    /**
     * Add the NRIC of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
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

    /**
     * Add the relation of next-of-kin of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
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

    /**
     * Add the religion of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
    public void addReligionToBodyMap(Body body) {
        try {
            if (body.getReligion().isEmpty()) {
                bodyMap.put(PREFIX_RELIGION, "");
            } else {
                bodyMap.put(PREFIX_RELIGION, body.getReligion().get().toString().toLowerCase());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in religion.");
        }
    }

    /**
     * Add the fridge that contains the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
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

    /**
     * Add the phone number of the next-of-kin of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
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

    /**
     * Add the name of next-of-kin of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
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

    /**
     * Add the organs donated by the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
    public void addOrgansForDonationToBodyMap(Body body) {
        try {
            if (body.getOrgansForDonation().isEmpty()) {
                bodyMap.put(PREFIX_ORGANS_FOR_DONATION, "");
            } else {
                body.getOrgansForDonation().forEach((organ) -> bodyMap.put(PREFIX_ORGANS_FOR_DONATION, organ));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in organs.");
        }
    }

    /**
     * Add the body status of the body to the map, if it exists. Otherwise, add an empty string.
     * @param body The body tested against.
     */
    public void addBodyStatusToBodyMap(Body body) {
        try {
            if (body.getBodyStatus().isEmpty()) {
                bodyMap.put(PREFIX_STATUS, "");
            } else {
                bodyMap.put(PREFIX_STATUS, body.getBodyStatus().get().toString());
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Exception found in body status.");
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
        addBodyStatusToBodyMap(body);
        return check();
    }

    /**
     * Checks the attributes supplied by the user against the body with the body map.
     * @return True if all attributes supplied by the user is also contained in the body. Otherwise, returns false.
     */
    private boolean check() {
        boolean pass = true;
        try {
            for (Map.Entry<Prefix, List<String>> entry : argumentMultimap.getMap().entrySet()) {
                if (entry.getValue() != null && !entry.getKey().toString().equals("")
                        && !entry.getKey().equals(PREFIX_FLAG)) {
                    if (entry.getKey().equals(PREFIX_ORGANS_FOR_DONATION)) {
                        if (!entry.getValue().containsAll(bodyMap.getMap().get(PREFIX_ORGANS_FOR_DONATION))) {
                            pass = false;
                            break;
                        }
                    } else if ((entry.getKey().equals(PREFIX_STATUS))) {
                        String status = entry.getValue().get(0).replaceAll("\\s+", "_");
                        pass = bodyMap.getValue(entry.getKey()).get().equalsIgnoreCase(status);
                        break;
                    } else if (!bodyMap.getValue(entry.getKey()).get().equalsIgnoreCase(entry.getValue().get(0))) {
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
                && argumentMultimap.equals(((BodyContainsAttributesKeywordsPredicate) other).argumentMultimap));
        // state check
    }

}
