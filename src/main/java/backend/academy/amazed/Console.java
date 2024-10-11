package backend.academy.amazed;

import java.util.Scanner;

public class Console {
    Scanner scanner;

    Console() {
        scanner = new Scanner(System.in);
    }

    @SuppressWarnings("RegexpSinglelineJava")
    public void print(String text) {
        System.out.println(text);
    }

    @SuppressWarnings("RegexpSinglelineJava")
    public int input(int minValue, int maxValue) {
        int value = -1;
        String input;
        while (value < minValue || value > maxValue) {
            input = scanner.nextLine().trim();
            if (!input.isEmpty() && input.matches("\\d+")) {
                value = Integer.parseInt(input);
            }
            if (value < minValue || value > maxValue) {
                System.out.println("Нужно ввести целое число от " + minValue + " до " + maxValue);
            }
        }
        return value;
    }
}
