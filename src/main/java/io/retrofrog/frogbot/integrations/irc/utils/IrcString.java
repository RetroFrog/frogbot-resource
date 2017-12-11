package io.retrofrog.frogbot.integrations.irc.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class IrcString {

    private Object value;
    private Set<IrcStyle> style;

    public IrcString(Object value, IrcStyle...style) {
        this.style = new HashSet<>();
        this.value = value;
        Collections.addAll(this.style, style);
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (IrcStyle c: style) {
            s.append(c);
        }

        s.append(value).append(IrcStyle.ResetAll);
        return s.toString();
    }
}
