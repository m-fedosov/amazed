package backend.academy.amazed.skeleton;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleTest {

    @Test
    public void testInput_ValidInputWithinRange() {
        // Симуляция ввода "5"
        String input = "5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Тестируемый объект
        Console console = new Console();
        int result = console.input(1, 10);

        assertEquals(5, result);
    }

    @Test
    public void testInput_InvalidInputThenValidInput() {
        // Симуляция некорректного ввода "abc" и корректного "7"
        String input = "abc\n7\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Тестируемый объект
        Console console = new Console();
        int result = console.input(1, 10);

        assertEquals(7, result);
    }

    @Test
    public void testInput_OutOfRangeInputThenValidInput() {
        // Симуляция ввода чисел за пределами диапазона "15" и корректного "8"
        String input = "15\n8\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Тестируемый объект
        Console console = new Console();
        int result = console.input(1, 10);

        assertEquals(8, result);
    }
}
