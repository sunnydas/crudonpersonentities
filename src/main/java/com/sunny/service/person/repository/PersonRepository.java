package com.sunny.service.person.repository;

import com.sunny.service.person.repository.domain.PersonDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonDTO,Long> {
}
