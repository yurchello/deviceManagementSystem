package com.airplanesoft.dms.dto;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private Set<String> jobPositions;


    public UserDto(Integer id, String firstName, String lastName, String email, Set<String> jobPositions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.jobPositions = jobPositions;
    }

    public UserDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getJobPositions() {
        return jobPositions;
    }

    public void setJobPositions(Set<String> jobPositions) {
        this.jobPositions = jobPositions;
    }
}
