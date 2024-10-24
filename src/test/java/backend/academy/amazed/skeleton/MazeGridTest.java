package backend.academy.amazed.skeleton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MazeGridTest {

    private MazeGrid mazeGrid;

    @BeforeEach
    void setUp() {
        // Инициализируем лабиринт 4x4
        mazeGrid = new MazeGrid(4, 4);

        // Создаем клетки с определенными координатами и типами
        MazeGrid.Cell cell00 = new MazeGrid.Cell(2, 0, 0);
        MazeGrid.Cell cell01 = new MazeGrid.Cell(2, 0, 1);
        MazeGrid.Cell cell02 = new MazeGrid.Cell(2, 0, 2);
        MazeGrid.Cell cell03 = new MazeGrid.Cell(2, 0, 3);
        MazeGrid.Cell cell10 = new MazeGrid.Cell(2, 1, 0);
        MazeGrid.Cell cell11 = new MazeGrid.Cell(2, 1, 1);
        MazeGrid.Cell cell12 = new MazeGrid.Cell(2, 1, 2);
        MazeGrid.Cell cell13 = new MazeGrid.Cell(2, 1, 3);
        MazeGrid.Cell cell20 = new MazeGrid.Cell(2, 2, 0);
        MazeGrid.Cell cell21 = new MazeGrid.Cell(2, 2, 1);
        MazeGrid.Cell cell22 = new MazeGrid.Cell(2, 2, 2);
        MazeGrid.Cell cell23 = new MazeGrid.Cell(2, 2, 3);
        MazeGrid.Cell cell30 = new MazeGrid.Cell(2, 3, 0);
        MazeGrid.Cell cell31 = new MazeGrid.Cell(2, 3, 1);
        MazeGrid.Cell cell32 = new MazeGrid.Cell(2, 3, 2);
        MazeGrid.Cell cell33 = new MazeGrid.Cell(2, 3, 3);

        // Зададим правильные стены
        cell00.eastWall(false);
        cell01.westWall(false);
        cell01.eastWall(false);
        cell01.southWall(false);
        cell02.westWall(false);
        cell02.southWall(false);
        cell03.southWall(false);

        cell10.eastWall(false);
        cell11.westWall(false);
        cell11.northWall(false);
        cell12.northWall(false);
        cell12.eastWall(false);
        cell12.southWall(false);
        cell13.westWall(false);
        cell13.northWall(false);

        cell20.northWall(false);
        cell20.southWall(false);
        cell21.eastWall(false);
        cell22.northWall(false);
        cell22.westWall(false);
        cell22.southWall(false);
        cell22.eastWall(false);
        cell23.westWall(false);
        cell23.southWall(false);

        cell30.northWall(false);
        cell30.eastWall(false);
        cell31.westWall(false);
        cell32.northWall(false);
        cell33.northWall(false);

        // Зададим путь
        cell00.partOfPath(true);
        cell01.partOfPath(true);
        cell02.partOfPath(true);
        cell12.partOfPath(true);
        cell22.partOfPath(true);
        cell23.partOfPath(true);
        cell33.partOfPath(true);

        // Задаем клетки через метод setCell
        mazeGrid.setCell(cell00);
        mazeGrid.setCell(cell01);
        mazeGrid.setCell(cell02);
        mazeGrid.setCell(cell03);
        mazeGrid.setCell(cell10);
        mazeGrid.setCell(cell11);
        mazeGrid.setCell(cell12);
        mazeGrid.setCell(cell13);
        mazeGrid.setCell(cell20);
        mazeGrid.setCell(cell21);
        mazeGrid.setCell(cell22);
        mazeGrid.setCell(cell23);
        mazeGrid.setCell(cell30);
        mazeGrid.setCell(cell31);
        mazeGrid.setCell(cell32);
        mazeGrid.setCell(cell33);

        // Зададим старт и финиш
        mazeGrid.startCell(cell00);
        mazeGrid.endCell(cell33);

    }

    @Test
    void testDraw() {
        // Проверим отрисовку лабиринта
        String expectedOutput = """
            ⬜️⬜️⬜️⬜️⬜️⬜️⬜️⬜️⬜️
            ⬜️🟩🟨🟨🟨🟨⬜️⬛️⬜️
            ⬜️⬜️⬜️⬛️⬜️🟨⬜️⬛️⬜️
            ⬜️⬛️⬛️⬛️⬜️🟨⬛️⬛️⬜️
            ⬜️⬛️⬜️⬜️⬜️🟨⬜️⬜️⬜️
            ⬜️⬛️⬜️⬛️⬛️🟨🟨🟨⬜️
            ⬜️⬛️⬜️⬜️⬜️⬛️⬜️🟨⬜️
            ⬜️⬛️⬛️⬛️⬜️⬛️⬜️🟥⬜️
            ⬜️⬜️⬜️⬜️⬜️⬜️⬜️⬜️⬜️
            """;

        assertEquals(expectedOutput, mazeGrid.draw());
    }
}

