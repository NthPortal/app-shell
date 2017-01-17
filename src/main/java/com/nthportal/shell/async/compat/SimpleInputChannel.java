package com.nthportal.shell.async.compat;

import com.nthportal.collection.concurrent.FutureQueue;

import java.util.concurrent.CompletionStage;

import static scala.compat.java8.FutureConverters.toJava;

/**
 * A simple {@link InputChannel} implementation.
 */
public final class SimpleInputChannel implements InputChannel {
    private final FutureQueue<InputAction<?>> queue = FutureQueue.empty();

    @Override
    public <T> CompletionStage<T> sendAction(InputAction<T> action) {
        queue.enqueue(action);
        return toJava(action.future());
    }

    @Override
    public CompletionStage<InputAction<?>> nextAction() {
        return toJava(queue.dequeue());
    }
}
