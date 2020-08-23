package model;

import java.util.Date;
import java.util.Objects;

public class Candidate implements ItemImpl {
    private int id = 0;
    private Date date = null;
    private String name = "";
    private String description = "";
    private int photoId = 0;
    private byte[] photo = null;

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

        public Builder setPhotoId(int id) {
            candidate.photoId = id;
            return this;
        }

        public Builder setPhoto(int photo) {
            candidate.photoId = photo;
            return this;
        }

        public Candidate build() {
            return candidate;
        }

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isEmpty() {
        return (id == 0 && date == null && name.equals("") && description.equals("") && photoId == 0);
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
        return String.format("Candidate{id=%s, date=%s, name='%s', description='%s', photo=%s}", id, date, name, description, photoId);
    }

}