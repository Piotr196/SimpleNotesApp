package com.simplenotesapp.simplenotesapp.sorting.generic;

import java.util.List;

public interface Sorter<T> {

    List<T> sort(List<T> items, SortingOrder order);
}
