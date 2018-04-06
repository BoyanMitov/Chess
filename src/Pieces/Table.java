package Pieces;

import java.util.ArrayList;

public class Table {

    private static PlayingPiece[][] table = new PlayingPiece[8][8];
    private static PlayingPiece empty = new PlayingPiece("none");
    private static ArrayList<PlayingPiece> aliveWhites = new ArrayList<>();
    private static ArrayList<PlayingPiece> aliveBlacks = new ArrayList<>();
    private static PlayingPiece kingWhite;
    private static PlayingPiece kingBlack;


    public Table() {
        createTable();
    }

    public static PlayingPiece[][] getTable(){
        return table;
    }

    public static void setStartingPosition(int x, int y, PlayingPiece piece) {
        piece.setX(x);
        piece.setY(y);
        table[x][y] = piece;

        if (piece.getColor().equals("white") && piece.getSymbol() != '\u2654') {
            aliveWhites.add(piece);
        } else if (piece.getColor().equals("black") && piece.getSymbol() != '\u265A') {
            aliveBlacks.add(piece);
        }
    }

    public static void createTable() {
        empty.setSymbol('\u3000');

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (table[row][col] == null)
                    table[row][col] = empty;
            }
        }
    }

    public static void printTable() {
        System.out.println("\u3000  0\u30001\u30002\u30003\u30004\u30005\u30006\u30007");

        for (int row = 0; row < 8; row++) {
            System.out.print(row + " ");
            System.out.print("|");
            for (int col = 0; col < 8; col++) {
                System.out.print(table[row][col].getSymbol() + "|");

            }
            System.out.println();
        }
    }

    public static void play(String color, String currentColor, int fromX, int fromY, int toX, int toY) {
        if (currentColor.equals(color)) {
            if (table[fromX][fromY].moveIsLegal(toX, toY)) {
                if (!getObject(toX, toY).getColor().equals(color)) {
                    takePiece(toX, toY);
                }
                table[fromX][fromY].move(toX, toY);
                PlayingPiece swap = table[fromX][fromY];
                table[fromX][fromY] = table[toX][toY];
                table[toX][toY] = swap;
            } else {
                System.out.println("Can't go there!");
//                Pieces.Play.turn--;
            }
        } else if(!color.equals("none")){
            System.out.println("It's not your turn!");
//            Pieces.Play.turn--;
        }else {
            System.out.println("That's empty!Try again.");
//            Pieces.Play.turn--;
        }
    }

    public static boolean canTakePiece(int x, int y, PlayingPiece piece) {
        if ((getObject(x, y) == getEmptyObject())
                || (!getObject(x, y).getColor().equals(piece.getColor()))) {
            piece.getPathX().add(x);
            piece.getPathY().add(y);
            return true;
        } else {
            piece.getPathX().clear();
            piece.getPathY().clear();
            return false;
        }
    }

    public static void takePiece(int x, int y) {
        if (table[x][y].getColor().equals("white")) {
            aliveWhites.remove(table[x][y]);
        } else {
            aliveBlacks.remove(table[x][y]);
        }

        table[x][y] = empty;
    }

    public static boolean isChessMateWhites(int kingX, int kingY) {
        return isChessMate(kingX, kingY, aliveBlacks, aliveWhites);
    }

    public static boolean isChessMateBlacks(int kingX, int kingY) {
        return isChessMate(kingX, kingY, aliveWhites, aliveBlacks);
    }

    private static boolean isChessMate(int x, int y, ArrayList<PlayingPiece> enemy
            , ArrayList<PlayingPiece> ally) {
        boolean hasOpenSpace = false;
        for (int row = x - 1; row <= x + 1; row++) {
            for (int col = y - 1; col <= y + 1; col++) {
                if (row >= 0 && col >= 0 && row <= 7 && col <= 7) {
                    //System.out.println("x = "+row+" y = "+col);
                    if (table[row][col] == empty) {
                        hasOpenSpace = true;
                        temporaryMoveKing(row, col, x, y);

                        if (row != x || col != y) {
                            //System.out.println(!enemyCanReachChessMate(row, col, enemy));
                            if (!enemyCanReachChessMate(row, col, enemy)) {
                                //table[x][y] = table[row][col];
                                //table[row][col] = empty;
                                moveKingBack(row, col, x, y);
                                return false;
                            }
                        } else {
                            if (ally.size() > 1 && !enemyCanReachChessMate(row, col, enemy)) {
                                //table[x][y] = table[row][col];
                                //table[row][col] = empty;
                                moveKingBack(row, col, x, y);
                                return false;
                            }
                        }
                        if (table[x][y] == empty) {
                            //table[x][y] = table[row][col];
                            //table[row][col] = empty;
                            moveKingBack(row, col, x, y);
                        }
                    } else if (!table[row][col].getColor().equals(table[x][y].getColor())) {
                        hasOpenSpace=true;
                        PlayingPiece king = table[x][y];
                        table[x][y] = empty;
                        PlayingPiece takenEnemy = table[row][col];
                        enemy.remove(table[row][col]);
                        table[row][col] = king;

                        if (!enemyCanReachChessMate(row, col, enemy)) {
                            //table[x][y] = king;
                            //table[row][col] = takenEnemy;
                            moveKingBack(king, takenEnemy,enemy, row, col, x, y);
                            return false;
                        }
                        //table[x][y] = king;
                        //table[row][col] = takenEnemy;
                        moveKingBack(king, takenEnemy,enemy, row, col, x, y);
                    }
                }
            }
        }
        if(!hasOpenSpace){
            return false;
        }
        return true;
    }

    private static void temporaryMoveKing(int row, int col, int kingX, int kingY) {
        table[row][col] = table[kingX][kingY];
        table[kingX][kingY] = empty;
    }

    private static void moveKingBack(int row, int col, int kingX, int kingY) {
        table[kingX][kingY] = table[row][col];
        table[row][col] = empty;
    }

    private static void moveKingBack(PlayingPiece king, PlayingPiece takenEnemy
            ,ArrayList<PlayingPiece> enemies, int row, int col, int kingX, int kingY) {
        table[kingX][kingY] = king;
        table[row][col] = takenEnemy;
        enemies.add(takenEnemy);

    }

    private static boolean allyOrEnemyCanReach(int x, int y, ArrayList<PlayingPiece> team) {
        for (int i = 0; i < team.size(); i++) {
            if (team.get(i).moveIsLegal(x, y)) {
                return true;
            }
        }
        return false;
    }

    public static boolean enemyCanReachChessMate(int x, int y, ArrayList<PlayingPiece> enemy) {
        for (int i = 0; i < enemy.size(); i++) {
            if (enemy.get(i).moveIsLegal(x, y) && !allyCanSaveKing(enemy.get(i), enemy)) {
                return true;
            }
        }
        return false;
    }

    private static boolean allyCanSaveKing(PlayingPiece enemy, ArrayList<PlayingPiece> enemies) {
        ArrayList<PlayingPiece> ally;
        int allyX;
        int allyY;
        int enemyX;
        int enemyY;
        PlayingPiece kingAlly;
        enemy.getPathX().add(enemy.getX());
        enemy.getPathY().add(enemy.getY());

        if (enemy.getColor().equals("white")) {
            kingAlly = getKingBlack();
            ally = aliveBlacks;
        } else {
            kingAlly = getKingWhite();
            ally = aliveWhites;
        }

        for (int i = 0; i < ally.size(); i++) {
            for (int j = 0; j < enemy.getPathX().size(); j++) {
                allyX = ally.get(i).getX();
                allyY = ally.get(i).getY();

                if (enemy.getPathX().get(j) != kingAlly.getX()
                        || enemy.getPathY().get(j) != kingAlly.getY()) {
                    enemyX = enemy.getPathX().get(j);
                    enemyY = enemy.getPathY().get(j);
                } else {
                    continue;
                }
                PlayingPiece.setIsOnlyTesting(true);
                if (ally.get(i).moveIsLegal(enemyX, enemyY)) {
                    /*
                    PlayingPiece swap = table[allyX][allyY];
                    table[allyX][allyY] = table[enemyX][enemyY];
                    ally.get(i).move(enemyX, enemyY);
                    table[enemyX][enemyY] = swap;
                    */
                    tempMoveAlly(ally.get(i), allyX, allyY, enemyX, enemyY);

                    if (!allyOrEnemyCanReach(kingAlly.getX(), kingAlly.getY(), enemies)) {
                        //System.out.println("saved you"+ally.get(i).getSymbol());
                        /*
                        swap = table[allyX][allyY];
                        table[allyX][allyY] = table[enemyX][enemyY];
                        ally.get(i).move(allyX, allyY);
                        table[enemyX][enemyY] = swap;
                        */
                        moveAllyBack(ally.get(i), allyX, allyY, enemyX, enemyY);
                        return true;
                    }

                    /*
                    swap = table[allyX][allyY];
                    table[allyX][allyY] = table[enemyX][enemyY];
                    ally.get(i).move(allyX, allyY);
                    table[enemyX][enemyY] = swap;
                    */
                    moveAllyBack(ally.get(i), allyX, allyY, enemyX, enemyY);
                }
                PlayingPiece.setIsOnlyTesting(false);
            }
        }
        return false;
    }

    private static void tempMoveAlly(PlayingPiece ally, int allyX, int allyY, int enemyX, int enemyY) {
        PlayingPiece swap = table[allyX][allyY];
        table[allyX][allyY] = table[enemyX][enemyY];
        ally.move(enemyX, enemyY);
        table[enemyX][enemyY] = swap;
    }

    private static void moveAllyBack(PlayingPiece ally, int allyX, int allyY, int enemyX, int enemyY) {
        PlayingPiece swap = table[allyX][allyY];
        table[allyX][allyY] = table[enemyX][enemyY];
        ally.move(allyX, allyY);
        table[enemyX][enemyY] = swap;
    }

    public static boolean isChessWhites(int kingX, int kingY) {
        return allyOrEnemyCanReach(kingX, kingY, aliveBlacks);
    }

    public static boolean isChessBlacks(int kingX, int kingY) {

        return allyOrEnemyCanReach(kingX, kingY, aliveWhites);
    }

    public static PlayingPiece getKingWhite() {
        return kingWhite;
    }

    public static void setKingWhite(int x, int y, PlayingPiece kingWhite) {
        kingWhite.setX(x);
        kingWhite.setY(y);
        table[x][y] = kingWhite;
        Table.kingWhite = kingWhite;
    }

    public static PlayingPiece getKingBlack() {
        return kingBlack;
    }

    public static void setKingBlack(int x, int y, PlayingPiece kingBlack) {
        kingBlack.setX(x);
        kingBlack.setY(y);
        table[x][y] = kingBlack;
        Table.kingBlack = kingBlack;
    }

    public static PlayingPiece getObject(int x, int y) {
        return table[x][y];
    }

    public static PlayingPiece getEmptyObject() {
        return empty;
    }
}
