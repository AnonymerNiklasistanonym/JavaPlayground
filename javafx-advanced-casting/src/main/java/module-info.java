module anonymerniklasistanonym.javafxadvancedcasting {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens anonymerniklasistanonym.javafxadvancedcasting to javafx.fxml;
    exports anonymerniklasistanonym.javafxadvancedcasting;
}