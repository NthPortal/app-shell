package com.nthportal.shell.compat.async;

import com.nthportal.shell.compat.Shell;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface InputAction<T> {
    default CompletionStage<T> completionStage() {
        return FutureConverters.toJava(future());
    }

    Future<T> future();

    T action(Shell shell);

    static InputAction<List<String>> tabCompletion(String line) {
        return JCompatInputAction.tabCompletion(line);
    }

    static InputAction<Void> execution(String line) {
        return JCompatInputAction.execution(line);
    }
}
