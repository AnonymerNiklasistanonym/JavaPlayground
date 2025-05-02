public class MainTypeErasure {
    public <T> void print(T anything) {
        System.out.println(anything);
    }

    public <T extends Number> void printNumber(T number) {
        System.out.println(number.doubleValue());
    }

    public static void main(String[] args) {
        var example = new MainTypeErasure();
        example.print(42);
        example.printNumber(42);
    }
}