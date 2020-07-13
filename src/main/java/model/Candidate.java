package model;


import java.util.Date;
import java.util.Objects;

public class Candidate implements ItemImpl {
    private int id = 0;
    private Date date;
    private String name = "";
    private String description = "";

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

        public Candidate build() {
            return candidate;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
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
        return id == candidate.id
                && Objects.equals(date, candidate.date)
                && Objects.equals(name, candidate.name)
                && Objects.equals(description, candidate.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, name, description);
    }

}