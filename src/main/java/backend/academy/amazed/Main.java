package backend.academy.amazed;

import backend.academy.amazed.skeleton.MazeProgram;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) throws Exception {
        MazeProgram mazeProgram = new MazeProgram();
        mazeProgram.run();
    }
}
