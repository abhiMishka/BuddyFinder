package com.example.userInfoClasses;

public class LinkedInUserClass {
	
	String firstName,lastName,birthDate,linkedinId,education,location;

	@Override
	public String toString() {
		return "LinkedInUserClass [firstName=" + firstName + ", lastName="
				+ lastName + ", birthDate=" + birthDate + ", linkedinId="
				+ linkedinId + ", education=" + education + ", location="
				+ location + "]";
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getLinkedinId() {
		return linkedinId;
	}

	public void setLinkedinId(String linkedinId) {
		this.linkedinId = linkedinId;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
