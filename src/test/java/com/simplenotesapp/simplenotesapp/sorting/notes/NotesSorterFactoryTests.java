package com.simplenotesapp.simplenotesapp.sorting.notes;

import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.sorting.generic.Sorter;
import com.simplenotesapp.simplenotesapp.sorting.generic.SorterFactory;
import com.simplenotesapp.simplenotesapp.sorting.notes.sorters.NotesContentSorter;
import com.simplenotesapp.simplenotesapp.sorting.notes.sorters.NotesCreatedTimeSorter;
import com.simplenotesapp.simplenotesapp.sorting.notes.sorters.NotesModifiedTimeSorter;
import com.simplenotesapp.simplenotesapp.sorting.notes.sorters.NotesTitleSorter;
import org.junit.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class NotesSorterFactoryTests {

    @Test
    public void createTitleSorterTest() {

        // Given
        SorterFactory<Note, NotesSortingSubject> notesSorterFactory = new NotesSorterFactory();
        Sorter<Note> sorter;

        // When
        sorter = notesSorterFactory.createSorter(NotesSortingSubject.TITLE);

        // Then
        assertThat(sorter, instanceOf(NotesTitleSorter.class));
    }

    @Test
    public void createContentSorterTest() {

        // Given
        SorterFactory<Note, NotesSortingSubject> notesSorterFactory = new NotesSorterFactory();
        Sorter<Note> sorter;

        // When
        sorter = notesSorterFactory.createSorter(NotesSortingSubject.CONTENT);

        // Then
        assertThat(sorter, instanceOf(NotesContentSorter.class));
    }

    @Test
    public void createCreatedTimeSorterTest() {

        // Given
        SorterFactory<Note, NotesSortingSubject> notesSorterFactory = new NotesSorterFactory();
        Sorter<Note> sorter;

        // When
        sorter = notesSorterFactory.createSorter(NotesSortingSubject.CREATED);

        // Then
        assertThat(sorter, instanceOf(NotesCreatedTimeSorter.class));
    }

    @Test
    public void createModifiedTimeSorterTest() {

        // Given
        SorterFactory<Note, NotesSortingSubject> notesSorterFactory = new NotesSorterFactory();
        Sorter<Note> sorter;

        // When
        sorter = notesSorterFactory.createSorter(NotesSortingSubject.MODIFIED);

        // Then
        assertThat(sorter, instanceOf(NotesModifiedTimeSorter.class));
    }
}
