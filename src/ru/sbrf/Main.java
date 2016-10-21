package ru.sbrf;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {
        {
            Map<Integer, Integer> r = Stream.of2(Arrays.asList("a", "b"))
                    .filter(s -> s.contains("a"))
                    .transform(s -> s.length())
                    .toMap(Function.identity(), Function.identity());

            System.out.println(Objects.toString(r));
        }

        {
            Map<String, String> r = Stream.of2(Arrays.asList("a", "b"))
                    .filter(s -> s.contains("a"))
                    .toMap(Function.identity(), Function.identity());

            System.out.println(Objects.toString(r));
        }

        {
            Map<String, String> r = Stream.of2(Arrays.asList((String)null, "bdsdd"))
                    .toMap(Function.identity(), Function.identity());

            System.out.println(Objects.toString(r));
        }
    }
}