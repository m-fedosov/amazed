package backend.academy.amazed;

public class MazeManager {
    MazeGrid generateMaze(int algorithmChoice, MazeGrid mazeGrid) throws Exception {
        MazeGenerator generator = switch (algorithmChoice) {
            case 1 -> new MazeGeneratorPrim();
            case 2 -> new MazeGeneratorKruskal();
            default -> throw new Exception("Неверный алгоритм генерации");
        };
        return generator.generate(mazeGrid);
    }
}
