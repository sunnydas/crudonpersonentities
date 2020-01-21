package com.sunny.service.person.rest;

import com.sunny.service.person.exception.PersonValidationException;
import com.sunny.service.person.exception.ResourceNotFoundException;
import com.sunny.service.person.repository.domain.PersonDTO;
import com.sunny.service.person.rest.domain.PersonPayLoad;
import com.sunny.service.person.rest.util.RestUtils;
import com.sunny.service.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PersonPayLoad> createPerson(@RequestBody PersonDTO person) throws PersonValidationException {
        person = personService.createPerson(person);
        return RestUtils.mapResponse(person,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PersonPayLoad>> getPersons(){
        Iterable<PersonDTO> personDTOS = personService.getPersons();
        return RestUtils.mapResponse(personDTOS,HttpStatus.OK);
    }

    @RequestMapping(value = "/{personId}",
            method = RequestMethod.GET)
    public ResponseEntity<PersonPayLoad> getPerson(@PathVariable("personId") @NotNull long personId) throws ResourceNotFoundException {
        PersonDTO person = personService.getPerson(personId);
        return RestUtils.mapResponse(person,HttpStatus.OK);
    }

    @RequestMapping(value = "/{personId}",
            method = RequestMethod.PUT)
    public ResponseEntity<PersonPayLoad> updatePerson(@PathVariable("personId") @NotNull long personId,
                                                      @RequestBody PersonDTO personDTO) throws ResourceNotFoundException, PersonValidationException {
        personDTO.setPersonId(personId);
        PersonDTO person = personService.updatePerson(personDTO);
        return RestUtils.mapResponse(person,HttpStatus.OK);
    }


    @RequestMapping(value = "/{personId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePerson(@PathVariable("personId") @NotNull long personId) throws ResourceNotFoundException {
        personService.deletePerson(personId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}