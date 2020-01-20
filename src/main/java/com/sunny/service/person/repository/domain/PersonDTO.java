package com.sunny.service.person.repository.domain;

import javax.persistence.*;
import java.util.List;

@Entity(name = "person")
public class PersonDTO {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long personId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private short age;

    @Column(name = "favorite_color")
    private String favoriteColor;

    public List<PersonHobbyDTO> getPersonHobbies() {
        return personHobbies;
    }

    public void setPersonHobbies(List<PersonHobbyDTO> personHobbies) {
        this.personHobbies = personHobbies;
    }

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "person_id", nullable = false)
    private List<PersonHobbyDTO> personHobbies;

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

}
