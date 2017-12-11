package io.retrofrog.frogbot.integrations.irc.utils;

public enum IrcStyle {
    ResetColor ("\u0003"),

    // Colors
    White ("\u000300"),
    Black ("\u000301"),
    Blue ("\u000302"),
    Red ("\u000304"),
    Brown ("\u000305"),
    Purple ("\u000306"),
    Orange ("\u000307"),
    Yellow ("\u000308"),
    Green ("\u000309"),
    Teal ("\u000310"),
    Cyan ("\u000311"),
    LightBlue ("\u000312"),
    Pink ("\u000313"),
    Grey ("\u000314"),
    Silver ("\u000315"),

    // background
    BackgroundWhite ("\u0003,00"),
    BackgroundBlack ("\u0003,01"),
    BackgroundBlue ("\u0003,02"),
    BackgroundRed ("\u0003,04"),
    BackgroundBrown ("\u0003,05"),
    BackgroundPurple ("\u0003,06"),
    BackgroundOrange ("\u0003,07"),
    BackgroundYellow ("\u0003,08"),
    BackgroundGreen ("\u0003,09"),
    BackgroundTeal ("\u0003,10"),
    BackgroundCyan ("\u0003,11"),
    BackgroundLightBlue ("\u0003,12"),
    BackgroundPink ("\u0003,13"),
    BackgroundGrey ("\u0003,14"),
    BackgroundSilver ("\u0003,15"),

    // Styles
    Bold ("\u0002"),
    Italic ("\u001d"),
    Underline ("\u001f"),
    Inverse ("\u0016"),
    ResetAll ("\u000f");

    private final String name;

    private IrcStyle(String s) {
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
