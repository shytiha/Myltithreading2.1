package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    final static int NUMBER_THREAD = 1000;
    final static String LETTERS = "RLRFR";
    final static int LENGTH = 100;

    public static final Map<Integer, Integer> size = new HashMap<>();


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) {

        for (int i = 0; i < NUMBER_THREAD; i++) {
            new Thread(() -> {
                String route = generateRoute(LETTERS, LENGTH);
                int r = (int) route.chars().filter(c -> c == 'R').count();

                synchronized (size) {
                    if (size.containsKey(r)) {
                        size.put(r, size.get(r) + 1);

                    } else {
                        size.put(r, 1);
                    }
                }

            }).start();
        }

        Map.Entry<Integer, Integer> max = size
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();

        System.out.println("Самое частое количество повторений буквы R " + max.getKey() + " (встретилось " + max.getValue() + " раз)");

        System.out.println("Другие размеры: ");
        size
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println(e.getKey() + " (" + e.getValue() + " раз)"));


    }
}