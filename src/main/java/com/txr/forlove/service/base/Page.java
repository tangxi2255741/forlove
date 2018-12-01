package com.txr.forlove.service.base;

import org.springframework.core.convert.converter.Converter;

public interface Page<T> extends Slice<T> {
    int getTotalPages();

    long getTotalElements();

    <S> Page<S> map(Converter<? super T,? extends S> var1);
}
