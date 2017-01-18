package com.nthportal.shell.async.compat;

import java.util.concurrent.CompletionStage;

/**
 * Something which transmits input between a source and an {@link AsyncShell}.
 */
public interface InputChannel extends InputProvider {
    /**
     * Sends an action to be executed asynchronously by an {@link AsyncShell}.
     *
     * Returns a {@link CompletionStage} which will contain the result of
     * the action. The {@code CompletionStage} returned SHALL be equivalent
     * to the one returned by invoking the
     * {@link InputAction#completionStage() completionStage} method of the
     * given action.
     *
     * @param action the action to be executed
     * @param <T> the type of the result of the action to be executed
     * @return a CompletionStage which will contain the result of the action
     */
    <T> CompletionStage<T> sendAction(InputAction<T> action);
}
