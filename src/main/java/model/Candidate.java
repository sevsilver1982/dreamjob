package model;

import java.util.Date;
import java.util.Objects;

public class Candidate implements Item {
    private int id = 0;
    private Date date = null;
    private String name = "";
    private String description = "";
    private int photoId = 0;
    private City city = new City();

    public Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Candidate candidate;

        public Builder() {
            candidate = new Candidate();
        }

        public Builder setId(int id) {
            candidate.id = id;
            return this;
        }

        public Builder setDate(Date dateCreate) {
            candidate.date = dateCreate;
            return this;
        }

        public Builder setName(String name) {
            candidate.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            candidate.description = description;
            return this;
        }

        public Builder setPhotoId(int photoId) {
            candidate.photoId = photoId;
            return this;
        }

        public Builder setCity(City city) {
            candidate.city = city;
            return this;
        }

        public Candidate build() {
            return candidate;
        }

    }

    @Override
    public boolean isEmpty() {
        return (id == 0 && date == null && name.equals("") && description.equals(""));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Candidate{id=%s, date=%s, name=%s, description=%s, photoId=%s, cityId=%s}", id, date, name, description, photoId, city);
    }

}