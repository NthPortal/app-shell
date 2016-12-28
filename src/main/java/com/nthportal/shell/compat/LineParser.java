package com.nthportal.shell.compat;

import java.util.List;

@FunctionalInterface
public interface LineParser {
    List<String> parseLine(String line);
}
