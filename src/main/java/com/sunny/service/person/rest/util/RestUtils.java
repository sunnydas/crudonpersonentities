package com.sunny.service.person.rest.util;

import com.sunny.service.person.repository.domain.PersonDTO;
import com.sunny.service.person.repository.domain.PersonHobbyDTO;
import com.sunny.service.person.rest.domain.PersonPayLoad;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class RestUtils {

    public static ResponseEntity<PersonPayLoad> mapResponse(PersonDTO personDTO, HttpStatus httpStatus){
        PersonPayLoad personPayLoad = populate(personDTO);
        return new ResponseEntity<>(personPayLoad,httpStatus);
    }

    public static ResponseEntity<List<PersonPayLoad>> mapResponse(Iterable<PersonDTO> personDTOs, HttpStatus httpStatus){
        List<PersonPayLoad> personPayLoads = new ArrayList<>();
        if(personDTOs != null) {
            for (PersonDTO personDTO : personDTOs) {
                if(personDTO != null) {
                    personPayLoads.add(populate(personDTO));
                }
            }
        }
        return new ResponseEntity<>(personPayLoads,httpStatus);
    }

    public static PersonPayLoad populate(PersonDTO personDTO){
        PersonPayLoad personPayLoad = new PersonPayLoad();
        if(personDTO != null) {
            personPayLoad.setPersonId(personDTO.getPersonId());
            personPayLoad.setFirstName(personDTO.getFirstName());
            personPayLoad.setLastName(personDTO.getLastName());
            personPayLoad.setAge(personDTO.getAge());
            personPayLoad.setFavoriteColor(personDTO.getFavoriteColor());
            personPayLoad.setHobby(getHobbies(personDTO));
        }
        return personPayLoad;
    }

    public static List<String> getHobbies(PersonDTO personDTO){
        List<String> hobbies = new ArrayList<>();
        List<PersonHobbyDTO> personHobbyDTOS = personDTO.getPersonHobbies();
        if(personHobbyDTOS != null && !personHobbyDTOS.isEmpty()){
            for(PersonHobbyDTO personHobbyDTO : personHobbyDTOS){
                if(personHobbyDTO != null){
                    hobbies.add(personHobbyDTO.getHobby());
                }
            }
        }
        return hobbies;
    }

}