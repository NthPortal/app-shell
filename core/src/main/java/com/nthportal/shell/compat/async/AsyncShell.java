package com.nthportal.shell.compat.async;

import com.nthportal.shell.compat.Shell;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.concurrent.CompletionStage;

public interface AsyncShell {
    default CompletionStage<Void> terminate() {
        return FutureConverters.toJava(terminate0());
    }

    Future<Void> terminate0();

    static AsyncShell create(InputProvider inputProvider, Shell shell) {
        return AsyncShellImpl.apply(inputProvider, shell);
    }
}
