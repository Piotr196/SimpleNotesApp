package com.simplenotesapp.simplenotesapp.repository;

import com.simplenotesapp.simplenotesapp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface NoteRepository extends JpaRepository<Note, Long> {

<<<<<<< HEAD
    Optional<Note> findOneById(final Long id);
=======
    Optional<Note> findOneById(final long id);
>>>>>>> 1ce1e4d3788595b66cf5ffb6aabab211d6cdf3e2

    Set<Note> findAllByTitle(final String title);

}