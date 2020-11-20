package model;

import java.util.Objects;

public class City implements Item {
    private int id = 0;
    private String name;

    public City() {
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean isEmpty() {
        return (id == 0 && name.equals(""));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city = (City) o;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return String.format("City{id=%s, name=%s}", id, name);
    }

}