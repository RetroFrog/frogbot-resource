package io.retrofrog.frogbot.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ColorString {

    // For row col
    // \u001b[Y;Xf
    // \u2581 - 88 for spark bars

    private Object value;
    private Set<Colors> style;
    private Short row;
    private Short col;

    public ColorString(Object value, Short row, Short col, Colors ...style) {
        this.style = new HashSet<>();
        this.value = value;
        Collections.addAll(this.style, style);
        this.row = row;
        this.col = col;
    }

    public ColorString(Object value, Colors ...style) {
        this(value, null, null, style);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        if (row != null && col != null) {
            s.append("\u001b[").append(row).append(";").append(col).append("f");
        }

        for (Colors c: style) {
            s.append("\u001b[").append(c).append("m");
        }

        s.append(value).append("\u001b[").append(Colors.Reset).append("m");
        return s.toString();
    }
}
