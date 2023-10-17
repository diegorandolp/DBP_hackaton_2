package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private GroupRepository groupRepository;

    public PersonService(PersonRepository personRepository, GroupRepository groupRepository) {
        this.personRepository = personRepository;
        this.groupRepository = groupRepository;
    }


    // * Crear nueva persona

    public ResponseEntity<Person> createPerson(Person model) {
        Person person = new Person();
        //if (personRepository.findById(model.getId()).isPresent()) {
            //return ResponseEntity.badRequest().body("The Email is already Present, Failed to Create new Person");
        //} else {
            person.setName(model.getName());
            person.setGroups(model.getGroups());

            Person savedPerson = personRepository.save(person);
            //if (personRepository.findById(savedPerson.getId()).isPresent())
                return ResponseEntity.ok(savedPerson);
            //else return ResponseEntity.unprocessableEntity().body("Failed Creating Person as Specified");
        //}
    }


}
