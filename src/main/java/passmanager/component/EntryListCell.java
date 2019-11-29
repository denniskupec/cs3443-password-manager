package passmanager.component;

import javafx.scene.control.ListCell;
import passmanager.model.PasswordEntry;

public class EntryListCell extends ListCell<PasswordEntry> {

	@Override
	protected void updateItem(PasswordEntry item, boolean empty) {
		super.updateItem(item, empty);
		
		if (item == null || empty) {
			setGraphic(null);
			setText(null);
		}
		else {
			EntryListCellController controller = new EntryListCellController(this);
			controller.setData(item);
		}
	}
	
}
