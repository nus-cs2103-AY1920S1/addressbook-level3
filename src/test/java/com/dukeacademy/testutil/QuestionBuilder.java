package com.dukeacademy.testutil;

import java.util.HashSet;
import java.util.Set;

import com.dukeacademy.model.question.Address;
import com.dukeacademy.model.question.Email;
import com.dukeacademy.model.question.Phone;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.tag.Tag;
import com.dukeacademy.model.util.SampleDataUtil;

/**
 * A utility class to help with building Question objects.
 */
public class QuestionBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Title title;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public QuestionBuilder() {
        title = new Title(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the QuestionBuilder with the data of {@code questionToCopy}.
     */
    public QuestionBuilder(Question questionToCopy) {
        title = questionToCopy.getTitle();
        phone = questionToCopy.getPhone();
        email = questionToCopy.getEmail();
        address = questionToCopy.getAddress();
        tags = new HashSet<>(questionToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code Question} that we are building.
     */
    public QuestionBuilder withTitle(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Question} that we are building.
     */
    public QuestionBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Question} that we are building.
     */
    public QuestionBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Question} that we are building.
     */
    public QuestionBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Question} that we are building.
     */
    public QuestionBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Question build() {
        return new Question(title, phone, email, address, tags);
    }

}
