package backend.academy.amazed.mazegenerator;

import backend.academy.amazed.skeleton.MazeGrid;

public class MazeGeneratorManager {
    public MazeGrid generateMaze(int algorithmChoice, MazeGrid mazeGrid) throws Exception {
        MazeGenerator generator = switch (algorithmChoice) {
            case 1 -> new MazeGeneratorPrim();
            case 2 -> new MazeGeneratorKruskal();
            default -> throw new Exception("Неверный алгоритм генерации");
        };
        return generator.generate(mazeGrid);
    }
}
