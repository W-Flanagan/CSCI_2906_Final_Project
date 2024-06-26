import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Chess {

    public static Piece currentPiece;
    public static String currentPlayer;
    public static Board cb;
    public boolean game;

    public Chess(GridPane chessBoard, String theme){
        cb = new Board(chessBoard, theme);
        currentPiece = null;
        currentPlayer = "white";
        this.game = true;
        addEventHandlers(cb.chessBoard);
    }

    private void addEventHandlers(GridPane chessBoard){
        chessBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EventTarget target = event.getTarget();

                
                if(target.toString().equals("Square")){
                    Square square = (Square) target;
                    if(square.occupied){
                        Piece newPiece = (Piece) square.getChildren().get(0);
                        
                        if(currentPiece == null){
                            currentPiece = newPiece;
                            currentPiece.getAllPossibleMoves();
                            if(!currentPiece.getColor().equals(currentPlayer)){
                                currentPiece = null;
                                return;
                            }
                            selectPiece(game);
                        }
                        
                        else{
                            if(currentPiece.color.equals(newPiece.color)){
                                deselectPiece(false);
                                currentPiece = newPiece;
                                currentPiece.getAllPossibleMoves();
                                selectPiece(game);
                            }
                            else{
                                capture(square);
                            }
                        }

                    }
                    
                    else{
                        dropPiece(square);
                    }
                }
                
                else{
                    Piece newPiece = (Piece) target;
                    Square square = (Square) newPiece.getParent();
                    
                    if(currentPiece == null){
                        currentPiece = newPiece;
                        if(!currentPiece.getColor().equals(currentPlayer)){
                            currentPiece = null;
                            return;
                        }
                        selectPiece(game);
                    }
                    
                    else{
                        if(currentPiece.color.equals(newPiece.color)){
                            deselectPiece(false);
                            currentPiece = newPiece;
                            selectPiece(game);
                        }
                        else{
                            capture(square);
                        }
                    }

                }
            }
        });
    }

    private void selectPiece(boolean game){
        if(!game){
            currentPiece = null;
            return;
        }

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.BLACK);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        currentPiece.setEffect(borderGlow);
        currentPiece.getAllPossibleMoves();
        currentPiece.showAllPossibleMoves(true);
    }

    private void deselectPiece(boolean changePlayer){
        currentPiece.setEffect(null);
        currentPiece.showAllPossibleMoves(false);
        currentPiece = null;
        if(changePlayer) currentPlayer = currentPlayer.equals("white") ? "black" : "white";
    }

    private void dropPiece(Square square){
        if(!currentPiece.possibleMoves.contains(square.name)) return;

        Square initialSquare = (Square) currentPiece.getParent();
        square.getChildren().add(currentPiece);
        square.occupied = true;
        initialSquare.getChildren().removeAll();
        initialSquare.occupied = false;
        currentPiece.posX = square.x;
        currentPiece.posY = square.y;
        deselectPiece(true);
    }

    private void capture(Square square){
        if(!currentPiece.possibleMoves.contains(square.name)) return;

        Piece capPiece = (Piece) square.getChildren().get(0);
        if(capPiece.type.equals("King")) this.game = false;
        


        Square initialSquare = (Square) currentPiece.getParent();
        square.getChildren().remove(0);
        square.getChildren().add(currentPiece);
        square.occupied = true;
        initialSquare.getChildren().removeAll();
        initialSquare.occupied = false;
        currentPiece.posX = square.x;
        currentPiece.posY = square.y;
        deselectPiece(true);
    }


}
