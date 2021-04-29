package view.style;

public enum ConsoleColors {
    RESET("\033[0m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW_BOLD("\033[1;33m"),
    GREEN_BOLD_BRIGHT("\033[1;92m"),
    MAGENTA_BOLD("\033[1;35m");

    String colorCode;

    ConsoleColors(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }
}
