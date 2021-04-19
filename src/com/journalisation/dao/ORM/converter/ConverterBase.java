package com.journalisation.dao.ORM.converter;

public interface ConverterBase<T> {
    T parse(Object d);
}
