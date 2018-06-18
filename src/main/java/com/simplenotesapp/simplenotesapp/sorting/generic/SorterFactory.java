package com.simplenotesapp.simplenotesapp.sorting.generic;

public interface SorterFactory<T, S extends SortingSubject> {

    Sorter<T> createSorter(S sortingSubject);
}
