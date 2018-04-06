

import GUI.TableGUI;
import Pieces.*;

public class Play {
    static int turn = 1;

    public static void main(String[] args) {
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
        King kingWhite = new King("white");
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
        King kingBlack = new King("black");
        Queen queenBlack = new Queen("black");


        Table table=new Table();
        TableGUI tableGUI= new TableGUI();


        /*TableGUI.createTable();
        TableGUI.printTable();

        Scanner input = new Scanner(System.in);
        int x;
        int y;
        int newX;
        int newY;
        String currentColor;
        PlayingPiece currentKing;
        int chessResult;

        while (true) {
            if (turn % 2 == 1) {
                currentColor = "white";
                currentKing = kingWhite;
                chessResult = isCheckMateOrChess(kingWhite);

                if (chessResult == 1) {
                    System.out.println("Game over! Black wins.");
                    break;
                } else if (chessResult == 2) {
                    System.out.println("Chess! Move or you'll lose.");
                }

                System.out.println("White's turn");
            } else {
                currentKing = kingBlack;
                chessResult = isCheckMateOrChess(kingBlack);

                if (chessResult == 1) {
                    System.out.println("Game over! White wins.");
                    break;
                } else if (chessResult == 2) {
                    System.out.println("Chess! Move or you'll lose.");
                }

                System.out.println("Black's turn");
                currentColor = "black";
            }

            System.out.print("Insert figure's X:");
            x = input.nextInt();
            System.out.print("Insert figure's Y:");
            y = input.nextInt();
            System.out.print("Insert new position X:");
            newX = input.nextInt();
            System.out.print("Insert new position Y:");
            newY = input.nextInt();
            TableGUI.play(TableGUI.getObject(x, y).getColor(), currentColor, x, y, newX, newY);
            TableGUI.printTable();

            if (isCheckMateOrChess(currentKing) == 1 || isCheckMateOrChess(currentKing) == 2) {
                if (currentColor.equals("white")) {
                    System.out.println("Game over! Black wins.");
                } else {
                    System.out.println("Game over! White wins.");
                }
                break;
            }
            turn++;
        }
    }

    private static int isCheckMateOrChess(PlayingPiece king) {
        if (king.getColor().equals("white")) {
            if (TableGUI.isChessMateWhites(king.getX(), king.getY())) {
                return 1;
            } else if (TableGUI.isChessWhites(king.getX(), king.getY())) {
                return 2;
            }
        } else if(king.getColor().equals("black")){
            if (TableGUI.isChessMateBlacks(king.getX(), king.getY())) {
                return 1;
            } else if (TableGUI.isChessBlacks(king.getX(), king.getY())) {
                return 2;
            }
        }
        return 0;
        */
    }
}


