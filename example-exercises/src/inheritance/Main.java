package inheritance;

public class Main {
    public static void main(String[] args) {
        final var animals = new Animal[]{
                new Dog(),
                new Cat(),
                new PersianCat(),
                new BigPersianCat(),
        };
        for (final var animal : animals) {
            System.out.println(animal.getSound());
        }
        System.out.printf("Dogs make:%n");
        for (final var animal : animals) {
            if (animal instanceof Dog dog) {
                System.out.println(dog.getSound());
            }
        }
    }
}
