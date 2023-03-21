package domain.piece;

import static domain.piece.Side.BLACK;
import static domain.piece.Side.WHITE;

import java.util.List;
import view.PieceCategory;

public abstract class Piece {
    protected final Side side;

    protected Piece(Side side) {
        this.side = side;
    }

    public abstract boolean isMovable(Piece targetPiece, Position sourcePosition, Position targetPosition);

    public abstract List<Position> collectPath(Position sourcePosition, Position targetPosition);

    public abstract boolean isEmptyPiece();

    public abstract PieceCategory getCategory();

    public boolean isSameSideWith(Piece targetPiece) {
        return this.side == targetPiece.side;
    }

    public boolean isOpponentSideWith(Piece targetPiece) {
        if (this.side.equals(WHITE)) {
            return targetPiece.side.equals(Side.BLACK);
        }
        return targetPiece.side.equals(WHITE);
    }

    public boolean isOpponentOf(Side side) {
        if (this.side.equals(WHITE)) {
            return side.equals(BLACK);
        }
        if (this.side.equals(BLACK)) {
            return side.equals(WHITE);
        }
        throw new IllegalArgumentException("서버 내부 에러 - Neutral한 Piece는 상대편을 확인할 수 없습니다.");
    }
}
