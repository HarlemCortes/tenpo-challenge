package com.harlem.tenpo.sumservice.util;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomPageImpl<T> extends PageImpl<T> {

    public CustomPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    @Override
    public String toString() {
        return "Current Page: " + (getNumber() + 1) +
                ", Elements per Page: " + getSize() +
                ", Total Elements: " + getTotalElements() +
                ", Total Pages: " + getTotalPages();
    }
}