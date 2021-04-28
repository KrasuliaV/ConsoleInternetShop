package view;

import view.style.ConsoleColors;

public interface Menu {

    void exit();

    default void showItems(String[] items) {
        System.out.printf("%sChoose operation(enter operation's number)%s%n", ConsoleColors.YELLOW_BOLD.getColorCode(), ConsoleColors.RESET.getColorCode());
        for (String item : items) {
            System.out.printf("%s%-25s%s", ConsoleColors.GREEN_BOLD_BRIGHT.getColorCode(), item, ConsoleColors.RESET.getColorCode());
        }
        System.out.println("");
    }
}
