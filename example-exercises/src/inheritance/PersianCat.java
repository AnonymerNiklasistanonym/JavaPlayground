package inheritance;

public class PersianCat extends Cat {
    @Override
    String getSound() {
        return super.getSound() + " (in Persian)";
    }
}
