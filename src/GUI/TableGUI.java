package GUI;

import Pieces.Play;
import Pieces.PlayingPiece;
import Pieces.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class TableGUI {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private PlayingPiece sourceTile;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");

    private static String pieceIconPath = "art/pieces/";

    public TableGUI() {
        this.gameFrame = new JFrame("Chess Game");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load Game");
        final JMenuItem exitMenuItem = new JMenuItem("Exit");

        //pgn is the game file , so you can open already existing game file
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up that pgn file!");
            }
        });

        //creates field exit in the menu
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(openPGN);
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            // тук трябва да има for до BoardUtils ,но при нас дъската не е чертана в arrayList
//            for (int i = 0; i <BoardUtils.NUM_TILES ; i++)
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    final TilePanel tilePanel = new TilePanel(this, row, col);
                    this.boardTiles.add(tilePanel);
                    add(tilePanel);
                }
            }

            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard() {
            removeAll();
            for (int i = 0; i < boardTiles.size(); i++) {
                boardTiles.get(i).drawTile();
                add(boardTiles.get(i));
            }
            validate();
            repaint();
        }

    }

    private class TilePanel extends JPanel {
        private final int tileX;
        private final int tileY;

        TilePanel(final BoardPanel boardPanel, final int tileX, final int tileY) {
            super(new GridBagLayout());
            this.tileX = tileX;
            this.tileY = tileY;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon();

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //десен бутон се използва за да деселектираш дадено поле, в случай че
                    // искаш да направиш друг ход, а не този, който си започнал
                    if (isRightMouseButton(e)) {
                        sourceTile = null;
                    } else if (isLeftMouseButton(e)) {
                        if (sourceTile == null) {
                            //ако е първото натискане на ляв бутон, значи избираш фигурата,
                            // която искаш да поместиш
                            sourceTile = Table.getObject(tileX, tileY);
                            if (sourceTile == null || sourceTile == Table.getEmptyObject()) {
                                sourceTile = null;

                            }
                        } else {
                            //ако е второто натискане, избираш мястото, на което да бъде
                            // поместена фигурата
                            Play.tryToMakeMove(sourceTile.getX(), sourceTile.getY(), tileX, tileY);
                            boardPanel.drawBoard();

                            if (Play.madeMove) {
                                //това са позициите на двата бутона в листа boardTiles
                                int tileIndex=sourceTile.getX() * 8 + sourceTile.getY();
                                int newTileIndex=tileX * 8 + tileY;
                                //в boardTiles слагаме бутона, съдържат фигурага за местене, на
                                // мястото на бутона, съдържащ мястото, на което да бъде преместена
                                boardPanel.boardTiles.add(newTileIndex
                                        , boardPanel.boardTiles.get(tileIndex));
                                //премахваме бутона от старото му място в boardTiles
                                boardPanel.boardTiles.remove(tileIndex);
                                boardPanel.drawBoard();
                            }
                            Play.madeMove = false;
                            sourceTile = null;

                        }
                        /*
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(board);
                            }
                        });
                        */
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            validate();
        }

        public void drawTile() {
            assignTileColor();
            assignTilePieceIcon();
            validate();
            repaint();
        }

        private void assignTileColor() {
            if (this.tileX % 2 == 0) {
                setBackground(this.tileY % 2 == 0 ? lightTileColor : darkTileColor);
            } else if (tileX % 2 != 0) {
                setBackground(this.tileY % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }

        private void assignTilePieceIcon() {
            this.removeAll();
            PlayingPiece tablePiece = Table.getObject(this.tileX, this.tileY);

            if (tablePiece != Table.getEmptyObject()) {
                try {
                    /*
                    задава директорията на иконата на фигурата, като към директорията на папката,
                     в която се намират иконите(съхранена в променливата pieceIconPath),добавя
                     първата буква на цвета, първите 2 букви на името на класа и накрая .gif
                    */
                    BufferedImage image = ImageIO.read(new File(pieceIconPath
                            + tablePiece.getColor().substring(0, 1).toUpperCase()
                            + tablePiece.getClass().getSimpleName().substring(0, 2).toUpperCase()
                            + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

