package com.nthportal.shell.compat;

import com.nthportal.shell.OutputSink;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A sub-command to be executed by a {@link Shell}. It may be a top-level
 * {@link Command command}, a sub-command of a top-level command, or a
 * sub-command of another sub-command.
 */
public interface SubCommand {
    /**
     * Returns an {@link Optional} containing a brief description of this sub-command,
     * or an empty Optional if no description is provided.
     *
     * @return a brief description of this sub-command
     */
    default Optional<String> description() {
        return Optional.empty();
    }

    /**
     * Returns an {@link Optional} containing a help message for this sub-command,
     * or an empty Optional if no help message is provided.
     *
     * The message may be based on the arguments provided, or it may be a
     * static message regardless of the arguments provided. For example, it may
     * return a different message for different sub-command of its own.
     *
     * @param args the arguments of this command for which to get a help message
     * @return a help message for this sub-command with the given arguments
     */
    default Optional<String> help(List<String> args) {
        return Optional.empty();
    }

    /**
     * Returns a list of suggested completions for the final argument
     * of a list of arguments. Returns an empty list if no suggestions
     * are available for a given argument list.
     *
     * @param args the arguments for which to provide suggested completions
     * @return a list of suggested completions for the final argument given
     */
    default List<String> tabComplete(List<String> args) {
        return Collections.emptyList();
    }

    /**
     * Executes this sub-command with the given arguments.
     *
     * @param args the arguments with which this should be executed
     * @param sink an {@link OutputSink} to which output may be written during execution
     */
    void execute(List<String> args, OutputSink sink);
}
