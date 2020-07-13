package model;

import java.util.Date;
import java.util.Objects;

public class Offer implements ItemImpl {
    private int id = 0;
    private Date date;
    private String name = "";
    private String author = "";
    private String text = "";

    public Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Offer offer;

        public Builder() {
            offer = new Offer();
        }

        public Builder setId(int id) {
            offer.id = id;
            return this;
        }

        public Builder setDate(Date dateCreate) {
            offer.date = dateCreate;
            return this;
        }

        public Builder setName(String name) {
            offer.name = name;
            return this;
        }

        public Builder setAuthor(String author) {
            offer.author = author;
            return this;
        }

        public Builder setText(String text) {
            offer.text = text;
            return this;
        }        

        public Offer build() {
            return offer;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public boolean isEmpty() {
        return id == 0 && date == null && name.equals("") && author.equals("") && text.equals("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Offer offer = (Offer) o;
        return id == offer.id
                && Objects.equals(date, offer.date)
                && Objects.equals(name, offer.name)
                && Objects.equals(author, offer.author)
                && Objects.equals(text, offer.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, name, author, text);
    }

}