package chess.domain.state;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chess.domain.command.Move;
import chess.domain.command.Status;
import chess.domain.piece.King;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

public class RunningBlackTurnTest {

	private final Map<Position, Piece> board = Map.of(
		new Position(7, 2), King.createBlack(),
		new Position(6, 2), King.createWhite());

	@Test
	@DisplayName("RunningBlackTurn 상태에서 Move 커맨드를 받으면 RunningWhiteTurn 상태가 된다.")
	void blackTurn_to_whiteTurn() {
		GameState state = new RunningBlackTurn(board);

		state = state.proceed(
			new Move(new Position(7, 2), new Position(6, 3)));

		assertThat(state).isInstanceOf(RunningWhiteTurn.class);
	}

	@Test
	@DisplayName("RunningBlackTurn일 때 Move 커맨드로 움직인다.")
	void movePiece() {
		GameState state = new RunningBlackTurn(board)
			.proceed(new Move(new Position(7, 2), new Position(6, 3)));

		assertThat(state.getBoard().get(new Position(6, 3)))
			.isInstanceOf(King.class);
	}

	@Test
	@DisplayName("RunningBlackTurn일 때 White을 움직일 수 없다.")
	void movePieceException() {
		GameState state = new RunningBlackTurn(board);

		assertThatThrownBy(() -> state.proceed(
			new Move(new Position(6, 2), new Position(5, 2))))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("RunningBlackTurn일 때 상대방의 King을 잡으면 Finished로 전환")
	void killOpponentKing() {
		GameState state = new RunningBlackTurn(board);
		state = state.proceed(
			new Move(new Position(7, 2), new Position(6, 2)));

		assertThat(state).isInstanceOf(Finished.class);
	}

	@Test
	@DisplayName("RunningBlackTurn일 때 Staus 커맨드이면 자기 자신을 유지해야 한다")
	void statusCommand() {
		GameState state = new RunningBlackTurn(board);
		state = state.proceed(new Status());

		assertThat(state).isInstanceOf(RunningBlackTurn.class);
	}
}
