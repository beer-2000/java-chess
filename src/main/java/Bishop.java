public class Bishop extends Piece {
    protected Bishop(Side side) {
        super(side);
    }

    public static Bishop createOfWhite() {
        return new Bishop(Side.WHITE);
    }

    public static Bishop createOfBlack() {
        return new Bishop(Side.BLACK);
    }

    @Override
    public boolean isMovable(Piece targetPiece, Position sourcePosition, Position targetPosition) {
        Movement movement = sourcePosition.calculateMovement(targetPosition);
        if (this.isSameSideWith(targetPiece)) {
            return false;
        }
        return movement.isDiagonal();
    }
}