package pl.sda.javalub11.maven.game.fiveteen;

public class Coordinates {
    private int row;
    private int col;

    public Coordinates(int row, int col) {
        if (row < 0 || row > 3 | col < 0 || col > 3) {
            throw new IllegalStateException("Invalid Coordinates");
        }
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {

        return col;
    }
}
