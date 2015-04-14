package com.example.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rishav Jain on 02-04-2015.
 * USER information class
 */
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String dateOfBirth;
    private List<String> memberOfEvents;

    public User() {

    }

    public User(String id, String firstName, String lastName, String phoneNumber, String dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.memberOfEvents = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "<Object to JSON Error>";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }

        User u = (User) obj;
        return this.id.contentEquals(u.getId());
    }

    public List<String> getMemberOfEvents() {
        return memberOfEvents;
    }

    public void setMemberOfEvents(List<String> memberOfEvents) {
        this.memberOfEvents = memberOfEvents;
    }

    public void addMemberOfEvents(String newEvent) {
        memberOfEvents.add(newEvent);
    }

    public void removeMemberOfEvents(String event) {
        memberOfEvents.remove(event);
    }
}
