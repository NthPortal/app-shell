package com.nthportal.shell.compat;

/**
 * A command to be executed by a {@link Shell}.
 */
public interface Command extends SubCommand {
    /**
     * Returns the name of the command.
     *
     * @return the name of the command
     */
    String name();
}
