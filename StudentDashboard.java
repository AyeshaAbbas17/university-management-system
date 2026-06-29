import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentDashboard extends JFrame {

    Student student;
    public StudentDashboard(Student student) {
        this.student = student;

        setTitle("Student Dashboard - " + student.getStudentName());
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("My Schedule", buildSchedulePanel());
        tabs.addTab("My Report", buildReportPanel());
        tabs.addTab("Campus Map", new CampusMapPanel());

        add(tabs);
        setVisible(true);
    }

    // ===================== Schedule Tab =====================
    private JPanel buildSchedulePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("My Class Schedule - " + student.getStudentName(),
                SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(new Color(0, 102, 204));

        String[] cols = { "Course Name", "Course ID", "Time Slot", "Classroom" };
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Course c : student.getEnrolledCourses()) {
            String room = (c.getClassroom() != null) ? c.getClassroom().getName() : "Not Assigned";
            model.addRow(new Object[]{
                    c.getCourseName(), c.getCourseId(), c.getTimeSlot(), room
            });
        }
        JTable table = new JTable(model);
        table.setRowHeight(25);

        JLabel infoLabel = new JLabel(
                "Reg No: " + student.getRegNo() +
                "   |   Department: " + student.getDepartmentName(),
                SwingConstants.CENTER);
        infoLabel.setForeground(Color.GRAY);

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(infoLabel, BorderLayout.SOUTH);
        return panel;
    }

    // ===================== Reportttt Tab =====================
    private JPanel buildReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("My Assignment Report", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(new Color(0, 102, 204));

        String[] cols = { "Assignment", "Total Marks", "Obtained Marks" };
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        // Look through all mappings to find this student's records
        for (StudentAssignmentMapping m : SmartCampusRunner.mappings) {
            if (m.getStudent().getRegNo().equals(student.getRegNo())) {
                Assignment a = m.getAssignment();
                model.addRow(new Object[]{
                        a.getTitle(), a.getTotalMarks(), m.getObtainedMarks()
                });
            }
        }
        JTable table = new JTable(model);
        table.setRowHeight(25);

        JLabel cgpaLabel = new JLabel(
                "CGPA: " + student.getCgpa() +
                "   |   Reg No: " + student.getRegNo() +
                "   |   Dept: " + student.getDepartmentName(),
                SwingConstants.CENTER);

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(cgpaLabel, BorderLayout.SOUTH);
        return panel;
    }
}