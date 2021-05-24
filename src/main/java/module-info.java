module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
	requires java.sql;
	requires transitive javafx.graphics;
	requires javafx.base;
    opens application to javafx.fxml;
    exports application;
}
