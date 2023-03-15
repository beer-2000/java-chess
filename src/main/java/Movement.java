import java.util.Objects;

public class Movement {
    private static final int NON_INCREMENT = 0;
    private final int fileIncrement;
    private final int rankIncrement;

    public Movement(int fileIncrement, int rankIncrement) {
        validate(fileIncrement, rankIncrement);
        this.fileIncrement = fileIncrement;
        this.rankIncrement = rankIncrement;
    }

    public boolean canMovedByPawn() {
        return this.rankIncrement == 1 && this.fileIncrement == 0;
    }

    private void validate(int fileIncrement, int rankIncrement) {
        if (fileIncrement == NON_INCREMENT && rankIncrement == NON_INCREMENT) {
            throw new IllegalArgumentException("잘못된 움직임입니다.");
        }
    }

    public boolean isUpward() {
        return this.rankIncrement > 0 && this.fileIncrement == NON_INCREMENT;
    }

    public boolean isDownward() {
        return this.rankIncrement < 0 && this.fileIncrement == NON_INCREMENT;
    }

    public boolean isOneStep() {
        return Math.abs(fileIncrement) <= 1 && Math.abs(rankIncrement) <= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return fileIncrement == movement.fileIncrement && rankIncrement == movement.rankIncrement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileIncrement, rankIncrement);
    }
}