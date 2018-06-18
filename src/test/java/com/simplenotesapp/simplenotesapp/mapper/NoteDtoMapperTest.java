package com.simplenotesapp.simplenotesapp.mapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.simplenotesapp.simplenotesapp.dto.NoteDto;
import com.simplenotesapp.simplenotesapp.dto.UserDto;
import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class NoteDtoMapperTest {

    private static final User USER = new User(1L, "login", "name", "surname",
            "password", Sets.newHashSet());

    private static final UserDto USER_DTO = new UserDto(1L, "login", "name", "surname",
            "password", Sets.newHashSet());

    private static final NoteDto NOTE_DTO = new NoteDto(1L, "title", "content",
            null, null, Sets.newHashSet(USER.getId()));

    private static final Note NOTE = new Note(1L, "title", "content",
            null, null, Sets.newHashSet(USER));

    static{
        ZonedDateTime now = ZonedDateTime.now();
        NOTE.setCreatedTime(now);
        NOTE.setModifiedTime(now);
        NOTE_DTO.setCreatedTime(now);
        NOTE_DTO.setModifiedTime(now);
        USER.getNotes().add(NOTE);
        USER_DTO.getNotesId().add(NOTE.getId());
    }

    @InjectMocks
    private NoteDtoMapper noteDtoMapper;

    @Mock
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        given(userService.findAllById(any(Set.class))).willReturn(Lists.newArrayList(USER));
    }

    @Test
    public void shouldReturnMappedEntity() {
        //given
        //when
        Note entity = noteDtoMapper.mapToEntity(NOTE_DTO);
        //then
        Assert.assertEquals(NOTE.getUsers(), entity.getUsers());
        Assert.assertEquals(NOTE.getId(), entity.getId());
        Assert.assertEquals(NOTE.getContent(), entity.getContent());
        Assert.assertEquals(NOTE.getCreatedTime(), entity.getCreatedTime());
        Assert.assertEquals(NOTE.getModifiedTime(), entity.getModifiedTime());
        Assert.assertEquals(NOTE.getTitle(), entity.getTitle());
    }

    @Test
    public void shouldReturnMappedDto() {
        //given
        //when
        NoteDto dto = noteDtoMapper.mapToDto(NOTE);
        //then
        Assert.assertEquals(NOTE_DTO.getUsersId(), dto.getUsersId());
        Assert.assertEquals(NOTE_DTO.getId(), dto.getId());
        Assert.assertEquals(NOTE_DTO.getContent(), dto.getContent());
        Assert.assertEquals(NOTE_DTO.getCreatedTime(), dto.getCreatedTime());
        Assert.assertEquals(NOTE_DTO.getModifiedTime(), dto.getModifiedTime());
        Assert.assertEquals(NOTE_DTO.getTitle(), dto.getTitle());
    }
}