package com.nthportal.shell.compat;

import com.nthportal.shell.OutputSink;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface SubCommand {
    default Optional<String> description() {
        return Optional.empty();
    }

    default Optional<String> help(List<String> args) {
        return Optional.empty();
    }

    default List<String> tabComplete(List<String> args) {
        return Collections.emptyList();
    }

    void execute(List<String> args, OutputSink sink);
}
