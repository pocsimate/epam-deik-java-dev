package com.epam.training.ticketservice.model.movie;

import javax.persistence.*;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String category;
    private int length;

    private Movie(MovieBuilder builder) {
        this.title = builder.title;
        this.category = builder.category;
        this.length = builder.length;
    }

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public int getLength() {
        return length;
    }

    public static class MovieBuilder{

        private  String title;
        private  String category;
        private  int length;

        public MovieBuilder title(String title){
            this.title = title;
            return this;
        }

        public MovieBuilder category(String category){
            this.category = category;
            return this;
        }

        public MovieBuilder length(int length){
            this.length = length;
            return this;
        }

        public Movie build(){
            return new Movie(this);
        }

    }

}