import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MovementTest {

    @DisplayName("file, rank움직임이 0,0이 되면 예외가 발생한다.")
    @Test
    void shouldThrowExceptionWhenMovingIsAllZero() {
        assertThatThrownBy(() -> new Movement(0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 움직임입니다.");
    }

    @DisplayName("위 방향의 움직임이면 true, 아니면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"0,1,true", "0,-1,false"})
    void shouldReturnTrueWhenMovementDirectionIsUpwardOrFalse(int fileIncrement, int rankIncrement, boolean result) {
        Movement movement = new Movement(fileIncrement, rankIncrement);
        assertThat(movement.isUpward()).isEqualTo(result);
    }

    @DisplayName("아래 방향의 움직임이면 true, 아니면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"0,-1,true", "0,1,false"})
    void shouldReturnTrueWhenMovementDirectionIsDownwardOrFalse(int fileIncrement, int rankIncrement, boolean result) {
        Movement movement = new Movement(fileIncrement, rankIncrement);
        assertThat(movement.isDownward()).isEqualTo(result);
    }

    @DisplayName("2칸 이내의 움직임이면 true, 아니면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"2,0,true", "0,1,true", "2,2,true", "-2,2,true", "-3,1,false"})
    void shouldReturnTrueWhenMovementDirectionIsUnderTwoStepsOrFalse(int fileIncrement, int rankIncrement, boolean result) {
        Movement movement = new Movement(fileIncrement, rankIncrement);
        assertThat(movement.isUnderTwoSteps()).isEqualTo(result);
    }

    @DisplayName("수직으로 상, 하, 좌, 우 방향의 움직임이면 true, 아니면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"2,0,true", "0,1,true", "-2,0,true", "0,-3,true", "1,1,false", "-1,3,false"})
    void shouldReturnTrueWhenMovementDirectionIsPerpendicularOrFalse(int fileIncrement, int rankIncrement, boolean result) {
        Movement movement = new Movement(fileIncrement, rankIncrement);
        assertThat(movement.isPerpendicular()).isEqualTo(result);
    }

}