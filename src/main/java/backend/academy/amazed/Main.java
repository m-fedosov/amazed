package backend.academy.amazed;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        MazeGenerator mazeGenerator = new MazeGenerator();
        mazeGenerator.setUp();
        mazeGenerator.generate();
        mazeGenerator.draw();
    }
}
