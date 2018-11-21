package com.airplanesoft.dms.dto;


public class DeviceDto {

    private Integer id;

    private String devicePlatform;

    private String deviceState;

    public DeviceDto(Integer id, String devicePlatform, String deviceState) {
        this.id = id;
        this.devicePlatform = devicePlatform;
        this.deviceState = deviceState;
    }

    public DeviceDto() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevicePlatform() {
        return devicePlatform;
    }

    public void setDevicePlatform(String devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "id=" + id +
                ", devicePlatform='" + devicePlatform + '\'' +
                ", deviceState='" + deviceState + '\'' +
                '}';
    }
}
