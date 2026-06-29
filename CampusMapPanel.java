import javax.swing.*;
import java.awt.*;

public class CampusMapPanel extends JPanel {

    public CampusMapPanel() {
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(800, 600));
        addBlock("Outside Uni", 10, 10, 100, 80, Color.WHITE);
        addBlock("Gate 3", 115, 10, 100, 80, Color.CYAN);
        addBlock("Chemistry", 220, 10, 150, 80, Color.GREEN);
        addBlock("", 375, 10, 80, 80, Color.LIGHT_GRAY);
        addBlock("Sports Complex", 460, 10, 190, 80, Color.ORANGE);
        addBlock("Pathway", 10, 100, 780, 20, Color.GRAY);
        addBlock("Gate 1", 10, 125, 90, 80, Color.CYAN);
        addBlock("AB-1", 105, 125, 90, 80, Color.GREEN);
        addBlock("Physics Block", 200, 125, 90, 80, Color.GREEN);
        addBlock("AB-2", 295, 125, 90, 80, Color.ORANGE);
        addBlock("FB-1", 390, 125, 90, 80, Color.GREEN);
        addBlock("FB-2", 485, 125, 90, 80, Color.ORANGE);
        addBlock("Cafe", 580, 125, 90, 80, Color.ORANGE);
        addBlock("AB-3", 680, 100, 110, 55, Color.GREEN);
        addBlock("Library", 680, 160, 110, 45, Color.MAGENTA);
        addBlock("N Block Closed", 680, 210, 110, 55, Color.RED);
        addBlock("Pathway", 10, 210, 665, 20, Color.GRAY);
        addBlock("Lawn", 10, 235, 665, 60, new Color(144, 238, 144));
        addBlock("Parking", 10, 300, 780, 50, Color.DARK_GRAY);

        addLegend();
    }

    private void addBlock(String name, int x, int y, int w, int h, Color bg) {
        JLabel lbl = new JLabel(name, SwingConstants.CENTER);
        lbl.setBounds(x, y, w, h);
        lbl.setOpaque(true);
        lbl.setBackground(bg);
        lbl.setForeground(bg == Color.DARK_GRAY ? Color.WHITE : Color.BLACK);
        lbl.setFont(new Font("Arial", Font.BOLD, 11));
        lbl.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        add(lbl);
    }
    private void addLegend() {
        JLabel s = new JLabel("Status:");
        s.setBounds(10, 365, 60, 20);
        s.setFont(new Font("Arial", Font.BOLD, 11));
        add(s);

        JLabel a = new JLabel("Green = Active");
        a.setBounds(75, 365, 120, 20);
        a.setFont(new Font("Arial", Font.BOLD, 11));
        a.setForeground(new Color(0, 140, 0));
        add(a);

        JLabel b = new JLabel("Orange = Busy");
        b.setBounds(205, 365, 120, 20);
        b.setFont(new Font("Arial", Font.BOLD, 11));
        b.setForeground(new Color(200, 100, 0));
        add(b);

        JLabel c = new JLabel("Red = Closed");
        c.setBounds(335, 365, 110, 20);
        c.setFont(new Font("Arial", Font.BOLD, 11));
        c.setForeground(Color.RED);
        add(c);
    }

    
}