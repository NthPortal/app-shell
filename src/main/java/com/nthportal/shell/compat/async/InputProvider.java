package com.nthportal.shell.compat.async;

import java.util.concurrent.CompletionStage;

/**
 * Something which provides input (asynchronously) for an {@link AsyncShell}.
 */
@FunctionalInterface
public interface InputProvider {
    /**
     * Returns a {@link CompletionStage} which will contain the next {@link InputAction action}
     * to be executed (by an {@link AsyncShell}).
     *
     * Successive invocations of this method MUST NOT return {@code CompletionStage}s which will
     * be completed with the same action; they MUST return {@code CompletionStage}s which will be
     * completed with successive requested actions.
     *
     * @return a CompletionStage which will contain the next action to be executed
     */
    CompletionStage<InputAction<?>> nextAction();
}
