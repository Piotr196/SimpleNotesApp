package com.simplenotesapp.simplenotesapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.simplenotesapp.simplenotesapp.dto.NoteDto;
import com.simplenotesapp.simplenotesapp.dto.NoteWithUsersDto;
import com.simplenotesapp.simplenotesapp.dto.UserDto;
import com.simplenotesapp.simplenotesapp.mapper.NoteDtoMapper;
import com.simplenotesapp.simplenotesapp.mapper.NoteWithUsersDtoMapper;
import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.service.NoteService;
import com.simplenotesapp.simplenotesapp.service.SessionService;
import com.simplenotesapp.simplenotesapp.service.UserService;
import com.simplenotesapp.simplenotesapp.sorting.generic.SortingOrder;
import com.simplenotesapp.simplenotesapp.sorting.notes.NotesSortingSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class NoteControllerTest {

    private static final User USER = new User(1L, "login", "name", "surname",
            "password", Sets.newHashSet());

    private static final UserDto USER_DTO = new UserDto(1L, "login", "name", "surname",
            "password", Sets.newHashSet());

    private static final NoteDto NOTE_DTO = new NoteDto(1L, "title", "content",
            ZonedDateTime.now(), ZonedDateTime.now(), Sets.newHashSet(USER.getId()));

    private static final Note NOTE = new Note(1L, "title", "content",
            ZonedDateTime.now(), ZonedDateTime.now(), Sets.newHashSet(USER));

    private static final List<Note> ALL_NOTES = Lists.newArrayList(NOTE);

    private static final NoteWithUsersDto NOTE_WITH_USERS_DTO = new NoteWithUsersDto(1L, "title", "content",
            ZonedDateTime.now(), ZonedDateTime.now(), Sets.newHashSet(USER_DTO));

    static{
        USER.getNotes().add(NOTE);
        USER_DTO.getNotesId().add(NOTE.getId());
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private NoteService noteService;

    @MockBean
    private UserService userService;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private NoteDtoMapper noteDtoMapper;

    @MockBean
    private NoteWithUsersDtoMapper noteWithUsersDtoMapper;

    private MockMvc mvc;


    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        given(noteDtoMapper.mapToEntity(any(NoteDto.class))).willReturn(NOTE);
        given(noteDtoMapper.mapToDto(any(Note.class))).willReturn(NOTE_DTO);
        given(noteWithUsersDtoMapper.mapToEntity(any(NoteDto.class))).willReturn(NOTE);
        given(noteWithUsersDtoMapper.mapToDto(any(Note.class))).willReturn(NOTE_WITH_USERS_DTO);
        given(noteService.save(any(Note.class))).willReturn(NOTE);
        given(noteService.update(any(Note.class))).willReturn(NOTE);
        given(noteService.findAll(NotesSortingSubject.TITLE, SortingOrder.ASC)).willReturn(ALL_NOTES);
        given(noteService.findOneById(any(Long.class))).willReturn(NOTE);
        given(noteService.update(any(Note.class))).willReturn(NOTE);
        given(noteService.sort(any(List.class), any(NotesSortingSubject.class), any(SortingOrder.class))).willReturn(ALL_NOTES);
        given(sessionService.isUserInRole(any(String.class))).willReturn(true);
        given(sessionService.getLoggedUser()).willReturn(USER);
        given(userService.findOneById(any(Long.class))).willReturn(USER);
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void shouldAddNote() throws Exception {
        //given
        //when
        mvc.perform(post("/api/notes").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(NOTE_DTO))
                .with(user("admin").roles("ADMIN", "USER"))).
        //then
                andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shoulDeleteNote() throws Exception {
        //given
        //when
        mvc.perform(delete("/api/notes/1").with(csrf())
                .with(user("admin").roles("ADMIN", "USER"))).
        //then
                andExpect(status().isOk());
    }

    @Test
    public void shoulReturnUnauthorized() throws Exception {
        //given
        //when
        when(sessionService.isUserInRole(any(String.class))).thenReturn(false);
        mvc.perform(delete("/api/notes/1").with(csrf())
                .with(user("user").roles("USER"))).
        //then
                andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnNotes() throws Exception {
        //given
        //when
        mvc.perform(get("/api/notes").with(csrf())
                .with(user("admin").roles("ADMIN"))).
        //then
                andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNote() throws Exception {
        //given
        //when
        mvc.perform(get("/api/notes/1").with(csrf())
                .with(user("user").roles("USER"))).
        //then
        andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void updateNote() throws Exception {
        //given
        //when
        mvc.perform(put("/api/notes").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(NOTE_DTO))
                .with(user("user").roles("USER"))).
        //then
        andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getUserLoginsForNote() throws Exception {
        //given
        //when
        mvc.perform(get("/api/notes/1/users").with(csrf())
                .with(user("user").roles("USER"))).
        //then
        andExpect(status().isOk()).andExpect(jsonPath("$[0]", is("login")));
    }

    @Test
    public void getNotesForUser() throws Exception {
        //given
        //when
        mvc.perform(get("/api/notes/users/1?sortby=TITLE&order=ASC").with(csrf())
                .with(user("user").roles("USER"))).
        //then
        andExpect(status().isOk()).andExpect(jsonPath("$[0].id", is(1)));
    }
}