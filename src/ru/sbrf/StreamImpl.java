package ru.sbrf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by svetlana on 18.10.16.
 */
public class StreamImpl<T> implements Stream<T> {

    private Iterator<?> itr;

    StreamImpl(Iterator<? extends T> itr) {
        this.itr = itr;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Stream<T> filter(Predicate<? super T> p) {
        final Iterator<? extends T> curItr = (Iterator<? extends T>) itr;
        itr = new Iterator<T>() {
            private T current;

            @Override
            public boolean hasNext() {
                while (curItr.hasNext()) {
                    current = curItr.next();
                    if (p.test(current)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public T next() {
                return current;
            }
        };
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> Stream<U> transform(Function<? super T, ? extends U> mapper) {
        final Iterator<? extends T> curItr = (Iterator<? extends T>) itr;
        itr = new Iterator<U>() {
            @Override
            public boolean hasNext() {
                return curItr.hasNext();
            }

            @Override
            public U next() {
                return mapper.apply(curItr.next());
            }
        };
        return (Stream<U>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> key, Function<? super T, ? extends V> value) {
        final Map<K, V> result = new HashMap<>();
        final Iterator<? extends T> curItr = (Iterator<? extends T>) itr;
        while (curItr.hasNext()) {
            T o = curItr.next();
            result.put(key.apply(o), value.apply(o));
        }
        return result;
    }
}