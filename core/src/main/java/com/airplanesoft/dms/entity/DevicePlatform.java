package com.airplanesoft.dms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "device_platforms")
public class DevicePlatform extends AbstractEntity<Integer>{

    @Column(nullable = false, unique = true)
    private String name;

    public DevicePlatform(String name) {
        this.name = name;
    }

    public DevicePlatform() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DevicePlatform that = (DevicePlatform) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "DevicePlatform{" +
                "name='" + name + '\'' +
                '}';
    }
}
