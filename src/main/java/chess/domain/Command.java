package chess.domain;

import java.util.Arrays;

public enum Command {

    START("start"),
    END("end"),
    MOVE("move"),
    STATUS("status");

    private static final String INVALID_COMMAND_ERROR = "[ERROR] 잘못된 명령어 입력입니다.";

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public boolean isEnd() {
        return this == END;
    }

    public boolean isStart() {
        return this == START;
    }

    public boolean isMove() {
        return this == MOVE;
    }

    public boolean isStatus() {
        return this == STATUS;
    }

    public static Command find(String input) {
        return Arrays.stream(values())
                .filter(value -> value.command.equals(input))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_COMMAND_ERROR));
    }
}