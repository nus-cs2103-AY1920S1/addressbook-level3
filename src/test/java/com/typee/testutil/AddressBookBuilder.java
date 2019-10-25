package com.typee.testutil;

import com.typee.model.EngagementList;
import com.typee.model.engagement.Engagement;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code EngagementList ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private EngagementList engagementList;

    public AddressBookBuilder() {
        engagementList = new EngagementList();
    }

    public AddressBookBuilder(EngagementList engagementList) {
        this.engagementList = engagementList;
    }

    /**
     * Adds a new {@code Person} to the {@code EngagementList} that we are building.
     */
    public AddressBookBuilder withPerson(Engagement engagement) {
        engagementList.addEngagement(engagement);
        return this;
    }

    public EngagementList build() {
        return engagementList;
    }
}
