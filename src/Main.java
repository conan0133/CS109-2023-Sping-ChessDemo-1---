import controller.GameController;
import model.Chessboard;
import view.BeginFrame;
import view.ChessGameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BeginFrame mainFrame = new BeginFrame(600, 400,1100,810);
            ChessGameFrame frame =new ChessGameFrame(1100,810);
            GameController gameController = new GameController(mainFrame.getChessGameFrame().getChessboardComponent(), new Chessboard(),mainFrame.getChessGameFrame());
            mainFrame.setVisible(true);
        });
    }
}
