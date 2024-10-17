package backend.academy.amazed;

import lombok.Getter;

public class MazeSetup {
    private static final int MIN_SIZE = 4;
    private static final int MAX_SIZE = 40;
    private static final int PRIM_ALGORITHM = 1;
    private static final int KRUSKAL_ALGORITHM = 2;
    private static final int BFS_ALGORITHM = 1;
    private static final int A_STAR_ALGORITHM = 2;

    private int width;
    private int height;

    @Getter
    private MazeGrid mazeGrid;

    public void setUp() throws Exception {
        Console console = new Console();
        printWelcomeMessage(console);

        height = promptForDimension(console, "Высота генерируемого лабиринта");
        width = promptForDimension(console, "Ширина генерируемого лабиринта");
        mazeGrid = new MazeGrid(height, width);

        int algorithmChoice = promptForAlgorithm(console, "Выберите алгоритм генерации лабиринта:", "Алгоритм Прима", "Алгоритм Краскала");
        generateMaze(algorithmChoice);

        MazeGrid.Cell startCell = promptForCell(console, "Начало лабиринта:");
        MazeGrid.Cell endCell = promptForCell(console, "Конец лабиринта:");

        while (startCell.equals(endCell)) {
            console.print("Начальная и конечная точки совпадают. Пожалуйста, выберите другие координаты для конца лабиринта.");
            endCell = promptForCell(console, "Конец лабиринта:");
        }

        mazeGrid.startCell(startCell);
        mazeGrid.endCell(endCell);

        int pathAlgorithmChoice = promptForAlgorithm(console, "Выберите алгоритм поиска пути:", "BFS", "A*");
        findPath(pathAlgorithmChoice);

        console.print(mazeGrid.draw());
    }

    private void printWelcomeMessage(Console console) {
        String welcomeText = """
            Добро пожаловать!
            Это консольная программа для генерации лабиринтов и поиска пути в них.
            Программа способна генерировать лабиринты различной сложности и размеров,
            а также предоставляет несколько методов поиска пути
            от заданной точки А (начала) к точке Б (конца).

            Для генерации лабиринта нужно ввести следующие параметры.
            """;
        console.print(welcomeText);
    }

    private int promptForDimension(Console console, String prompt) {
        console.print(prompt);
        return console.input(MIN_SIZE, MAX_SIZE);
    }

    private int promptForAlgorithm(Console console, String prompt, String firstOption, String secondOption) {
        String algorithmChoiceText = String.format("""
            %s

            1. %s
            2. %s
            """, prompt, firstOption, secondOption);
        console.print(algorithmChoiceText);
        return console.input(1, 2);
    }

    private MazeGrid.Cell promptForCell(Console console, String prompt) {
        console.print(prompt);
        int y = console.input(0, height - 1);
        int x = console.input(0, width - 1);
        return new MazeGrid.Cell(0, y, x);
    }

    private void generateMaze(int algorithmChoice) throws Exception {
        MazeGenerator generator = switch (algorithmChoice) {
            case PRIM_ALGORITHM -> new MazeGeneratorPrim();
            case KRUSKAL_ALGORITHM -> new MazeGeneratorKruskal();
            default -> throw new Exception("Заданы неправильные константы в алгоритмах генерации лабиринта");
        };
        mazeGrid = generator.generate(mazeGrid);
    }

    private void findPath(int pathAlgorithmChoice) throws Exception {
        PathFinder pathFinder = switch (pathAlgorithmChoice) {
            case BFS_ALGORITHM -> new PathFinderBFS();
            case A_STAR_ALGORITHM -> new PathFinderAStar();
            default -> throw new Exception("Заданы неправильные константы в алгоритмах поиска пути");
        };
        mazeGrid = pathFinder.find(mazeGrid);
    }
}
