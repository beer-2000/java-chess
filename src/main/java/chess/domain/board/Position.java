package chess.domain.board;

import chess.domain.Team;

import java.util.*;

public class Position {

    private static final String OVER_RANGE_ERROR = "체스판 범위를 벗어나는 입력입니다.";
    private static final int MIN_ROW = 1;
    private static final int MAX_ROW = 8;
    private static final int MIN_COLUMN = 1;
    private static final int MAX_COLUMN = 8;
    private static final int BLACK_PAWN_DEFAULT_ROW = 7;
    private static final int WHITE_PAWN_DEFAULT_ROW = 2;

    private final int row;
    private final int column;

    private static final Map<Integer, Map<Integer, Position>> cachedPositions = new HashMap<>();
    private static final List<Position> positions = getAllPositions();

    private static List<Position> getAllPositions() {
        List<Position> positions = new ArrayList<>();
        for (int i = MIN_ROW; i <= MAX_ROW; i++) {
            positions.addAll(createRowPositions(i));
        }
        return positions;
    }

    private Position(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(final int row, final int column) {
        validateRange(row, column);
        cachedPositions.putIfAbsent(row, new HashMap<>());
        cachedPositions.get(row).putIfAbsent(column, new Position(row, column));
        return cachedPositions.get(row).get(column);
    }

    private static void validateRange(final int row, final int column) {
        if (isOverRange(row, column)) {
            throw new IllegalArgumentException(OVER_RANGE_ERROR);
        }
    }

    private static boolean isOverRange(final int row, final int column) {
        return row < MIN_ROW || row > MAX_ROW || column < MIN_COLUMN || column > MAX_COLUMN;
    }

    public static List<Position> getPositions() {
        return positions;
    }

    public Optional<Position> addDirection(Direction direction) {
        int row = direction.addRow(this.row);
        int column = direction.addColumn(this.column);
        if (isOverRange(row, column)) {
            return Optional.empty();
        }
        return Optional.of(Position.of(row, column));
    }

    public boolean isDifferentRow(Position position) {
        return this.row != position.row;
    }

    public boolean isDifferentColumn(Position position) {
        return this.column != position.column;
    }

    public boolean isDifferentDiagonal(Position position) {
        return Math.abs(this.row - position.row) != Math.abs(this.column - position.column);
    }

    public int calculateRowDirection(Position position) {
        return Integer.compare(this.row, position.row);
    }

    public int calculateColumnDirection(Position position) {
        return Integer.compare(this.column, position.column);
    }

    private static List<Position> createRowPositions(final int row) {
        List<Position> rowPositions = new ArrayList<>();
        for (int j = MIN_COLUMN; j <= MAX_COLUMN; j++) {
            rowPositions.add(Position.of(row, j));
        }
        return rowPositions;
    }

    public static List<Position> getReversePositions() {
        List<Position> positions = new ArrayList<>();
        for (int i = MAX_ROW; i >= MIN_ROW; i--) {
            positions.addAll(createRowPositions(i));
        }
        return positions;
    }

    public boolean isEndColumn() {
        return column == MAX_COLUMN;
    }

    public boolean isDefaultRow(final Team team) {
        if (team.isBlack()) {
            return this.row == BLACK_PAWN_DEFAULT_ROW;
        }
        return this.row == WHITE_PAWN_DEFAULT_ROW;
    }

    public int subtractRow(Position target) {
        return this.row - target.row;
    }

    public int subtractColumn(Position target) {
        return this.column - target.column;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}