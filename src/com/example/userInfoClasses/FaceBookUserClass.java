package com.example.userInfoClasses;

public class FaceBookUserClass {

	String firstName,lastName,facebookId,location,
	birthdate,movies,likes;

	public String getFirstName() {
		return firstName;
	}

	@Override
	public String toString() {
		return "FaceBookUserClass [firstName=" + firstName + ", lastName="
				+ lastName + ", facebookId=" + facebookId + ", location="
				+ location + ", birthdate=" + birthdate + ", movies=" + movies
				+ ", likes=" + likes + "]";
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

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getMovies() {
		return movies;
	}

	public void setMovies(String movies) {
		this.movies = movies;
	}

	public String getLikes() {
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}
}
