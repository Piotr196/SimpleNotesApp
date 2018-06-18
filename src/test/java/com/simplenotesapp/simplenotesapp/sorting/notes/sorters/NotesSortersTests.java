package com.simplenotesapp.simplenotesapp.sorting.notes.sorters;

import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.sorting.generic.Sorter;
import com.simplenotesapp.simplenotesapp.sorting.generic.SortingOrder;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class NotesSortersTests {

    private List<Note> mockNotes = new ArrayList<>();
    private Note note1, note2, note3;
    private Set<User> mockUsers = new HashSet<>(Collections.singletonList(new User()));

    @Before
    public void setUp() {
        ZonedDateTime time1c = ZonedDateTime.of(2016, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        ZonedDateTime time2c = ZonedDateTime.of(2017, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        ZonedDateTime time3c = ZonedDateTime.of(2018, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());

        ZonedDateTime time1m = ZonedDateTime.of(2011, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        ZonedDateTime time2m = ZonedDateTime.of(2012, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        ZonedDateTime time3m = ZonedDateTime.of(2013, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());

        note1 = new Note(1, "aaTitle", "kkContent", time2c, time3m, mockUsers);
        note2 = new Note(1, "zzTitle", "zzContent", time1c, time1m, mockUsers);
        note3 = new Note(1, "kkTitle", "aaContent", time3c, time2m, mockUsers);
        mockNotes = new ArrayList<>(Arrays.asList(note1, note2, note3));
    }

    @Test
    public void titleSorterAscTest() {

        // Given
        // setUp() method
        Sorter<Note> sorter = new NotesTitleSorter();

        // When
        List<Note> resultNotes = sorter.sort(mockNotes, SortingOrder.ASC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note1)));
        assertThat(resultNotes.get(1), is(equalTo(note3)));
        assertThat(resultNotes.get(2), is(equalTo(note2)));
    }

    @Test
    public void titleSorterDescTest() {

        // Given
        // setUp() method
        Sorter<Note> sorter = new NotesTitleSorter();

        // When
        List<Note> resultNotes = sorter.sort(mockNotes, SortingOrder.DESC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note2)));
        assertThat(resultNotes.get(1), is(equalTo(note3)));
        assertThat(resultNotes.get(2), is(equalTo(note1)));
    }

    @Test
    public void contentSorterAscTest() {

        // Given
        // setUp() method
        Sorter<Note> sorter = new NotesContentSorter();

        // When
        List<Note> resultNotes = sorter.sort(mockNotes, SortingOrder.ASC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note3)));
        assertThat(resultNotes.get(1), is(equalTo(note1)));
        assertThat(resultNotes.get(2), is(equalTo(note2)));
    }

    @Test
    public void contentSorterDescTest() {

        // Given
        // setUp() method
        Sorter<Note> sorter = new NotesContentSorter();

        // When
        List<Note> resultNotes = sorter.sort(mockNotes, SortingOrder.DESC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note2)));
        assertThat(resultNotes.get(1), is(equalTo(note1)));
        assertThat(resultNotes.get(2), is(equalTo(note3)));
    }

    @Test
    public void createdTimeSorterAscTest() {

        // Given
        // setUp() method
        Sorter<Note> sorter = new NotesCreatedTimeSorter();

        // When
        List<Note> resultNotes = sorter.sort(mockNotes, SortingOrder.ASC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note2)));
        assertThat(resultNotes.get(1), is(equalTo(note1)));
        assertThat(resultNotes.get(2), is(equalTo(note3)));
    }

    @Test
    public void createdTimeSorterDescTest() {

        // Given
        // setUp() method
        Sorter<Note> sorter = new NotesCreatedTimeSorter();

        // When
        List<Note> resultNotes = sorter.sort(mockNotes, SortingOrder.DESC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note3)));
        assertThat(resultNotes.get(1), is(equalTo(note1)));
        assertThat(resultNotes.get(2), is(equalTo(note2)));
    }

    @Test
    public void modifiedTimeSorterAscTest() {

        // Given
        // setUp() method
        Sorter<Note> sorter = new NotesModifiedTimeSorter();

        // When
        List<Note> resultNotes = sorter.sort(mockNotes, SortingOrder.ASC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note2)));
        assertThat(resultNotes.get(1), is(equalTo(note3)));
        assertThat(resultNotes.get(2), is(equalTo(note1)));
    }

    @Test
    public void modifiedTimeSorterDescTest() {

        // Given
        // setUp() method
        Sorter<Note> sorter = new NotesModifiedTimeSorter();

        // When
        List<Note> resultNotes = sorter.sort(mockNotes, SortingOrder.DESC);

        // Then
        assertThat(resultNotes.get(0), is(equalTo(note1)));
        assertThat(resultNotes.get(1), is(equalTo(note3)));
        assertThat(resultNotes.get(2), is(equalTo(note2)));
    }
}
