package Pieces;

import GUI.TableGUI;

public class Play {
    public static int turn = 1;
    public static boolean madeMove;
    private static King kingWhite = new King("white");
    private static King kingBlack = new King("black");

    public static void main(String[] args) {
        setPieces();
        Table table = new Table();
        TableGUI tableGUI = new TableGUI();
    }

    public static void tryToMakeMove(int x, int y, int newX, int newY) {
        String currentColor;
        PlayingPiece currentKing;
        int chessResult;

        Table.printTable();
        if (turn % 2 == 1) {
            currentColor = "white";
            currentKing = kingWhite;
            chessResult = isCheckMateOrChess(kingWhite);

            if (chessResult == 1) {
                System.out.println("Game over! Black wins.");
            } else if (chessResult == 2) {
                System.out.println("Chess! Move or you'll lose.");
            }

            System.out.println("White's turn");
        } else {
            currentKing = kingBlack;
            chessResult = isCheckMateOrChess(kingBlack);

            if (chessResult == 1) {
                System.out.println("Game over! White wins.");
            } else if (chessResult == 2) {
                System.out.println("Chess! Move or you'll lose.");
            }

            System.out.println("Black's turn");
            currentColor = "black";
        }
        System.out.println(currentColor);
        Table.play(Table.getObject(x, y).getColor(), currentColor, x, y, newX, newY);
        Table.printTable();


        if (isCheckMateOrChess(currentKing) == 1 || isCheckMateOrChess(currentKing) == 2) {
            if (currentColor.equals("white")) {
                System.out.println("Game over! Black wins.");
            } else {
                System.out.println("Game over! White wins.");
            }
        }
        turn++;
    }

    private static int isCheckMateOrChess(PlayingPiece king) {
        if (king.getColor().equals("white")) {
            if (Table.isChessMateWhites(king.getX(), king.getY())) {
                return 1;
            } else if (Table.isChessWhites(king.getX(), king.getY())) {
                return 2;
            }
        } else if (king.getColor().equals("black")) {
            if (Table.isChessMateBlacks(king.getX(), king.getY())) {
                return 1;
            } else if (Table.isChessBlacks(king.getX(), king.getY())) {
                return 2;
            }
        }
        return 0;
    }

    private static void setPieces(){
        //TODO try to do this in some other way
        Pawn pawn1White = new Pawn("white");
        Pawn pawn2White = new Pawn("white");
        Pawn pawn3White = new Pawn("white");
        Pawn pawn4White = new Pawn("white");
        Pawn pawn5White = new Pawn("white");
        Pawn pawn6White = new Pawn("white");
        Pawn pawn7White = new Pawn("white");
        Pawn pawn8White = new Pawn("white");

        Rook rook1White = new Rook("white");
        Rook rook2White = new Rook("white");
        Knight knight1White = new Knight("white");
        Knight knight2White = new Knight("white");
        Bishop bishop1White = new Bishop("white");
        Bishop bishop2White = new Bishop("white");
        Queen queenWhite = new Queen("white");

        Pawn pawn1Black = new Pawn("black");
        Pawn pawn2Black = new Pawn("black");
        Pawn pawn3Black = new Pawn("black");
        Pawn pawn4Black = new Pawn("black");
        Pawn pawn5Black = new Pawn("black");
        Pawn pawn6Black = new Pawn("black");
        Pawn pawn7Black = new Pawn("black");
        Pawn pawn8Black = new Pawn("black");

        Rook rook1Black = new Rook("black");
        Rook rook2Black = new Rook("black");
        Knight knight1Black = new Knight("black");
        Knight knight2Black = new Knight("black");
        Bishop bishop1Black = new Bishop("black");
        Bishop bishop2Black = new Bishop("black");
        Queen queenBlack = new Queen("black");
    }
}





