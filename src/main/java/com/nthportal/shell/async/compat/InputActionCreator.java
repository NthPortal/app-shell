package com.nthportal.shell.async.compat;

import com.nthportal.shell.compat.Shell;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface InputActionCreator {
    <T> InputAction<T> inputAction(Function<Shell, T> action);

    default InputAction<Void> execution(String line) {
        return inputAction(shell -> {
            shell.executeLine(line);
            return null;
        });
    }

    default InputAction<List<String>> tabCompletion(String line) {
        return inputAction(shell -> shell.tabComplete(line));
    }
}
