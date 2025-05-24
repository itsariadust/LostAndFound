package models.TableModel;

import models.LostItems;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class LostItemsTableModel extends AbstractTableModel {
    private final String[] columns = { "Item Number", "Item Name", "Category",
            "Location", "Date Found", "Found by", "Status" };
    private List<LostItems> data = new ArrayList<>();

    public void setData(List<LostItems> newData) {
        this.data = newData;
        fireTableDataChanged(); // Notify JTable to refresh
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        LostItems record = data.get(row);
        return switch (col) {
            case 0 -> record.getItemId();
            case 1 -> record.getItemName();
            case 2 -> record.getItemCategory();
            case 3 -> record.getLocationFound();
            case 4 -> record.getDateFound();
            case 5 -> record.getFoundBy();
            case 6 -> record.getStatus();
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    };
}
