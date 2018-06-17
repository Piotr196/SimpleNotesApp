package com.simplenotesapp.simplenotesapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Sets;
import com.simplenotesapp.simplenotesapp.dto.NoteDto;
import com.simplenotesapp.simplenotesapp.mapper.NoteDtoMapper;
import com.simplenotesapp.simplenotesapp.mapper.NoteWithUsersDtoMapper;
import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.service.NoteService;
import com.simplenotesapp.simplenotesapp.service.SessionService;
import com.simplenotesapp.simplenotesapp.service.UserService;
import org.apache.tomcat.jni.Error;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@WebMvcTest(NoteController.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class NoteControllerTest {

    private static final NoteDto NOTE_DTO = new NoteDto(1L, "title", "content",
            ZonedDateTime.now(), ZonedDateTime.now(), new HashSet<>());

    private static final User USER = new User(1L, "login", "name", "surname", "password", Sets.newHashSet());

    private static final Note NOTE = new Note(1L, "title", "content",
            ZonedDateTime.now(), ZonedDateTime.now(), new HashSet<>());

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

    @Autowired
    private MockMvc mvc;


    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        given(noteDtoMapper.mapToEntity(any(NoteDto.class))).willReturn(NOTE);
        given(noteDtoMapper.mapToDto(any(Note.class))).willReturn(NOTE_DTO);
        given(noteService.save(any(Note.class))).willReturn(NOTE);
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void shouldAddNote() throws Exception {
        mvc.perform(post("/api/notes").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(NOTE_DTO))
                .with(user("admin").roles("ADMIN", "USER"))
        ).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void deleteNote() {
    }

    @Test
    public void getNotes() {
    }

    @Test
    public void getNote() {
    }

    @Test
    public void updateNote() {
    }

    @Test
    public void getUserLoginsForNote() {
    }

    @Test
    public void getNotesForUser() {
    }
}