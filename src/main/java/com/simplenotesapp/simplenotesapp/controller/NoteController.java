package com.simplenotesapp.simplenotesapp.controller;

import com.simplenotesapp.simplenotesapp.dto.NoteDto;
import com.simplenotesapp.simplenotesapp.dto.NoteWithUsersDto;
import com.simplenotesapp.simplenotesapp.mapper.NoteDtoMapper;
import com.simplenotesapp.simplenotesapp.mapper.NoteWithUsersDtoMapper;
import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.service.NoteService;
import com.simplenotesapp.simplenotesapp.service.SessionService;
import com.simplenotesapp.simplenotesapp.service.UserService;
import com.simplenotesapp.simplenotesapp.sorting.generic.SortingOrder;
import com.simplenotesapp.simplenotesapp.sorting.notes.NotesSortingSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class NoteController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private NoteDtoMapper noteDtoMapper;

    @Autowired
    private NoteWithUsersDtoMapper noteWithUsersDtoMapper;

    @RequestMapping(value = "/api/notes", method = RequestMethod.POST)
    public ResponseEntity<NoteDto> addNote(@RequestBody NoteDto noteDto) {
        NoteDto saved = noteDtoMapper.mapToDto(noteService.save(noteDtoMapper.mapToEntity(noteDto)));
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/notes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        if (sessionService.isUserInRole("ROLE_ADMIN")) {
            noteService.delete(noteService.findOneById(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/notes", method = RequestMethod.GET)
    public ResponseEntity<List<NoteDto>> getNotes(
            @RequestParam(value = "sortby", required = false) NotesSortingSubject sortingSubject,
            @RequestParam(value = "order", required = false) SortingOrder sortingOrder) {
        if (sessionService.isUserInRole("ROLE_ADMIN")) {
            List<Note> notes = noteService.findAll(sortingSubject, sortingOrder);
            List<NoteDto> notesDtos = notes.stream().map(note -> noteDtoMapper.mapToDto(note)).collect(Collectors.toList());
            return new ResponseEntity<>(notesDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/notes/{id}", method = RequestMethod.GET)
    public ResponseEntity<NoteWithUsersDto> getNote(@PathVariable Long id) {
        Note savedNote = noteService.findOneById(id);
        User loggedUser = sessionService.getLoggedUser();
        if (loggedUser != null && (savedNote.getUsers().contains(loggedUser.getId()) || sessionService.isUserInRole("ROLE_ADMIN"))) {
            NoteWithUsersDto noteWithUsersDto = noteWithUsersDtoMapper.mapToDto(savedNote);
            return new ResponseEntity<>(noteWithUsersDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/notes", method = RequestMethod.PUT)
    public ResponseEntity<NoteDto> updateNote(@RequestBody NoteDto noteDto) {
        Note savedNote = noteService.findOneById(noteDto.getId());
        User loggedUser = sessionService.getLoggedUser();
        if (loggedUser != null && (savedNote.getUsers().contains(loggedUser.getId()) || sessionService.isUserInRole("ROLE_ADMIN"))) {
            NoteDto updatedNoteDto = noteDtoMapper.mapToDto(noteService.update(noteDtoMapper.mapToEntity(noteDto)));
            return new ResponseEntity<>(updatedNoteDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/notes/{id}/users", method = RequestMethod.GET)
    public ResponseEntity<Set<String>> getUserLoginsForNote(@PathVariable Long id) {
        Note savedNote = noteService.findOneById(id);
        if (savedNote.getUsers().contains(sessionService.getLoggedUser().getId()) || sessionService.isUserInRole("ROLE_ADMIN")) {
            Set<String> logins = noteService.findOneById(id).getUsers().stream()
                    .map(User::getLogin).collect(Collectors.toSet());
            return new ResponseEntity<>(logins, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/notes/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<NoteDto>> getNotesForUser(
            @PathVariable Long id,
            @RequestParam(value = "sortby", required = false) NotesSortingSubject sortingSubject,
            @RequestParam(value = "order", required = false) SortingOrder sortingOrder) {

        User user = userService.findOneById(id);
        if (user.getId().equals(sessionService.getLoggedUser().getId()) || sessionService.isUserInRole("ROLE_ADMIN")) {
            List<Note> notes = new ArrayList<>();
            notes.addAll(user.getNotes());
            notes = noteService.sort(notes, sortingSubject, sortingOrder);
            List<NoteDto> notesDtos = notes.stream().map(note -> noteDtoMapper.mapToDto(note)).collect(Collectors.toList());
            return new ResponseEntity<>(notesDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}