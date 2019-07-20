package com.airplanesoft.dms.dto;

public class JobPositionDto {
    private Integer id;
    private String name;

    public JobPositionDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public JobPositionDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
