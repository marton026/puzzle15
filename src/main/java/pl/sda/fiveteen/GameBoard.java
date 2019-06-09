package pl.sda.javalub11.maven.game.fiveteen;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GameBoard {
    private final int emptyField = 0;
    int[][] fields = new int[4][4];

    public GameBoard() {
        int counter = 1;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                fields[row][col] = counter++;
            }
        }
        fields[3][3] = emptyField;
    }

    public boolean isSolved() {
        String result = "";
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                result += fields[row][col];
            }
        }
        return "1234567891011121314150".equals(result);
    }

    public void randomize() {
        for (int i = 0; i < 100; i++) {
            Coordinates emptyElementCoordinates = locateEmptyElement();
//            random int -> 1=góra, 2 = prawo, 3 = dół, 4 = lewo
            Optional<Coordinates> elementToBeMoved = getElementToBeMoved(emptyElementCoordinates);
            elementToBeMoved.ifPresent(element -> moveElement(element));
        }
    }

    public Optional<Coordinates> getElementToBeMoved(Coordinates emptyElementCoordinates) {
        Random random = new Random();
        int randomizationVector = random.nextInt(4) + 1;
        Coordinates elementToBeMooved = null;
        switch (randomizationVector) {
            case 1:
                if (emptyElementCoordinates.getRow() != 0) {
                    elementToBeMooved = new Coordinates(
                            emptyElementCoordinates.getRow() - 1,
                            emptyElementCoordinates.getCol());
                }
                break;
            case 2:
                if (emptyElementCoordinates.getCol() != 3) {
                    elementToBeMooved = new Coordinates(
                            emptyElementCoordinates.getRow(),
                            emptyElementCoordinates.getCol() + 1);
                }
                break;
            case 3:
                if (emptyElementCoordinates.getRow() != 3) {
                    elementToBeMooved = new Coordinates(
                            emptyElementCoordinates.getRow() + 1,
                            emptyElementCoordinates.getCol());
                }
                break;
            case 4:
                if (emptyElementCoordinates.getCol() != 0) {
                    elementToBeMooved = new Coordinates(
                            emptyElementCoordinates.getRow(),
                            emptyElementCoordinates.getCol() - 1);
                }
                break;
        }
        return Optional.ofNullable(elementToBeMooved);
    }

    private boolean moveElement(Coordinates blockToBeMoved) {
        Coordinates emptyElement = locateEmptyElement();
        int absCol = Math.abs(blockToBeMoved.getCol() - emptyElement.getCol());
        int absRow = Math.abs(blockToBeMoved.getRow() - emptyElement.getRow());
        if (absCol + absRow == 1) {
            swap(blockToBeMoved, emptyElement);
            return true;
        } else {
            return false;
        }
    }

    public void makeAMove(int direction) {
        boolean ifCorrect;
        do {
            Coordinates emptyElementCoordinates = locateEmptyElement();
//            random int -> 1=góra, 2 = prawo, 3 = dół, 4 = lewo
            int move = direction;
            Coordinates elementToBeMooved = null;
            ifCorrect = false;
            switch (move) {
                case 1:
                    if (emptyElementCoordinates.getRow() != 0) {
                        elementToBeMooved = new Coordinates(
                                emptyElementCoordinates.getRow() - 1,
                                emptyElementCoordinates.getCol());
                        System.out.println("Wykonano ruch do góry");
                    } else {
                        System.out.println("Ruch poza planszę");
                    }
                    break;
                case 2:
                    if (emptyElementCoordinates.getCol() != 3) {
                        elementToBeMooved = new Coordinates(
                                emptyElementCoordinates.getRow(),
                                emptyElementCoordinates.getCol() + 1);
                        System.out.println("Wykonano ruch w prawo");
                    } else {
                        System.out.println("Ruch poza planszę");
                    }
                    break;
                case 3:
                    if (emptyElementCoordinates.getRow() != 3) {
                        elementToBeMooved = new Coordinates(
                                emptyElementCoordinates.getRow() + 1,
                                emptyElementCoordinates.getCol());
                        System.out.println("Wykonano ruch w dół");
                    } else {
                        System.out.println("Ruch poza planszę");
                    }
                    break;
                case 4:
                    if (emptyElementCoordinates.getCol() != 0) {
                        elementToBeMooved = new Coordinates(
                                emptyElementCoordinates.getRow(),
                                emptyElementCoordinates.getCol() - 1);
                        System.out.println("Wykonano ruch w lewo");
                    } else {
                        System.out.println("Ruch poza planszę");
                    }
                    break;
                default: {
                    System.out.println("Nieprawidłowy ruch");
                    ifCorrect = true;
                    return;
                }
            }
            Optional<Coordinates> elementToBeMoved = Optional.ofNullable(elementToBeMooved);
            elementToBeMoved.ifPresent(element -> moveElement(element));
        } while (ifCorrect);

    }

    private void swap(Coordinates block1, Coordinates block2) {
        int tmp = fields[block1.getRow()][block1.getCol()];
        fields[block1.getRow()][block1.getCol()] = fields[block2.getRow()][block2.getCol()];
        fields[block2.getRow()][block2.getCol()] = tmp;
    }

    private Coordinates locateEmptyElement() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (fields[row][col] == emptyField) {
                    return new Coordinates(row, col);
                }
            }

        }
        throw new IllegalStateException("emptyField not found");
    }

    public void drawTheBoard() {
        String setRow = "ABCD";
        System.out.println("     1   2   3   4");
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                int i1 = fields[i][j];
                if (i1 == fields[i][0]) {
                    System.out.print(setRow.charAt(i) + "  |");
                }
                System.out.format("% -3d|", i1);
            }
            System.out.println();
        }
    }

    public long timeStringToMillis(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("1970-01-01 " + time);
        return date.getTime();

    }

    public void saveToFile(String fileName, String name, String timeToWin) {
        try {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            printWriter.print(name + "," + timeToWin + "\r\n");
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void openFile(String filename) throws FileNotFoundException {
        FileInputStream file = new FileInputStream(filename);
        String line;
        long minTime = 0;
        String bestPlayer = "";
        BufferedReader input = null;
        StringTokenizer stringTokenizer;
        String delimiter = ",";
        System.out.println("                   Wyniki gry:");
        try {
            input = new BufferedReader(new InputStreamReader(file));
            while ((line = input.readLine()) != null) {
                stringTokenizer = new StringTokenizer(line, delimiter);
                while (stringTokenizer.hasMoreTokens()) {
                    String nick = stringTokenizer.nextToken();
                    String time = stringTokenizer.nextToken();
                    System.out.println("Gracz: " + nick + " ukończył grę z czasem: " + time);
                    if (minTime > timeStringToMillis(time)) {
                        minTime = timeStringToMillis(time);
                        bestPlayer = nick;
                    }
                }
            }
            System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("Gracz: " + bestPlayer + " osiągnął najlepszy wynik: " +
                    DateFormat.getTimeInstance(DateFormat.MEDIUM).format(minTime));
            System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        } catch (IOException e) {
            System.out.println("Problem z odczytem pliku: " + filename);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                System.out.println("Problem podczas zamknięcia pliku " + filename);
            }
        }
    }
}
