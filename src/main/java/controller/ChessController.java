package controller;

import controller.command.Command;
import controller.command.End;
import controller.command.Move;
import dao.GameDto;
import domain.game.Game;
import domain.game.Position;
import domain.game.Score;
import domain.piece.Piece;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import service.ChessService;
import util.GameStatusMapper;
import util.PieceMapper;
import view.InputView;
import view.OutputView;

public class ChessController {
    private final InputView inputView;
    private final OutputView outputView;
    private final ChessService chessService;
    private final String userName;

    public ChessController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.chessService = new ChessService();
        this.userName = repeat(this.inputView::requestUserName);
    }

    public void run() {
        Command command;
        do {
            play();
            command = repeat(this.inputView::requestStartCommand);
        } while (command.isContinue());
    }

    public void play() {
        this.outputView.printGameGuideMessage();
        Game game = repeat(this::getGame);
        proceed(game);
        this.chessService.saveChessBoard(game);
    }

    private Game getGame() {
        Command command = this.inputView.requestUserCommand();
        if (command.isStart()) {
            return startGame();
        }
        if (command.isSearch()) {
            return searchGame();
        }
        throw new IllegalArgumentException("게임 시작하려면 먼저 start를 입력하세요.");
    }

    private Game startGame() {
        String title = this.inputView.requestGameTitle();
        Game game = Game.create(this.userName, title);
        printChessBoardOf(game);
        this.chessService.create(game);
        return game;
    }

    private Game searchGame() {
        List<GameDto> gameDtos = this.chessService.findGamesByUserName(this.userName);
        this.outputView.printGamesOfUser(gameDtos);
        Game game = this.chessService.findGameById(this.inputView.requestGameId());
        printChessBoardOf(game);
        return game;
    }

    private void proceed(Game game) {
        Command command;
        if (isEnd(game)) {
            printGameResultOf(game);
            return;
        }
        do {
            this.outputView.printSideOfTurn(game.getSideOfTurn());
            command = repeat(() -> moveByUserCommand(game));
        } while (!command.isEnd());
        printGameResultOf(game);
    }

    private Command moveByUserCommand(Game game) {
        Command command = this.inputView.requestUserCommand();
        if (command.isEnd()) {
            return command;
        }
        if (command.isStatus()) {
            printGameResultOf(game);
            return command;
        }
        moveByPositionsOfMoveCommand(game, command);
        if (isEnd(game)) {
            return new End();
        }
        printChessBoardOf(game);
        return command;
    }

    private boolean isEnd(Game game) {
        return this.chessService.isEnd(game);
    }

    private void printChessBoardOf(Game game) {
        Map<Position, Piece> chessBoard = game.getChessBoard();
        Map<Position, String> chessBoardOfForPrint = new LinkedHashMap<>();
        for (Map.Entry<Position, Piece> positionPieceEntry : chessBoard.entrySet()) {
            chessBoardOfForPrint.put(
                    positionPieceEntry.getKey(),
                    PieceMapper.convertPieceCategoryToTextForView(positionPieceEntry.getValue().getCategory()));
        }
        this.outputView.printChessBoard(chessBoardOfForPrint);
    }

    private void moveByPositionsOfMoveCommand(Game game, Command command) {
        Move moveCommand = (Move) command;
        this.chessService.moveByCommand(game, moveCommand);
    }

    private void printGameResultOf(Game game) {
        String gameStatusText = GameStatusMapper.convertGameStatusToText(game.checkStatus());
        Score whiteScore = game.calculateWhiteScore();
        Score blackScore = game.calculateBlackScore();
        this.outputView.printGameResult(gameStatusText, whiteScore, blackScore);
    }

    private <T> T repeat(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalStateException e) {
            System.out.println("Log: " + e.getMessage()); // log 기록
            this.outputView.printServerErrorMessage();
            return repeat(supplier);
        } catch (RuntimeException e) {
            this.outputView.printErrorMessage(e.getMessage());
            return repeat(supplier);
        }
    }
}
