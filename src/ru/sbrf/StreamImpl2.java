package ru.sbrf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by svetlana on 18.10.16.
 */
public class StreamImpl2<T> implements Stream<T> {

    private Iterator<? extends T> itr;
    private BiConsumer<T, Consumer<Object>> consumer;

    StreamImpl2(Iterator<? extends T> itr) {
        this.itr = itr;
        this.consumer = (e, c) -> c.accept(e);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Stream<T> filter(Predicate<? super T> p) {
        final BiConsumer<T, Consumer<Object>> prev = consumer;
        consumer = (e, c) -> prev.accept(e, ee -> {
            if (p.test((T) ee)) c.accept(ee);
        });
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> Stream<U> transform(Function<? super T, ? extends U> mapper) {
        final BiConsumer<T, Consumer<Object>> prev = consumer;
        consumer = (e, c) -> prev.accept(e, ee -> c.accept(mapper.apply((T) ee)));
        return (Stream<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> key, Function<? super T, ? extends V> value) {
        final Map<K, V> result = new HashMap<>();
        while (itr.hasNext()) {
            consumer.accept(itr.next(), e -> result.put(key.apply((T) e), value.apply((T) e)));
        }
        return result;
    }
}