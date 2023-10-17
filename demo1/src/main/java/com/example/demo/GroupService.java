package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupService {

    private GroupRepository groupRepository;
    private PersonRepository personRepository;

    public GroupService(GroupRepository groupRepository, PersonRepository personRepository) {
        this.groupRepository = groupRepository;
        this.personRepository = personRepository;
    }

    // Crear un nuevo grupo
    @Transactional
    public ResponseEntity<Object> addGroup(Group group)  {

        Group newGroup = new Group();
        newGroup.setName(group.getName());
        List<Group> groupList = new ArrayList<>();
        groupList.add(newGroup);
        for(int i=0; i< group.getPersons().size(); i++){
            //if(!personRepository.findById(group.getPersons().get(i).getId()).isPresent()) {
            Person newPerson = group.getPersons().get(i);
            newPerson.setGroups(groupList);
            Person savedPerson = personRepository.save(newPerson);
            if(! personRepository.findById(savedPerson.getId()).isPresent())
                return ResponseEntity.unprocessableEntity().body("Group Creation Failed");
            //}
            //else  return   ResponseEntity.unprocessableEntity().body("Person with email Id is already Present");
        }
        return ResponseEntity.ok("Successfully created Group");
    }

    public List<PersonDto> gerGroupPersonsById(Long id) {
        Group group = groupRepository.findById(id).get();
        List<Person> personList = group.getPersons();
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
