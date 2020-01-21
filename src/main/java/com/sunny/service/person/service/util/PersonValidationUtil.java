package com.sunny.service.person.service.util;

import com.sunny.service.person.exception.PersonValidationException;
import com.sunny.service.person.exception.PersonValidationExceptionType;
import com.sunny.service.person.repository.domain.PersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonValidationUtil {

    private static Logger logger = LoggerFactory.getLogger(PersonValidationUtil.class);

    public static void validatePerson(PersonDTO personDTO) throws PersonValidationException {
        if(personDTO != null){
            String firstName = personDTO.getFirstName();
            if(firstName != null
                    && firstName.length() > 100){
                String message = "First name exceeds max allowed %s";
                message = String.format(message, firstName);
                logger.error(message);
                throw new PersonValidationException(message,PersonValidationExceptionType.PERSON_FIRST_NAME_TOO_LONG);
            }
            String lastName = personDTO.getLastName();
            if(lastName != null
                    && lastName.length() > 100){
                String message = "Last name exceeds max allowed %s";
                message = String.format(message, lastName);
                logger.error(message);
                throw new PersonValidationException(message,PersonValidationExceptionType.PERSON_LAST_NAME_TOO_LONG);
            }
            long age = personDTO.getAge();
            if(age <= 0){
                String message = "Age invalid %s";
                message = String.format(message, age);
                logger.error(message);
                throw new PersonValidationException(message,PersonValidationExceptionType.INVALID_AGE);
            }
        }else{
            String message = "PersonDTO is null";
            logger.error(message);
            throw new PersonValidationException(message, PersonValidationExceptionType.PERSON_DTO_INVALID);
        }
    }

}
