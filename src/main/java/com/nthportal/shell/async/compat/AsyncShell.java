package com.nthportal.shell.async.compat;

import com.nthportal.shell.compat.Shell;
import scala.concurrent.Future;

import java.util.concurrent.CompletionStage;

import static scala.compat.java8.FutureConverters.*;


/**
 * An asynchronous shell. It processes commands and tab-completions asynchronously.
 */
public interface AsyncShell extends InputActionCreator {
    /**
     * Returns a {@link CompletionStage} which represents the status of this asynchronous shell.
     *
     * The CompletionStage will succeed (with {@code null}) when this shell is terminated normally.
     * If this shell throws an exception, the Future will fail with that exception.
     *
     * @return a CompletionStage representing the status of this asynchronous shell
     */
    default CompletionStage<Void> status() {
        return toJava(status0());
    }

    /**
     * Returns a {@link Future} which represents the status of this asynchronous shell.
     *
     * The Future will succeed (with {@code null}) when this shell is terminated normally.
     * If this shell throws an exception, the Future will fail with that exception.
     *
     * @return a Future representing the status of this asynchronous shell
     */
    Future<Void> status0();

    /**
     * Terminates this asynchronous shell so that it will no longer process inputs.
     * It cannot be terminated more than once.
     *
     * Note: Invoking this method does NOT interrupt an action which is already
     * being processed.
     *
     * @return {@link #status()}, which will be completed once the shell is fully terminated
     */
    default CompletionStage<Void> terminate() {
        return toJava(terminate0());
    }

    /**
     * Terminates this asynchronous shell so that it will no longer process inputs.
     * It cannot be terminated more than once.
     *
     * Note: Invoking this method does NOT interrupt an action which is already
     * being processed.
     *
     * @return {@link #status0()}, which will be completed once the shell is fully terminated
     */
    Future<Void> terminate0();

    /**
     * Creates an asynchronous shell.
     *
     * @param inputProvider the {@link InputProvider} for the asynchronous shell
     * @param shell         the (synchronous) shell to be managed asynchronously by the AsyncShell
     * @return an asynchronous shell created with the given parameters
     */
    static AsyncShell create(InputProvider inputProvider, Shell shell) {
        return new AsyncShellImpl(inputProvider, shell);
    }
}
