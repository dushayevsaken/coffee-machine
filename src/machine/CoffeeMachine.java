package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;
    private State state;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        String input = scanner.next();
        while (!input.equals("exit")) {
            switch (coffeeMachine.state) {
                case CHOOSING_AN_ACTION:
                    coffeeMachine.action(input);
                    break;
                case CHOOSING_A_VARIANT_OF_COFFEE:
                    coffeeMachine.buy(input);
                    break;
                case FILLING_WATER:
                    coffeeMachine.fillWater(input);
                    break;
                case FILLING_MILK:
                    coffeeMachine.fillMilk(input);
                    break;
                case FILLING_BEANS:
                    coffeeMachine.fillBeans(input);
                    break;
                case FILLING_CUPS:
                    coffeeMachine.fillCups(input);
                    break;
            }
            input = scanner.next();
        }
    }

    public CoffeeMachine() {
        water = 400;
        milk = 540;
        beans = 120;
        cups = 9;
        money = 550;
        askAction();
    }

    private void askAction() {
        System.out.println("\nWrite action (buy, fill, take, remaining, exit): ");
        state = State.CHOOSING_AN_ACTION;
    }

    private void action(String action) {
        switch (action) {
            case "buy":
                askCoffee();
                break;
            case "fill":
                askWater();
                break;
            case "take":
                take();
                break;
            case "remaining":
                printState();
                break;
            default:
                break;
        }
    }

    private void askCoffee() {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        this.state = State.CHOOSING_A_VARIANT_OF_COFFEE;
    }

    private void buy(String coffeeId) {
        if (coffeeId.equals("back")) {
            askAction();
            return;
        }
        CoffeeType coffee = CoffeeType.get(coffeeId);
        assert coffee != null;
        if (water < coffee.water) System.out.println("Sorry, not enough water!");
        else if (milk < coffee.milk) System.out.println("Sorry, not enough milk!");
        else if (beans < coffee.beans) System.out.println("Sorry, not enough coffee beans!");
        else if (cups == 0) System.out.println("Sorry, not enough cups!");
        else {
            System.out.println("I have enough resources, making you a coffee!");
            water -= coffee.water;
            milk -= coffee.milk;
            beans -= coffee.beans;
            cups--;
            money += coffee.price;
        }
        askAction();
    }

    private void askWater() {
        System.out.println("\nWrite how many ml of water you want to add: ");
        this.state = State.FILLING_WATER;
    }

    private void fillWater(String water) {
        this.water += Integer.parseInt(water);
        System.out.println("Write how many ml of milk you want to add: ");
        this.state = State.FILLING_MILK;
    }

    private void fillMilk(String milk) {
        this.milk += Integer.parseInt(milk);
        System.out.println("Write how many grams of coffee beans you want to add: ");
        this.state = State.FILLING_BEANS;
    }

    private void fillBeans(String beans) {
        this.beans += Integer.parseInt(beans);
        System.out.println("Write how many grams of coffee beans you want to add: ");
        this.state = State.FILLING_CUPS;
    }

    private void fillCups(String cups) {
        this.cups += Integer.parseInt(cups);
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        askAction();
    }

    private void take() {
        System.out.println("I gave you $" + money);
        money = 0;
        askAction();
    }

    private void printState() {
        System.out.println("\nThe coffee machine has:\n" +
                water + " ml of water\n" +
                milk + " ml of milk\n" +
                beans + " g of coffee beans\n" +
                cups + " disposable cups\n" +
                "$" + money + " of money");
        askAction();
    }


    enum CoffeeType {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);

        final int water;
        final int milk;
        final int beans;
        final int price;

        public static CoffeeType get(String id) {
            switch (id) {
                case "1": return CoffeeType.ESPRESSO;
                case "2": return CoffeeType.LATTE;
                case "3": return CoffeeType.CAPPUCCINO;
                default: return null;
            }
        }

        CoffeeType(int water, int milk, int beans, int price) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.price = price;
        }
    }

    enum State {
        CHOOSING_AN_ACTION,
        CHOOSING_A_VARIANT_OF_COFFEE,
        FILLING_WATER,
        FILLING_MILK,
        FILLING_BEANS,
        FILLING_CUPS
    }
}


