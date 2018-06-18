package com.simplenotesapp.simplenotesapp.sorting.notes;

import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.sorting.generic.Sorter;
import com.simplenotesapp.simplenotesapp.sorting.generic.SorterFactory;
import com.simplenotesapp.simplenotesapp.sorting.notes.sorters.NotesContentSorter;
import com.simplenotesapp.simplenotesapp.sorting.notes.sorters.NotesCreatedTimeSorter;
import com.simplenotesapp.simplenotesapp.sorting.notes.sorters.NotesModifiedTimeSorter;
import com.simplenotesapp.simplenotesapp.sorting.notes.sorters.NotesTitleSorter;
import org.springframework.stereotype.Component;

@Component
public class NotesSorterFactory implements SorterFactory<Note, NotesSortingSubject> {

    @Override
    public Sorter<Note> createSorter(NotesSortingSubject sortingSubject) {

        switch (sortingSubject) {
            case TITLE:
                return new NotesTitleSorter();
            case CONTENT:
                return new NotesContentSorter();
            case CREATED:
                return new NotesCreatedTimeSorter();
            case MODIFIED:
                return new NotesModifiedTimeSorter();
            default:
                throw new UnsupportedOperationException("No sorter exists for provided sorting subject.");
        }
    }
}
