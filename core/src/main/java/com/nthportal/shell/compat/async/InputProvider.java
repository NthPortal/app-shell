package com.nthportal.shell.compat.async;

import java.util.concurrent.CompletionStage;

@FunctionalInterface
public interface InputProvider {
    CompletionStage<InputAction<?>> nextAction();
}
