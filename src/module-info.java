module Hospital_Inventory {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens model.mainInventory to javafx.base;
    opens model.regulatory to javafx.base;
    opens model.user to javafx.base;
    opens model.hospitalStaff to javafx.base;

}
