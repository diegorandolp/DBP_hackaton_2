package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;
    private PersonRepository personRepository;

    public PersonController(PersonService personService, PersonRepository personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable Long id) {
        if (personRepository.findById(id).isPresent()){
            Person person = personRepository.findById(id).get();
            PersonDto personDto = new PersonDto();
            personDto.setName(person.getName());
            // id
            personDto.setId(person.getId());
            personDto.setGroups(getListGroups(person));
            return personDto;
        }
        else return null;
    }

    @GetMapping
    public List<PersonDto> getPersons() {
        List<Person> personList = personRepository.findAll();
        if(personList.size() > 0){
            List<PersonDto> personDtos = new ArrayList<>();
            for(Person person: personList){
                PersonDto personDto = new PersonDto();
                personDto.setGroups(getListGroups(person));
                personDto.setName(person.getName());
                //id
                personDto.setId(person.getId());
                personDtos.add(personDto);
            }
            return personDtos;
        }else
            return new ArrayList<PersonDto>();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<GroupDto>> getPersonsGroup(@PathVariable Long id){
        Person person = personRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(getListGroups(person));
    }

    public List<GroupDto> getListGroups(Person person) {
        List<GroupDto> groupDtos = new ArrayList<>();
        for (int i = 0; i < person.getGroups().size(); i++){
            GroupDto groupDto = new GroupDto();
            groupDto.setName(person.getGroups().get(i).getName());
            //id
            groupDto.setId(person.getGroups().get(i).getId());
            groupDtos.add(groupDto);
        }
        return groupDtos;
    }
}