package com.sunny.service.person.service;

import com.sunny.service.person.exception.PersonValidationExceptionType;
import com.sunny.service.person.exception.ResourceNotFoundException;
import com.sunny.service.person.repository.PersonRepository;
import com.sunny.service.person.repository.domain.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public PersonDTO createPerson(PersonDTO person){
        return personRepository.save(person);
    }

    public PersonDTO updatePerson(PersonDTO person) throws ResourceNotFoundException {
       if(personRepository.existsById(person.getPersonId())) {
           person = personRepository.save(person);
       } else{
           handleMissingPersonRecord(person.getPersonId());
       }
       return person;
    }

    public void deletePerson(long personId) throws ResourceNotFoundException {
        if(personRepository.existsById(personId)) {
            personRepository.deleteById(personId);
        }else{
            handleMissingPersonRecord(personId);
        }
    }


    public PersonDTO getPerson(long personId) throws ResourceNotFoundException {
        Optional<PersonDTO> optional = personRepository.findById(personId);
        PersonDTO personDTO = null;
        if(optional.isPresent()){
            personDTO = optional.get();
        }else{
            handleMissingPersonRecord(personId);
        }
        return personDTO;
    }

    private static void handleMissingPersonRecord(long personId) throws ResourceNotFoundException {
        String error = String.format("Person not found for id %s",personId);
        throw new ResourceNotFoundException(error, PersonValidationExceptionType.PERSON_NOT_FOUND);
    }

    public Iterable<PersonDTO> getPersons(){
        return personRepository.findAll();
    }

}
