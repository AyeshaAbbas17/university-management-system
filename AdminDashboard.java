import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AdminDashboard extends JFrame {

    Admin admin;
    JTabbedPane tabbedPane;

    public AdminDashboard(Admin admin) {
        this.admin = admin;

        setTitle("Admin Dashboard - " + admin.getAdminName());
        setSize(850, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SmartCampusRunner.saveSystemData();
                System.exit(0);
            }
        });

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Students", buildStudentPanel());
        tabbedPane.addTab("Courses", buildCoursePanel());
        tabbedPane.addTab("Campus Map", new CampusMapPanel());
        tabbedPane.addTab("Emergency", buildEmergencyPanel());
        tabbedPane.addTab("Department Report", buildDepartmentReportPanel());
        tabbedPane.addTab("Library Report", buildLibraryReportPanel());
        tabbedPane.addTab("Cafeteria Menu", buildCafeteriaPanel());
        tabbedPane.addTab("Classroom Check", buildClassroomCheckPanel());

        add(tabbedPane);
        setVisible(true);
    }

    // ============ Student Tab ===============
    private JPanel buildStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = { "Name", "Reg No", "Department", "CGPA" };
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        refreshStudentTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField nameF = new JTextField();
        JTextField regF  = new JTextField();
        JTextField deptF = new JTextField();
        JTextField cgpaF = new JTextField();
        JTextField userF = new JTextField();
        JTextField passF = new JTextField();

        formPanel.add(new JLabel("Name:"));        formPanel.add(nameF);
        formPanel.add(new JLabel("Reg No:"));      formPanel.add(regF);
        formPanel.add(new JLabel("Department:"));  formPanel.add(deptF);
        formPanel.add(new JLabel("CGPA:"));        formPanel.add(cgpaF);
        formPanel.add(new JLabel("Username:"));    formPanel.add(userF);
        formPanel.add(new JLabel("Password:"));    formPanel.add(passF);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Student");
        JButton delBtn = new JButton("Delete Selected");

        addBtn.addActionListener(e -> {
            try {
                String name = nameF.getText().trim();
                String reg  = regF.getText().trim();
                String dept = deptF.getText().trim();
                String user = userF.getText().trim();
                String pass = passF.getText().trim();

                double cgpa = Double.parseDouble(cgpaF.getText().trim());
                if (cgpa < 0 || cgpa > 4.0) {
                    JOptionPane.showMessageDialog(this, "CGPA must be between 0.0 and 4.0!");
                    return;
                }
                if (name.isEmpty() || reg.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!");
                    return;
                }

                Student s = new Student(user, pass, "STUDENT",
                        name, reg, dept, cgpa, new ArrayList<>());

                // Add to studentRepo so student appears in lists
                SmartCampusRunner.studentRepo.addItem(s);
                // Add to userRepo so the student is saved to file on next saveSystemData()
                SmartCampusRunner.userRepo.addItem(s);
                //Add to authManager so the student can log in immediately
                SmartCampusRunner.authManager.addUser(s);
                // Save immediately so this student persists across runs
                SmartCampusRunner.saveSystemData();
                refreshStudentTable(model);
                nameF.setText(""); regF.setText(""); deptF.setText("");
                cgpaF.setText(""); userF.setText(""); passF.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "CGPA must be a number (e.g. 3.5)");
            }
        });

         delBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a student first!");
                return;
            }
            Student s = SmartCampusRunner.studentRepo.getItem(row);

//===============================================================
// to remove that student from all the enrolled courses
            for (Course c : SmartCampusRunner.courseRepo.getAllItems()) {
                c.removeStudent(s);
            }
//===============================================================

            //Also remove from userRepo so deletion persists across runs
            SmartCampusRunner.userRepo.removeItem(s);
            SmartCampusRunner.studentRepo.removeItem(s);
            //Save immediately after deletion
            SmartCampusRunner.saveSystemData();

            refreshStudentTable(model);
        });
 
        btnPanel.add(addBtn);
        btnPanel.add(delBtn);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.EAST);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }
    private void refreshStudentTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Student s : SmartCampusRunner.studentRepo.getAllItems()) {
            model.addRow(new Object[] {
                    s.getStudentName(), s.getRegNo(),
                    s.getDepartmentName(), s.getCgpa()
            });
        }
    }
    // ========== Course Tab ================
    private JPanel buildCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = { "Course ID", "Course Name", "Credit Hours", "Time Slot" };
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        refreshCourseTable(model);
        JScrollPane scroll = new JScrollPane(table);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField idF     = new JTextField();
        JTextField nameF   = new JTextField();
        JTextField creditF = new JTextField();
        JTextField timeF   = new JTextField();

        formPanel.add(new JLabel("Course ID:"));    formPanel.add(idF);
        formPanel.add(new JLabel("Course Name:"));  formPanel.add(nameF);
        formPanel.add(new JLabel("Credit Hours:")); formPanel.add(creditF);
        formPanel.add(new JLabel("Time Slot:"));    formPanel.add(timeF);
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Course");
        JButton delBtn = new JButton("Delete Selected");

        addBtn.addActionListener(e -> {
            try {
                String id   = idF.getText().trim();
                String name = nameF.getText().trim();
                int credit  = Integer.parseInt(creditF.getText().trim());
                String time = timeF.getText().trim();

                if (id.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID and Name required!");
                    return;
                }

                Course c = new Course(new ArrayList<>(), id, name, credit,
                        null, time, null, false);
                SmartCampusRunner.courseRepo.addItem(c);

                // Save immediately so newly added course persists across runs
                SmartCampusRunner.saveSystemData();
                refreshCourseTable(model);
                idF.setText(""); nameF.setText("");
                creditF.setText(""); timeF.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Credit hours must be a number!");
            }
        });
       //remove course from teacher tab as wellll in section 1
       delBtn.addActionListener(e -> {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a course!");
            return;
        }
        Course courseToRemove = SmartCampusRunner.courseRepo.getItem(row);
        if (courseToRemove == null) return;
        for (User u : SmartCampusRunner.authManager.getAllUsers()) {
            if (u instanceof Teacher) {
                Teacher t = (Teacher) u;
                t.removeCourse(courseToRemove);
            }
        }
        courseToRemove.getStudents().clear();
        for (Student s : SmartCampusRunner.studentRepo.getAllItems()) {
             s.getEnrolledCourses().remove(courseToRemove);
         }
        SmartCampusRunner.courseRepo.removeItem(courseToRemove);
        SmartCampusRunner.saveSystemData();
        refreshCourseTable(model);
        });
       
        btnPanel.add(addBtn);
        btnPanel.add(delBtn);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.EAST);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshCourseTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Course c : SmartCampusRunner.courseRepo.getAllItems()) {
            model.addRow(new Object[] {
                    c.getCourseId(), c.getCourseName(),
                    c.getCreditHours(), c.getTimeSlot()
            });
        }
    }

    // ===================== Emergency Tab =====================
    private JPanel buildEmergencyPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
        logArea.setBackground(new Color(240, 240, 240));
        JScrollPane scroll = new JScrollPane(logArea);

        JButton emergencyBtn = new JButton("Trigger Medical Emergency");
        emergencyBtn.setBackground(Color.RED);
        emergencyBtn.setForeground(Color.WHITE);
        emergencyBtn.setFont(new Font("Arial", Font.BOLD, 14));

        emergencyBtn.addActionListener(e -> {
            logArea.append("=== Medical Emergency Triggered! ===\n");
            logArea.append("Notifying Health Center...\n");
            logArea.append("Notifying Security Service...\n");
            if (SmartCampusRunner.healthCenter != null)
                SmartCampusRunner.healthCenter.sendNotification();
            if (SmartCampusRunner.securityService != null)
                SmartCampusRunner.securityService.sendNotification();
            logArea.append("All services notified.\n\n");
            JOptionPane.showMessageDialog(this,
                    "Emergency alert sent to Health Center and Security!",
                    "Emergency", JOptionPane.WARNING_MESSAGE);
        });

        JLabel note = new JLabel("Admin Emergency Controls", SwingConstants.CENTER);
        note.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(note, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(emergencyBtn, BorderLayout.SOUTH);
        return panel;
    }

    // ===================== Department Report Tab =====================
    private JPanel buildDepartmentReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Department Report", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        reportArea.setBackground(new Color(240, 240, 240));
        Department dept = SmartCampusRunner.csDept;
        if (dept == null) {
            reportArea.setText("No department data available.\n");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("===== Department Report =====\n\n");
            sb.append("Department Name  : ").append(dept.getName()).append("\n");
            sb.append("Location         : ").append(dept.getLocation()).append("\n");
            sb.append("Total Courses    : ").append(dept.getCourses().size()).append("\n");
            sb.append("Total Students   : ").append(dept.getTotalStudents()).append("\n");
            sb.append("Department Budget: ").append(dept.getDepartmentBudget()).append("\n");
            sb.append("Operational Cost : ").append(dept.calculateOperationalCost()).append("\n\n");
            sb.append("----- Courses in Department -----\n");
            for (Course c : dept.getCourses()) {
                sb.append("  Course: ").append(c.getCourseName())
                  .append("  |  ID: ").append(c.getCourseId())
                  .append("  |  Students: ").append(c.getStudents().size()).append("\n");
            }
            reportArea.setText(sb.toString());
        }
        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return panel;
    }
    // ===================== Library Report Tab =====================
    private JPanel buildLibraryReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("Library Report", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        reportArea.setBackground(new Color(240, 240, 240));

        Library lib = SmartCampusRunner.library;
        if (lib == null) {
            reportArea.setText("No library data available.\n");
        } else {
            int totalBooks = lib.getBooks().size();
            int issuedBooks = 0;
            for (Book b : lib.getBooks()) {
                if (b.isIssued()) issuedBooks++;
            }
            int availableBooks = totalBooks - issuedBooks;
            StringBuilder sb = new StringBuilder();
            sb.append("===== Library Usage Report =====\n\n");
            sb.append("Library Name    : ").append(lib.getName()).append("\n");
            sb.append("Location        : ").append(lib.getLocation()).append("\n");
            sb.append("Is Open         : ").append(lib.isOpen()).append("\n");
            sb.append("Daily Visitors  : ").append(lib.getDailyVisitors()).append("\n");
            sb.append("Total Books     : ").append(totalBooks).append("\n");
            sb.append("Books Issued    : ").append(issuedBooks).append("\n");
            sb.append("Books Available : ").append(availableBooks).append("\n");
            sb.append("Operational Cost: ").append(lib.calculateOperationalCost()).append("\n");
            reportArea.setText(sb.toString());
        }

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return panel;
    }

    // ================= Cafee Menu Tab=====================
    private JPanel buildCafeteriaPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Cafeteria Menu", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea menuArea = new JTextArea();
        menuArea.setEditable(false);
        menuArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        menuArea.setBackground(new Color(240, 240, 240));

        Cafeteria cafe = SmartCampusRunner.cafeteria;
        if (cafe == null) {
            menuArea.setText("Cafeteria data not available.\n");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("===== Cafeteria: ").append(cafe.getName()).append(" =====\n\n");
            sb.append("Location        : ").append(cafe.getLocation()).append("\n");
            sb.append("Is Open         : ").append(cafe.isOpen()).append("\n");
            sb.append("Daily Customers : ").append(cafe.getDailyCustomers()).append("\n");
            sb.append("Food Cost/Day   : ").append(cafe.getFoodCost()).append("\n");
            sb.append("Operational Cost: ").append(cafe.calculateOperationalCost()).append("\n\n");
            sb.append("---- Menu Items ----\n");
            ArrayList<String> items = cafe.getMenuItems();
            if (items.isEmpty()) {
                sb.append("  (No items in menu yet)\n");
            } else {
                for (String item : items) {
                    sb.append("  - ").append(item).append("\n");
                }
            }
            menuArea.setText(sb.toString());
        }

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(menuArea), BorderLayout.CENTER);
        return panel;
    }
    // ================== Classroom Check Tab ==================
    private JPanel buildClassroomCheckPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Classroom & Schedule Conflict Check", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        logArea.setBackground(new Color(240, 240, 240));
        JScrollPane scroll = new JScrollPane(logArea);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton checkClassroomBtn  = new JButton("Check Classroom Availability");
        JButton resolveConflictBtn = new JButton("Resolve Schedule Conflict");

        checkClassroomBtn.addActionListener(e -> {
            logArea.append("=== Checking Classroom Availability ===\n");
            Course firstCourse = SmartCampusRunner.courseRepo.getItem(0);
            Classroom classroom = SmartCampusRunner.c1;

            if (firstCourse == null) { logArea.append("No courses found.\n\n"); return; }
            if (classroom == null)   { logArea.append("No classroom data available.\n\n"); return; }
            logArea.append("Course    : " + firstCourse.getCourseName() + "\n");
            logArea.append("Classroom : " + classroom.getName() + "\n");
            logArea.append("Available : " + classroom.isAvailable() + "\n");

            if (classroom.isAvailable()) {
                logArea.append("Result    : Classroom is available. Schedule confirmed.\n\n");
            } else {
                logArea.append("Result    : Classroom unavailable! Rescheduling...\n");
                logArea.append("New Time  : 11:00 AM - 1:00 PM\n");
                logArea.append("Room      : Alternative Classroom Assigned\n\n");
            }
        });
        resolveConflictBtn.addActionListener(e -> {
            logArea.append("=== Checking for Schedule Conflicts ===\n");
            Course c1 = SmartCampusRunner.courseRepo.getItem(0);
            Course c2 = SmartCampusRunner.courseRepo.getItem(1);
            if (c1 == null || c2 == null) {
                logArea.append("Need at least 2 courses in the system.\n\n");
                return;
            }
            logArea.append("Course 1: " + c1.getCourseName() + " | Time: " + c1.getTimeSlot() + "\n");
            logArea.append("Course 2: " + c2.getCourseName() + " | Time: " + c2.getTimeSlot() + "\n");

            if (c1.getClassroom() != null && c2.getClassroom() != null
                    && c1.getClassroom() == c2.getClassroom()
                    && c1.getTimeSlot() != null
                    && c1.getTimeSlot().equals(c2.getTimeSlot())) {
                logArea.append("Conflict Detected! Same room and same time slot.\n");
                c2.setTimeSlot("1:00 PM - 3:00 PM");
                logArea.append(c2.getCourseName() + " rescheduled to: 1:00 PM - 3:00 PM\n\n");
                JOptionPane.showMessageDialog(this,
                        c2.getCourseName() + " rescheduled to 1:00 PM - 3:00 PM",
                        "Conflict Resolved", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logArea.append("No conflict found.\n\n");
            }
        });
        btnPanel.add(checkClassroomBtn);
        btnPanel.add(resolveConflictBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }
}