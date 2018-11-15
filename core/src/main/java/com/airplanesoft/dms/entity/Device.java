package com.airplanesoft.dms.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "devices")
public class Device extends AbstractEntity<Integer>{

    @ManyToOne(optional = false)
    @JoinColumn
    private DevicePlatform devicePlatform;

    @Enumerated(EnumType.STRING)
    @Column
    private DeviceState deviceState;

    @Column
    private ZonedDateTime activationDate;

    @Column
    private ZonedDateTime lastContactDate;

    public DevicePlatform getDevicePlatform() {
        return devicePlatform;
    }

    public void setDevicePlatform(DevicePlatform devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
    }

    public ZonedDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(ZonedDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public ZonedDateTime getLastContactDate() {
        return lastContactDate;
    }

    public void setLastContactDate(ZonedDateTime lastContactDate) {
        this.lastContactDate = lastContactDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Device device = (Device) o;
        return Objects.equals(devicePlatform, device.devicePlatform) &&
                deviceState == device.deviceState &&
                Objects.equals(activationDate, device.activationDate) &&
                Objects.equals(lastContactDate, device.lastContactDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), devicePlatform, deviceState, activationDate, lastContactDate);
    }

    @Override
    public String toString() {
        return "Device{" +
                "devicePlatform=" + devicePlatform +
                ", deviceState=" + deviceState +
                ", activationDate=" + activationDate +
                ", lastContactDate=" + lastContactDate +
                '}';
    }
}
