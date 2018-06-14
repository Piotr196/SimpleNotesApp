package com.simplenotesapp.simplenotesapp.service;

import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.repository.NoteRepository;
import com.simplenotesapp.simplenotesapp.sorting.generic.Sorter;
import com.simplenotesapp.simplenotesapp.sorting.generic.SorterFactory;
import com.simplenotesapp.simplenotesapp.sorting.generic.SortingOrder;
import com.simplenotesapp.simplenotesapp.sorting.notes.NotesSortingSubject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class NoteServiceFindAllTests {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository mockNoteRepository;

    @Mock
    private Sorter<Note> mockSorter;

    @Mock
    private SorterFactory<Note, NotesSortingSubject> mockNotesSorterFactory;

    private List<Note> mockNotes = new ArrayList<>();
    private Note note1, note2, note3;
    private Set<User> mockUsers = new HashSet<>(Collections.singletonList(new User()));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ZonedDateTime time = ZonedDateTime.of(2018, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        note1 = new Note(1, "aaTitle", "kkContent", time, time, mockUsers);
        note2 = new Note(1, "zzTitle", "zzContent", time, time, mockUsers);
        note3 = new Note(1, "kkTitle", "aaContent", time, time, mockUsers);
        mockNotes = new ArrayList<>(Arrays.asList(note1, note2, note3));
        when(mockNoteRepository.findAll()).thenReturn(mockNotes);
    }

    @Test
    public void testFindAllNoSorting() {

        // Given
        // setUp() method

        List<Note> resultNotes;

        // 1. Without order provided
        // When
        resultNotes = noteService.findAll(null, null);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note1)));
        assertThat(resultNotes.get(1), is(equalTo(note2)));
        assertThat(resultNotes.get(2), is(equalTo(note3)));

        // 2. With ASC order provided
        // When
        resultNotes = noteService.findAll(null, SortingOrder.ASC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note1)));
        assertThat(resultNotes.get(1), is(equalTo(note2)));
        assertThat(resultNotes.get(2), is(equalTo(note3)));

        // 3. With DESC order provided
        // When
        resultNotes = noteService.findAll(null, SortingOrder.DESC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note1)));
        assertThat(resultNotes.get(1), is(equalTo(note2)));
        assertThat(resultNotes.get(2), is(equalTo(note3)));
    }

    @Test
    public void testFindAllSortWithoutOrderProvided() {

        // Given
        // setUp() method
        when(mockSorter.sort(mockNotes, SortingOrder.ASC)).thenReturn(Arrays.asList(note3, note2, note1));
        when(mockNotesSorterFactory.createSorter(ArgumentMatchers.any())).thenReturn(mockSorter);

        // When
        List<Note> resultNotes = noteService.findAll(NotesSortingSubject.TITLE, null);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note3)));
        assertThat(resultNotes.get(1), is(equalTo(note2)));
        assertThat(resultNotes.get(2), is(equalTo(note1)));
    }

    @Test
    public void testFindAllSortWithAscOrderProvided() {

        // Given
        // setUp() method
        when(mockSorter.sort(mockNotes, SortingOrder.ASC)).thenReturn(Arrays.asList(note3, note2, note1));
        when(mockNotesSorterFactory.createSorter(ArgumentMatchers.any())).thenReturn(mockSorter);

        // When
        List<Note> resultNotes = noteService.findAll(NotesSortingSubject.TITLE, SortingOrder.ASC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note3)));
        assertThat(resultNotes.get(1), is(equalTo(note2)));
        assertThat(resultNotes.get(2), is(equalTo(note1)));
    }

    @Test
    public void testFindAllSortWithDescOrderProvided() {

        // Given
        // setUp() method
        when(mockSorter.sort(mockNotes, SortingOrder.DESC)).thenReturn(Arrays.asList(note2, note3, note1));
        when(mockNotesSorterFactory.createSorter(ArgumentMatchers.any())).thenReturn(mockSorter);

        // When
        List<Note> resultNotes = noteService.findAll(NotesSortingSubject.TITLE, SortingOrder.DESC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note2)));
        assertThat(resultNotes.get(1), is(equalTo(note3)));
        assertThat(resultNotes.get(2), is(equalTo(note1)));
    }
}
