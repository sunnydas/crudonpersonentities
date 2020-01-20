package com.sunny.service.person.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.service.person.repository.domain.PersonDTO;
import com.sunny.service.person.repository.domain.PersonHobbyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonServiceAPITestUtil {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceAPITestUtil.class);

    public static String getPersonAsJsonString(String firstName,
                                               String lastName,
                                               short age,
                                               String favoriteColor,
                                               List<String> hobbies){
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName(firstName);
        personDTO.setLastName(lastName);
        personDTO.setAge(age);
        personDTO.setFavoriteColor(favoriteColor);
        personDTO.setPersonHobbies(getHobbyDTOs(hobbies));
        return asJsonString(personDTO);
    }

    public static List<PersonHobbyDTO> getHobbyDTOs(List<String> hobbies){
        List<PersonHobbyDTO> personHobbyDTOS = new ArrayList<>();
        if(hobbies != null){
            for(String hobby : hobbies){
                PersonHobbyDTO personHobbyDTO = new PersonHobbyDTO();
                personHobbyDTO.setHobby(hobby);
                personHobbyDTOS.add(personHobbyDTO);
            }
        }
        return personHobbyDTOS;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertFrom(String json, Class<T> aClass){
        ObjectMapper objectMapper = new ObjectMapper();
        T type = null;
        try {
            type = objectMapper.readValue(json, aClass);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return type;
    }

}
