package chess.domain.state;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chess.domain.command.End;
import chess.domain.command.Move;
import chess.domain.command.Start;
import chess.domain.position.Position;

public class ReadyTest {

	@Test
	@DisplayName("Ready는 Move 커맨드를 받을 수 없다.")
	void ready_cannot_proceed_move() {
		GameState state = new Ready(new HashMap<>());
		assertThatThrownBy(() -> state.proceed(
			new Move(new Position(1, 1), new Position(2, 2)))
		).isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("Ready가 End 커맨드를 받으면 Finished 상태가 된다.")
	void ready_proceed_end() {
		GameState state = new Ready(new HashMap<>());
		assertThat(state.proceed(new End()))
			.isInstanceOf(Finished.class);
	}

	@Test
	@DisplayName("Ready가 start 커맨드를 받으면 Started 상태가 된다")
	void started() {
		GameState state = new Ready(new HashMap<>())
			.proceed(new Start());
		assertThat(state).isInstanceOf(Running.class);
	}
}
