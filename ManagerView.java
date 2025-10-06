import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ManagerView extends JFrame {
    private final LinkedHashMap<String, Integer> inventory = new LinkedHashMap<>() {{
        put("Black Tea", 500);   // oz
        put("Milk Powder", 200); // oz
        put("Taro Powder", 120);
        put("Boba Pearls", 300); // servings
        put("Cups (L)", 150);
        put("Cups (M)", 200);
    }};

    public ManagerView() {
        super("Manager – Inventory (Initial Step)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] cols = {"Item", "On Hand"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 1; }
            @Override public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? Integer.class : String.class;
            }
        };

        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            model.addRow(new Object[]{ e.getKey(), e.getValue() });
        }

        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save Changes (stub)");
        JButton reorderBtn = new JButton("Generate Reorder (stub)");

        reorderBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Suggested Reorder Items (qty<100):\n");
            inventory.forEach((k, v) -> { if (v < 100) sb.append(" - ").append(k).append(" ("+v+")\n"); });
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        south.add(saveBtn);
        south.add(reorderBtn);
        add(south, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagerView().setVisible(true));
    }
}

