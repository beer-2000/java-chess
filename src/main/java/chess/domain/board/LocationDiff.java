package chess.domain.board;

public class LocationDiff {

    private final int diffFile;
    private final int diffRank;

    public LocationDiff(int diffFile, int diffRank) {
        checkSameLocation(diffFile, diffRank);
        this.diffFile = diffFile;
        this.diffRank = diffRank;
    }

    private void checkSameLocation(int diffFile, int diffRank) {
        if (diffFile == 0 && diffRank == 0) {
            throw new IllegalArgumentException("[ERROR] 제자리로 움직일 수 없습니다.");
        }
    }

    public Direction computeDirection() {
        int distance = computeDistance();
        return Direction.of(diffFile / distance, diffRank / distance);
    }

    public int computeDistance() {
        return Math.abs(gcd(diffFile, diffRank));
    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
