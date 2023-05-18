package com.minibank.models.comparator;

import com.minibank.models.Transaction;

import java.util.Comparator;

public class ComparatorDate implements Comparator<Transaction> {
    @Override
    public int compare(Transaction o1, Transaction o2) {
        return o1.getDateTime().compareTo(o2.getDateTime());
    }
}
