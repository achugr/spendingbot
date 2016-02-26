package ru.achugr.spendingbot.exception;

/**
 * author: achugr
 * date: 14.02.16
 */
public class WrongCommandException extends IllegalArgumentException {
    public WrongCommandException(String msg) {
        super(msg);
    }
}
