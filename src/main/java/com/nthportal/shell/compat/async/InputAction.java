package com.nthportal.shell.compat.async;

import com.nthportal.shell.compat.Shell;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * An action requested to be executed by a {@link Shell} by the
 * {@link InputProvider input source} of an {@link AsyncShell}.
 *
 * @param <T> the type of the result of the action
 */
public interface InputAction<T> {
    /**
     * Returns a {@link CompletionStage} which will contain the result of this action.
     *
     * @return a CompletionStage which will contain the result of this action
     */
    default CompletionStage<T> completionStage() {
        return FutureConverters.toJava(future());
    }

    /**
     * Returns a {@link Future} which will contain the result of this action.
     *
     * @return a Future which will contain the result of this action
     */
    Future<T> future();

    /**
     * Returns the result of an action performed with a {@link Shell}.
     *
     * @param shell the shell with which to perform the action
     * @return the result of the action
     */
    T action(Shell shell);

    /**
     * Creates an input action for tab-completing a line.
     *
     * @param line the line to be tab-completed
     * @return an input action for tab-completing the given line
     * @see Shell#tabComplete(String)
     */
    static InputAction<List<String>> tabCompletion(String line) {
        return JCompatInputAction.tabCompletion(line);
    }

    /**
     * Creates an input action for executing a line.
     *
     * @param line the line to be executed
     * @return an input action for executing the given line
     * @see Shell#executeLine(String)
     */
    static InputAction<Void> execution(String line) {
        return JCompatInputAction.execution(line);
    }
}
