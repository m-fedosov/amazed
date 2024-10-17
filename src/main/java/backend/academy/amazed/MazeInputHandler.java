package backend.academy.amazed;

import lombok.Getter;

public class MazeInputHandler {
    private final Console console;
    @Getter
    private final int height;
    @Getter
    private final int width;

    MazeInputHandler(Console console, int minSize, int maxSize) {
        this.console = console;
        this.height = promptForDimension("Высота лабиринта", minSize, maxSize);
        this.width = promptForDimension("Ширина лабиринта", minSize, maxSize);
    }

    private int promptForDimension(String prompt, int min, int max) {
        console.print(prompt);
        return console.input(min, max);
    }

    int promptForAlgorithm(String prompt, String firstOption, String secondOption) {
        String algorithmChoiceText = String.format("%s\n1. %s\n2. %s", prompt, firstOption, secondOption);
        console.print(algorithmChoiceText);
        return console.input(1, 2);
    }

    MazeGrid.Cell promptForCell(String prompt) {
        console.print(prompt);
        int y = console.input(0, height - 1);
        int x = console.input(0, width - 1);
        return new MazeGrid.Cell(0, y, x);
    }
}

