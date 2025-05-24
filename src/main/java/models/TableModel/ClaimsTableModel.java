package models.TableModel;

import models.Claims;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClaimsTableModel extends AbstractTableModel {
    private final String[] columns = { "Claim Number", "Claimant Name", "Item Claimed", "Claimant Contact",
            "Claim Date", "Status"};
    private List<Claims> data = new ArrayList<>();

    public void setData(List<Claims> newData) {
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
        Claims record = data.get(row);
        return switch (col) {
            case 0 -> record.getClaimId();
            case 1 -> record.getClaimantName();
            case 2 -> record.getItemId();
            case 3 -> record.getClaimantContact();
            case 4 -> record.getClaimDate();
            case 5 -> record.getStatus();
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
