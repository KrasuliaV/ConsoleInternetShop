package view;

import view.style.ConsoleColors;

public interface Menu {

    String ENTER_RIGHT_OPERATION = "Enter right operation";

    void exit();

    default void showItems(String[] items) {
        System.out.printf("%sChoose operation%s%n", ConsoleColors.YELLOW_BOLD.getColorCode(), ConsoleColors.RESET.getColorCode());
        for (String item : items) {
            System.out.printf("%s%-25s%s%n", ConsoleColors.GREEN_BOLD_BRIGHT.getColorCode(), item, ConsoleColors.RESET.getColorCode());
        }
    }
}
