package com.sunny.service.person.repository.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "person_hobby")
public class PersonHobbyDTO {

    @Id
    @Column(name = "id")
    @JsonIgnore
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "hobby")
    private String hobby;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

}
