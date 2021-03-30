package chess.domain.dto;

import chess.domain.ChessGame;
import chess.domain.board.Team;
import chess.view.OutputView;

import java.util.List;

public class GameInfoDto {
    private List<SquareDto> squares;
    private Team turn;
    private ScoresDto scores;

    public GameInfoDto(ChessGame chessGame) {
        this.squares = new SquaresDto(chessGame.board()).squares();
        this.turn = chessGame.turn();
        this.scores = new ScoresDto(chessGame.pointDto());
    }

    public List<SquareDto> squares() {
        return squares;
    }

    public Team turn() {
        return turn;
    }

    public List<ScoreDto> scores() {
        return scores.scores();
    }
}
