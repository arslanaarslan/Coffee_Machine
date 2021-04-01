package machine;

import java.util.*;

public class CoffeeMachine {
    private int amountOfWaterInMachine;
    private int amountOfMilkInMachine;
    private int amountOfCoffeeBeansInMachine;
    private int amountOfDisposableCupsInMachine;
    private int amountOfMoney;

    private final Coffee espresso = new Coffee(250, 0, 16, 4);
    private final Coffee latte = new Coffee(350, 75, 20, 7);
    private final Coffee cappuccino = new Coffee(200, 100, 12, 6);

    public enum Selection {
        BUY, FILL, TAKE, REMAINING, EXIT
    }

    private String command;
    private Selection selectedMode;
    private boolean isModeSelected = false;

    public CoffeeMachine() {
        this.amountOfWaterInMachine = 400;
        this.amountOfMilkInMachine = 540;
        this.amountOfCoffeeBeansInMachine = 120;
        this.amountOfDisposableCupsInMachine = 9;
        this.amountOfMoney = 550;
        this.start();
    }

    private void start() {
        buyingProcessNumber = 0;
        fillingProcessNumber = 0;
        isModeSelected = false;
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    public int getAmountOfWaterInMachine() {
        return amountOfWaterInMachine;
    }

    public int getAmountOfMilkInMachine() {
        return amountOfMilkInMachine;
    }

    public int getAmountOfCoffeeBeansInMachine() {
        return amountOfCoffeeBeansInMachine;
    }

    public int getAmountOfDisposableCupsInMachine() {
        return amountOfDisposableCupsInMachine;
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }




    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String command;

        CoffeeMachine coffeeMachine = new CoffeeMachine();

        while (true) {
            command = scanner.nextLine();
            if (coffeeMachine.input(command)) break;
        }
    }

    private boolean input(String command) {
        this.command = command.toUpperCase(Locale.ROOT);
        return takeCommand(this.command);
    }

    private boolean takeCommand(String command) {
        if (!isModeSelected) {
            try {
                selectedMode = Selection.valueOf(command);
                isModeSelected = true;
            } catch(Exception e) {
                System.out.println("Invalid Command...");
                return true;
            }
        }

        if (isModeSelected){
            this.command = command;

            switch (selectedMode) {
                case BUY:
                    buy(command);
                    break;
                case FILL:
                    fill(command);
                    break;
                case TAKE:
                    take();
                    start();
                    isModeSelected = false;
                    break;
                case REMAINING:
                    remaining();
                    start();
                    isModeSelected = false;
                    break;
                case EXIT:
                    return true;
            }
            return false;
        }
        return false;
    }

    private int buyingProcessNumber = 0;
    private void buy(String answer) {
        if (buyingProcessNumber == 0) {
            System.out.println();
            System.out.println("What do you want to buy? 1 - espresso, " +
                    "2 - latte, " +
                    "3 - cappuccino, " +
                    "back - to main menu:");
            buyingProcessNumber += 1;
        } else if (buyingProcessNumber == 1) {
            switch (answer) {
                case "1":
                    if (checkContents(answer)) {
                        System.out.println("I have enough resources, making you a coffee!");
                        System.out.println();
                        amountOfWaterInMachine -= espresso.getWater();
                        amountOfMilkInMachine -= espresso.getMilk();
                        amountOfCoffeeBeansInMachine -= espresso.getCoffeeBeans();
                        amountOfDisposableCupsInMachine -= 1;
                        amountOfMoney += espresso.getCost();
                    }
                    start();
                    break;

                case "2":
                    if (checkContents(answer)) {
                        System.out.println("I have enough resources, making you a coffee!");
                        System.out.println();
                        amountOfWaterInMachine -= latte.getWater();
                        amountOfMilkInMachine -= latte.getMilk();
                        amountOfCoffeeBeansInMachine -= latte.getCoffeeBeans();
                        amountOfDisposableCupsInMachine -= 1;
                        amountOfMoney += latte.getCost();
                    }
                    start();
                    break;

                case "3":
                    if (checkContents(answer)) {
                        System.out.println("I have enough resources, making you a coffee!");
                        System.out.println();
                        amountOfWaterInMachine -= cappuccino.getWater();
                        amountOfMilkInMachine -= cappuccino.getMilk();
                        amountOfCoffeeBeansInMachine -= cappuccino.getCoffeeBeans();
                        amountOfDisposableCupsInMachine -= 1;
                        amountOfMoney += cappuccino.getCost();
                    }
                    start();
                    break;

                case "BACK":
                    System.out.println();
                    start();
                    break;

                default:
                    System.out.println("Error!");
                    break;
            }
        }

    }

    private boolean checkContents(String answer) {
        boolean enoughContent = true;

        int toBeNeededWater = 0;
        int toBeNeededMilk = 0;
        int toBeNeededCoffeeBeans = 0;

        switch (answer) {
            case "1":
                toBeNeededWater = espresso.getWater();
                toBeNeededMilk = espresso.getMilk();
                toBeNeededCoffeeBeans = espresso.getCoffeeBeans();
                break;
            case "2":
                toBeNeededWater = latte.getWater();
                toBeNeededMilk = latte.getMilk();
                toBeNeededCoffeeBeans = latte.getCoffeeBeans();
                break;
            case "3":
                toBeNeededWater = cappuccino.getWater();
                toBeNeededMilk = cappuccino.getMilk();
                toBeNeededCoffeeBeans = cappuccino.getCoffeeBeans();
                break;
        }

        if (amountOfWaterInMachine < toBeNeededWater) {
            System.out.println("Sorry, not enough water!");
            enoughContent = false;
        }

        if (amountOfMilkInMachine < toBeNeededMilk) {
            System.out.println("Sorry, not enough milk!");
            enoughContent = false;
        }

        if (amountOfCoffeeBeansInMachine < toBeNeededCoffeeBeans) {
            System.out.println("Sorry, not enough coffee beans!");
            enoughContent = false;
        }

        if (amountOfDisposableCupsInMachine == 0) {
            System.out.println("Sorry, not enough disposable cups!");
            enoughContent = false;
        }

        return enoughContent;
    }

    private int fillingProcessNumber = 0;

    private void fill(String value) {
        if (fillingProcessNumber == 0) {
            System.out.println();
            System.out.println("Write how many ml of water do you want to add:");
            fillingProcessNumber += 1;
        } else if (fillingProcessNumber == 1) {
            amountOfWaterInMachine += Integer.parseInt(value);
            fillingProcessNumber += 1;
            System.out.println("Write how many ml of milk do you want to add:");
        } else if (fillingProcessNumber == 2) {
            amountOfMilkInMachine += Integer.parseInt(value);
            fillingProcessNumber += 1;
            System.out.println("Write how many grams of coffee beans do you want to add:");
        } else if (fillingProcessNumber == 3) {
            amountOfCoffeeBeansInMachine += Integer.parseInt(value);
            fillingProcessNumber += 1;
            System.out.println("Write how many disposable cups of coffee do you want to add:");
        } else if (fillingProcessNumber == 4) {
            amountOfDisposableCupsInMachine += Integer.parseInt(value);
            System.out.println();
            fillingProcessNumber = 0;
            start();
        }
    }

    private void take() {
        System.out.println();
        System.out.println("I gave you $" + getAmountOfMoney());
        setAmountOfMoney(0);
        System.out.println();
    }

    private void remaining() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.printf("%d of water\n", getAmountOfWaterInMachine());
        System.out.printf("%d of milk\n", getAmountOfMilkInMachine());
        System.out.printf("%d of coffee beans\n", getAmountOfCoffeeBeansInMachine());
        System.out.printf("%d of disposable cups\n", getAmountOfDisposableCupsInMachine());
        System.out.printf("%d of money\n", getAmountOfMoney());
        System.out.println();
    }


}
