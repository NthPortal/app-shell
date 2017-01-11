package com.nthportal.shell.compat.async;

import com.nthportal.shell.compat.Shell;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.concurrent.CompletionStage;

/**
 * An asynchronous shell. It processes commands and tab-completions asynchronously.
 */
public interface AsyncShell {
    /**
     * Terminates this asynchronous shell so that it will no longer process inputs.
     * It cannot be terminated more than once.
     *
     * Note: Invoking this method does NOT interrupt an action which is already
     * being processed.
     *
     * @return a {@link CompletionStage} which will be completed once the shell is fully terminated
     */
    default CompletionStage<Void> terminate() {
        return FutureConverters.toJava(terminate0());
    }

    /**
     * Terminates this asynchronous shell so that it will no longer process inputs.
     * It cannot be terminated more than once.
     *
     * Note: Invoking this method does NOT interrupt an action which is already
     * being processed.
     *
     * @return a Future which will be completed once the shell is fully terminated
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
        return AsyncShellImpl.apply(inputProvider, shell);
    }
}
