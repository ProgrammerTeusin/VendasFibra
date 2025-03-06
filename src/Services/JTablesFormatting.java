package Services;

import Controller.Formatting;
import Model.Enums.Period;
import Model.Enums.Situation;
import Model.Enums.ToPrioritize;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class JTablesFormatting {

    Formatting format = new Formatting();
    static LocalDateTime date;
    static int alter = 1;
    public static int allLinesSelected = 0;
    public static int[] positionss;
    static Set<Integer> linhasSelecionadas = new HashSet<>();

    public static void tableFormatColors(JTable table) {

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.setHorizontalAlignment(JLabel.CENTER); // Adicione esta linha para centralizar o texto
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Suponha que a coluna que contém o valor "instalada" seja a penúltima coluna
                int columnIndex = table.getColumnCount() - 3; // Ajuste este valor conforme necessário
                String cellValue = (table.getValueAt(row, columnIndex).toString());

                if (isSelected) {

                    c.setBackground(java.awt.Color.decode("#4B0082"));
                    c.setForeground(java.awt.Color.decode("#FFFFFF"));
                    System.out.println(alter + ": Linhas selecionadas: " + Arrays.toString(table.getSelectedRows()));
                    positionss = table.getSelectedRows();
                    allLinesSelected = positionss.length;
                    alter++;
                } else {
                    updateColorsSituation(cellValue, c, table, row);
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

        updateTableLinesSelectedMouseAction(table);
    }

    public static void updateColorsSituation(String cellValue, Component c, JTable table1, int row) throws NumberFormatException {
        if ("Cancelada".equals(cellValue)) {
            c.setBackground(java.awt.Color.decode("#FFC7CE"));
            c.setForeground(java.awt.Color.decode("#9C0006"));
        } else if ("Instalada".equals(cellValue)) {
            c.setBackground(java.awt.Color.decode("#C6EFCE"));
            c.setForeground(java.awt.Color.decode("#006100"));
        } else if (ToPrioritize.YES.toString().equals(table1.getValueAt(row, table1.getColumnCount() - 1).toString())) {
            c.setBackground(java.awt.Color.decode("#3a2a18"));
            c.setForeground(java.awt.Color.white);
        } else {
            c.setBackground(java.awt.Color.decode("#FFE699"));
            c.setForeground(java.awt.Color.decode("#9C5700"));
        }
    }

    public static void updateTableLinesSelectedMouseAction(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.rowAtPoint(e.getPoint());
                if (linhasSelecionadas.contains(selectedRow)) {
                    linhasSelecionadas.remove(selectedRow); // Deseleciona a linha se já estiver selecionada
                    System.out.println("tirou linha");
                } else {
                    linhasSelecionadas.add(selectedRow); // Seleciona a linha
                    System.out.println("acrescentou linha");
                }
                table.repaint();
            }
        });
    }

    public static void tableFormatColorsSelected(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.setHorizontalAlignment(JLabel.CENTER); // Adicione esta linha para centralizar o texto
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(java.awt.Color.decode("#4B0082"));
                    c.setForeground(java.awt.Color.decode("#ffff"));
                }

                return c;
            }
        });

    }

}
