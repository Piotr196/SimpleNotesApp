package com.simplenotesapp.simplenotesapp.sorting.notes.sorters;

import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.sorting.generic.Sorter;
import com.simplenotesapp.simplenotesapp.sorting.generic.SortingOrder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NotesTitleSorter implements Sorter<Note> {

    @Override
    public List<Note> sort(List<Note> notes, SortingOrder order) {
        Comparator<Note> comparator = Comparator.comparing(Note::getTitle);
        if (order != null && order.equals(SortingOrder.DESC)) {
            comparator = comparator.reversed();
        }
        return notes.stream().sorted(comparator).collect(Collectors.toList());
    }
}
