package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ScoreTest {

    @DisplayName("Score 3과 Score 4를 더하면 Score 7을 반환한다.")
    @Test
    void shouldReturnScoreOf7WhenAdd4To3() {
        Score firstScore = new Score(3);
        Score secondScore = new Score(4);
        assertThat(Score.add(firstScore, secondScore)).isEqualTo(new Score(7));
    }
}
