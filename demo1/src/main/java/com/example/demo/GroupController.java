package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private GroupService groupService;
    private GroupRepository groupRepository;

    public GroupController(GroupService groupService, GroupRepository groupRepository) {
        this.groupService = groupService;
        this.groupRepository = groupRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createGroup(@RequestBody Group group){

        return groupService.addGroup(group);
    }

    @GetMapping
    public List<GroupDto> getAllGroups(){
        List<Group> groupList = groupRepository.findAll();
        if(groupList.size() > 0){
            List<GroupDto> groupDtos = new ArrayList<>();
            for(Group group:groupList){
                GroupDto groupDto = new GroupDto();
                groupDto.setName(group.getName());
                groupDto.setId(group.getId());
                groupDtos.add(groupDto);
            }
            return groupDtos;
        } else
            return new ArrayList<GroupDto>();
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable Long id){
        if(groupRepository.findById(id).isPresent())
            return groupRepository.findById(id).get();
        else
            return null;
    }

    @GetMapping("/persons/{id}")
    public List<PersonDto> getGroupPersons(@PathVariable Long id){
            return groupService.gerGroupPersonsById(id);


    }

}
