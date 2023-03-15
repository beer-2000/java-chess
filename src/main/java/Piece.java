public abstract class Piece {
    protected final Side side;

    protected Piece(Side side) {
        this.side = side;
    }

    public abstract boolean isMovable(Piece targetPiece, Position sourcePosition, Position targetPosition);

    public boolean isSameSideWith(Piece targetPiece) {
        return this.side == targetPiece.side;
    }

    public boolean isOpponentSideWith(Piece targetPiece) {
        if (this.side.equals(Side.WHITE)) {
            return targetPiece.side.equals(Side.BLACK);
        }
        return targetPiece.side.equals(Side.WHITE);
    }
}
