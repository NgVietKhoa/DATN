package mobileworld.tablecutoms.pay;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author RAVEN
 */
public class TableActionCellEditorDelete extends DefaultCellEditor {

    public TableActionCellEditorDelete() {
        super(new JCheckBox());
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        PanelActionDelete action = new PanelActionDelete();
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
