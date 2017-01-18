package com.nthportal.shell.async.compat;

import com.nthportal.shell.compat.Shell;

import java.util.List;
import java.util.function.Function;

/**
 * Something which creates {@link InputAction}s.
 */
@FunctionalInterface
public interface InputActionCreator {
    /**
     * Creates a new {@link InputAction} with the given action
     *
     * @param action the action for the InputAction to perform
     * @param <T> the return type of the action
     * @return an InputAction for the given action
     */
    <T> InputAction<T> inputAction(Function<Shell, T> action);

    /**
     * Creates an {@link InputAction} for executing a line.
     *
     * @param line the line to be executed
     * @return an input action for executing the given line
     * @see Shell#executeLine(String)
     */
    default InputAction<Void> execution(String line) {
        return inputAction(shell -> {
            shell.executeLine(line);
            return null;
        });
    }

    /**
     * Creates an {@link InputAction} for tab-completing a line.
     *
     * @param line the line to be tab-completed
     * @return an input action for tab-completing the given line
     * @see Shell#tabComplete(String)
     */
    default InputAction<List<String>> tabCompletion(String line) {
        return inputAction(shell -> shell.tabComplete(line));
    }
}
