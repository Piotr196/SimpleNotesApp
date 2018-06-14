package com.simplenotesapp.simplenotesapp.service;


import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.repository.NoteRepository;
import com.simplenotesapp.simplenotesapp.sorting.generic.Sorter;
import com.simplenotesapp.simplenotesapp.sorting.generic.SorterFactory;
import com.simplenotesapp.simplenotesapp.sorting.generic.SortingOrder;
import com.simplenotesapp.simplenotesapp.sorting.notes.NotesSortingSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    SorterFactory<Note, NotesSortingSubject> notesSorterFactory;

    @Transactional
    public Note save(final Note note) {
        note.getUsers().forEach(user -> user.addNote(note));
        return noteRepository.save(note);
    }

    @Transactional
    public void delete(final Note note) {
        note.getUsers().forEach(user -> user.removeNote(note));
        noteRepository.delete(note);
    }

    @Transactional
    public Note update(final Note note) {
        Note updatedNote = findOneById(note.getId());

        updatedNote.setTitle(note.getTitle());
        updatedNote.setContent(note.getContent());
        updatedNote.getUsers().forEach(user -> user.removeNote(updatedNote));
        updatedNote.setUsers(note.getUsers());
        updatedNote.getUsers().forEach(user -> user.addNote(updatedNote));

        return updatedNote;
    }

    public List<Note> findAll(NotesSortingSubject sortingSubject, SortingOrder sortingOrder) {
        List<Note> notes = noteRepository.findAll();
        if (sortingSubject != null) {
            Sorter<Note> sorter = notesSorterFactory.createSorter(sortingSubject);
            notes = sorter.sort(notes, sortingOrder != null ? sortingOrder : SortingOrder.ASC);
        }
        return notes;
    }

    public Note findOneById(final Long id) {

        return noteRepository.findOneById(id).get();
    }

    public List<Note> findAllById(final Set<Long> ids) {
        return noteRepository.findAllById(ids);
    }

    public Set<Note> findAllByTitle(final String title) {
        return noteRepository.findAllByTitle(title);
    }

    public Note findOneByIdNullable(final Long id) {
        return noteRepository.findOneById(id).orElse(null);
    }
}
