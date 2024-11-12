package Services;

import Controller.Formatting;
import Model.Enums.Period;
import Model.Enums.Situation;
import Model.Enums.ToPrioritize;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDateTime;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class JTablesFormatting {

    Formatting format = new Formatting();
    static LocalDateTime date;

    public static void tableFormatColors(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.setHorizontalAlignment(JLabel.CENTER); // Adicione esta linha para centralizar o texto
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Suponha que a coluna que contém o valor "instalada" seja a penúltima coluna
                int columnIndex = table.getColumnCount() - 3; // Ajuste este valor conforme necessário
                String cellValue = (table.getValueAt(row, columnIndex).toString());
//                Period period = Period.fromString(table.getValueAt(row, 8) + "");
//                if (period.equals(Period.MORNING)) {
//                    date = Formatting.dateTimeFormaterBank2(table.getValueAt(row, 7) + " 12:00");
//                } else {
//                    date = Formatting.dateTimeFormaterBank2(table.getValueAt(row, 7) + " 18:00");
//                }

                if ("Cancelada".equals(cellValue)) {
                    c.setBackground(java.awt.Color.decode("#FFC7CE"));
                    c.setForeground(java.awt.Color.decode("#9C0006"));
                } else if ("Instalada".equals(cellValue)) {
                    c.setBackground(java.awt.Color.decode("#C6EFCE"));
                    c.setForeground(java.awt.Color.decode("#006100"));
                } else if (ToPrioritize.YES.toString()
                        .equals((table.getValueAt(row, table.getColumnCount() - 1).toString()))) {
                    c.setBackground(java.awt.Color.decode("#3a2a18"));
                    c.setForeground(java.awt.Color.white);
//                } 
//                else if (Situation.PROVISIONING.toString().equals(cellValue) 
//                        && date.isBefore(LocalDateTime.now())) {
//                    c.setBackground(java.awt.Color.decode("#2B1125"));
//                    c.setForeground(java.awt.Color.white);
                } else {
                    c.setBackground(java.awt.Color.decode("#FFE699"));
                    c.setForeground(java.awt.Color.decode("#9C5700"));
                }

                return c;
            }
        });
        table.setShowGrid(false);
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(java.awt.Color.decode("#7E5685"));
                setForeground(java.awt.Color.white);
                setHorizontalAlignment(JLabel.CENTER); // Adicione esta linha para centralizar o texto
                return this;
            }
        });

    }

}
