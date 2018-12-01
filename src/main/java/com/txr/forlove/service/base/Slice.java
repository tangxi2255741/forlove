package com.txr.forlove.service.base;


import org.springframework.core.convert.converter.Converter;

public interface Slice<T> extends Iterable<T> {
    int getNumber();
    int getSize();
    <S>Slice<S> map(Converter<? super T, ? extends S> var1);
}
