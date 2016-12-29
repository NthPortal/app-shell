package com.nthportal.shell.compat;

import com.nthportal.shell.OutputProvider;

import java.util.List;

public interface Shell {
    List<Command> commands();

    LineParser lineParser();

    List<String> tabComplete(String line);

    void executeLine(String line);

    static Shell create(LineParser lineParser, List<Command> commands, OutputProvider outputProvider) {
        return JCompatShell.create(lineParser, commands, outputProvider);
    }

    static Shell create(com.nthportal.shell.LineParser lineParser,
                        List<Command> commands,
                        OutputProvider outputProvider) {
        return JCompatShell.create0(lineParser, commands, outputProvider);
    }
}
