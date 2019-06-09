package pl.sda.javalub11.maven.game.fiveteen;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Scanner;

public class Puzzle15 {
    public static void main(String[] args) throws FileNotFoundException {
        String name;
        long time;
        long endOfTime;
        final String pathToFile = "C:\\Install\\DOKUMENTY\\KURS\\gra.txt";
        GameBoard gameBoard = new GameBoard();
        gameBoard.openFile(pathToFile);
        Scanner odczyt = new Scanner(System.in);
        System.out.print("Podaj imię gracza: ");
        name = odczyt.next();
        time = System.currentTimeMillis();
        String date = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(time);
        System.out.println("Czas rozpoczęcia gry: " + date);
        GameBoard gameBoard1 = new GameBoard();
        gameBoard1.drawTheBoard();
        gameBoard1.isSolved();
        System.out.println();
        gameBoard1.randomize();
        gameBoard1.drawTheBoard();
        System.out.println("Wprowadź prawidłowy ruch :");
        do {
            System.out.println("1 = góra , 2 = prawo , 3 = dół , 4 = lewo ");
            int direction = odczyt.nextInt();
            gameBoard1.makeAMove(direction);
            gameBoard1.drawTheBoard();
        } while (gameBoard1.isSolved() == false);
        endOfTime = System.currentTimeMillis();
        System.out.println("Czas zakończenia gry: " + DateFormat.getTimeInstance(DateFormat.MEDIUM).format(endOfTime));
        long timeToWin = endOfTime - time - 3600000;
        date = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(timeToWin);
        gameBoard1.saveToFile(pathToFile, name, date);
        System.out.println();
        gameBoard.openFile(pathToFile);
    }

}
