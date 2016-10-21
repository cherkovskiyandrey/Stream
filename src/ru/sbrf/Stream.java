package ru.sbrf;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<T> {

    static <T> Stream<T> of(Collection<? extends T> source) {
        return new StreamImpl<>(source.iterator());
    }

    static <T> Stream<T> of2(Collection<? extends T> source) {
        return new StreamImpl2<>(source.iterator());
    }

    Stream<T> filter(Predicate<? super T> predicate);

    <R> Stream<R> transform(Function<? super T, ? extends R> transformer);

    <K, V> Map<K, V> toMap(Function<? super T, ? extends K> key, Function<? super T, ? extends V> value);
}
