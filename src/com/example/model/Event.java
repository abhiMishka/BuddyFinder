package com.example.model;



import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Rishav Jain on 09-04-2015.
 * Event Information Class
 */
public class Event {

    public static String[] CATEGORIES = {"movie", "coffee", "outstation"};

    private String id;
    private String ownerId;
    private String title;
    private String message;
    private String category;
    private String time;
    private double locationLatitude;
    private double locationLongitude;
    private List<String> members;

    public Event() {

    }

    public Event(String ownerId, String title, String message,
                 String category, String time, double locationLatitude, double locationLongitude) {
        this.ownerId = ownerId;
        this.title = title;
        this.message = message;
        this.category = category;
        this.time = time;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.members = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void addMember(String newMember) {
        this.members.add(newMember);
    }

    public void removeMember(String member) {
        this.members.remove(member);
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
        if (!(obj instanceof Event)) {
            return false;
        }

        Event e = (Event) obj;
        return this.id.contentEquals(e.getId());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
