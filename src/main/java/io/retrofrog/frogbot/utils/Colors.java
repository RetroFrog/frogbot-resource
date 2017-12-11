package io.retrofrog.frogbot.utils;

public enum Colors {
    // \u001B[ + Colors
    Reset ("0"),

    // Colors
    Black ("30"),
    Red ("31"),
    Green ("32"),
    Yellow ("33"),
    Blue ("34"),
    Magenta ("35"),
    Cyan ("36"),
    White ("37"),

    BrightBlack ("1;30"),
    BrightRed ("1;31"),
    BrightGreen ("1;32"),
    BrightYellow ("1;33"),
    BrightBlue ("1;34"),
    BrightMagenta ("1;35"),
    BrightCyan ("1;36"),
    BrightWhite ("1;37"),


    // Backgrounds
    BackgroundBlack ("40"),
    BackgroundRed ("41"),
    BackgroundGreen ("42"),
    BackgroundYellow ("43"),
    BackgroundBlue ("44"),
    BackgroundMagenta ("45"),
    BackgroundCyan ("46"),
    BackgroundWhite ("47"),

    BackgroundBrightBlack ("100"),
    BackgroundBrightRed ("101"),
    BackgroundBrightGreen ("102"),
    BackgroundBrightYellow ("103"),
    BackgroundBrightBlue ("104"),
    BackgroundBrightMagenta ("105"),
    BackgroundBrightCyan ("106"),
    BackgroundBrightWhite ("107"),

    // Styles
    Bold ("1"),
    Dim ("2"),
    Italic ("3"),
    Underline ("4"),
    SlowBlink ("5"),
    FastBlink ("6"),
    Inverse ("7"),
    Hidden ("8"),
    Strikethrough ("9"),

    Framed ("51"),
    Encircled ("52"),
    Overlined ("53");

    private final String name;

    private Colors(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
    
}
