package inheritance;

public class BigPersianCat extends Cat {
    @Override
    String getSound() {
        return super.getSound() + " (loud)";
    }
}
