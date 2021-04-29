import view.impl.LoginMenu;
import view.style.ConsoleColors;

public class ConsoleInternetShop {

    public static void start() {
        System.out.printf("%sGREETINGS%s%n", ConsoleColors.MAGENTA_BOLD.getColorCode(), ConsoleColors.RESET.getColorCode());

        new LoginMenu().show();

    }
}
