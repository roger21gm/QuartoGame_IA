package Quatro;


import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
/*
 * Classe per fer que la taula mostri icones. Afegint un image renderer a les 
 * columnes.
 */

/**
 * @author Llorenç
 */
class ImageRenderer extends DefaultTableCellRenderer {

    JLabel lbl = new JLabel();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        lbl.setIcon((ImageIcon) value);
        return lbl;
    }
}
