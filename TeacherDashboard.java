import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TeacherDashboard extends JFrame {

    Teacher teacher;
    JTabbedPane tabbedPane;
    // Shared reference so any tab can refresh My Courses immediately
    DefaultTableModel myCoursesModel;

    public TeacherDashboard(Teacher teacher) {
        this.teacher = teacher;

        setTitle("Teacher Dashboard - " + teacher.getTeacherId());
        setSize(800, 550);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SmartCampusRunner.saveSystemData();
                System.exit(0);
            }
        });
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("My Courses", buildMyCoursesPanel());
        tabbedPane.addTab("Timetable",  buildTimetablePanel());
        tabbedPane.addTab("Add / Remove Course", buildAddRemoveCoursePanel());
        tabbedPane.addTab("Manage Students",buildManageStudentsPanel());
        
   
        add(tabbedPane);
        setVisible(true);
    }

    // ===================== Myy course tab =====================
    private JPanel buildMyCoursesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = { "Course ID", "Course Name", "Time Slot", "Students" };
        myCoursesModel = new DefaultTableModel(cols, 0);
        JTable courseTable = new JTable(myCoursesModel);
        courseTable.setDefaultEditor(Object.class, null);
        refreshTeacherCourses(myCoursesModel);

        String[] sCols = { "Student Name", "Reg No", "CGPA" };
        DefaultTableModel studentModel = new DefaultTableModel(sCols, 0);
        JTable studentTable = new JTable(studentModel);
        studentTable.setDefaultEditor(Object.class, null);

        courseTable.getSelectionModel().addListSelectionListener(e -> {
            int row = courseTable.getSelectedRow();
            if (row < 0) return;
            studentModel.setRowCount(0);
            ArrayList<Course> courses = teacher.getCourses();
            if (row < courses.size()) {
                Course selected = courses.get(row);
                for (Student s : selected.getStudents()) {
                    studentModel.addRow(new Object[] {
                            s.getStudentName(), s.getRegNo(), s.getCgpa()
                    });
                }
            }
        });
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(courseTable),
                new JScrollPane(studentTable));
        split.setDividerLocation(200);

        JLabel hint = new JLabel("Click a course to see enrolled students", SwingConstants.CENTER);
        hint.setForeground(Color.GRAY);

        panel.add(split, BorderLayout.CENTER);
        panel.add(hint, BorderLayout.SOUTH);
        return panel;
    }
    private void refreshTeacherCourses(DefaultTableModel model) {
        
        System.out.println("Refreshing - teacher has " + teacher.getCourses().size() + " courses");
        model.setRowCount(0);
        for (Course c : teacher.getCourses()) {
            model.addRow(new Object[] {
                    c.getCourseId(), c.getCourseName(),
                    c.getTimeSlot(), c.getStudents().size()
            });
        }
        model.setRowCount(0);
        for (Course c : teacher.getCourses()) {
            model.addRow(new Object[] {
                    c.getCourseId(), c.getCourseName(),
                    c.getTimeSlot(), c.getStudents().size()
            });
        }
    }

    // ===================== Timetable tab=====================
    private JPanel buildTimetablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Class Timetable", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        StringBuilder sb = new StringBuilder();
        sb.append("===== YOUR TIMETABLE =====\n\n");
        for (Course c : teacher.getCourses()) {
            sb.append("Course : ").append(c.getCourseName()).append("\n");
            sb.append("ID     : ").append(c.getCourseId()).append("\n");
            sb.append("Time   : ").append(c.getTimeSlot()).append("\n");
            if (c.getClassroom() != null)
                sb.append("Room   : ").append(c.getClassroom().getName()).append("\n");
            sb.append("----------------------------\n");
        }
        area.setText(sb.toString());

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    // =================add /remove course tab =====================
    private JPanel buildAddRemoveCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Add or Remove a Course from Your List", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 15));

        String[] cols = { "Course ID", "Course Name", "Time Slot" };
        DefaultTableModel allCoursesModel = new DefaultTableModel(cols, 0);
        JTable allCoursesTable = new JTable(allCoursesModel);
        allCoursesTable.setDefaultEditor(Object.class, null);
        refreshAllCoursesTable(allCoursesModel);
        JScrollPane scroll = new JScrollPane(allCoursesTable);

        JLabel hint = new JLabel(
                "Select a course from the list above, then click Add or Remove.",
                SwingConstants.CENTER);
        hint.setForeground(Color.DARK_GRAY);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addBtn    = new JButton("Add Selected Course to My List");
        JButton removeBtn = new JButton("Remove Selected Course from My List");

        addBtn.addActionListener(e -> {
            int row = allCoursesTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a course from the table first.");
                return;
            }
            Course selected = SmartCampusRunner.courseRepo.getItem(row);
            if (selected == null) return;
            teacher.addCourse(selected);
            refreshTeacherCourses(myCoursesModel);

            // FIX: Save so teacher's course list persists across runs
            SmartCampusRunner.saveSystemData();
        });

        removeBtn.addActionListener(e -> {
            int row = allCoursesTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a course from the table first.");
                return;
            }
            Course selected = SmartCampusRunner.courseRepo.getItem(row);
            if (selected == null) return;
            teacher.removeCourse(selected);
            refreshTeacherCourses(myCoursesModel);

            // FIX: Save so teacher's course removal persists across runs
            SmartCampusRunner.saveSystemData();
        });

        btnPanel.add(addBtn);
        btnPanel.add(removeBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(hint, BorderLayout.NORTH);
        southPanel.add(btnPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }
    private void refreshAllCoursesTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Course c : SmartCampusRunner.courseRepo.getAllItems()) {
            model.addRow(new Object[] {
                    c.getCourseId(), c.getCourseName(), c.getTimeSlot()
            });
        }
    }

    // ===============manage students tab =====================
    private JPanel buildManageStudentsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Add or Remove Students from a Course", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 15));

        JPanel pickCoursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pickCoursePanel.add(new JLabel("Course index (from your list, starting at 0):"));
        JTextField courseIndexField = new JTextField(5);
        pickCoursePanel.add(courseIndexField);
        String[] sCols = { "Name", "Reg No", "Department" };
        DefaultTableModel studentModel = new DefaultTableModel(sCols, 0);
        JTable studentTable = new JTable(studentModel);
        studentTable.setDefaultEditor(Object.class, null);
        refreshAllStudentsTable(studentModel);
        JScrollPane scroll = new JScrollPane(studentTable);
        JLabel hint = new JLabel(
                "Select a student, enter course index, then click Add or Remove.",
                SwingConstants.CENTER);
        hint.setForeground(Color.DARK_GRAY);
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton addStudentBtn    = new JButton("Add Student to Course");
        JButton removeStudentBtn = new JButton("Remove Student from Course");
        JButton viewStudentsBtn  = new JButton("View Students in Course");
        addStudentBtn.addActionListener(e -> {
            int sRow = studentTable.getSelectedRow();
            if (sRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student.");
                return;
            }
            String idxText = courseIndexField.getText().trim();
            if (idxText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a course index.");
                return;
            }
            try {
                int idx = Integer.parseInt(idxText);
                ArrayList<Course> myCourses = teacher.getCourses();
                if (idx < 0 || idx >= myCourses.size()) {
                    JOptionPane.showMessageDialog(this, "Invalid index. You have "
                            + myCourses.size() + " course(s) (0 to " + (myCourses.size() - 1) + ").");
                    return;
                }
                Student selectedStudent = SmartCampusRunner.studentRepo.getItem(sRow);
                Course  selectedCourse  = myCourses.get(idx);
                teacher.addStudentToCourse(selectedStudent, selectedCourse);
                refreshTeacherCourses(myCoursesModel);

                // Savinggg it  immediatlyyy so added student persists across runs
                SmartCampusRunner.saveSystemData();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Course index must be a number.");
            }
        });

        removeStudentBtn.addActionListener(e -> {
            int sRow = studentTable.getSelectedRow();
            if (sRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student.");
                return;
            }
            String idxText = courseIndexField.getText().trim();
            if (idxText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a course index.");
                return;
            }
            try {
                int idx = Integer.parseInt(idxText);
                ArrayList<Course> myCourses = teacher.getCourses();
                if (idx < 0 || idx >= myCourses.size()) {
                    JOptionPane.showMessageDialog(this, "Invalid index.");
                    return;
                }
                Student selectedStudent = SmartCampusRunner.studentRepo.getItem(sRow);
                Course  selectedCourse  = myCourses.get(idx);
                teacher.removeStudentFromCourse(selectedStudent, selectedCourse);
                refreshTeacherCourses(myCoursesModel);

                // Savinggg it  jaldiii so removed student does NOT reappear on next run
                SmartCampusRunner.saveSystemData();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Course index must be a number.");
            }
        });
        viewStudentsBtn.addActionListener(e -> {
            String idxText = courseIndexField.getText().trim();
            if (idxText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a course index.");
                return;
            }
            try {
                int idx = Integer.parseInt(idxText);
                ArrayList<Course> myCourses = teacher.getCourses();
                if (idx < 0 || idx >= myCourses.size()) {
                    JOptionPane.showMessageDialog(this, "Invalid index.");
                    return;
                }
                Course selectedCourse = myCourses.get(idx);
                StringBuilder sb = new StringBuilder();
                sb.append("Students in: ").append(selectedCourse.getCourseName()).append("\n");
                sb.append("----------------------------\n");
                if (selectedCourse.getStudents().isEmpty()) {
                    sb.append("  No students enrolled.\n");
                } else {
                    for (Student s : selectedCourse.getStudents()) {
                        sb.append("  ").append(s.getStudentName())
                          .append("  (").append(s.getRegNo()).append(")\n");
                    }
                }
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
                JScrollPane popupScroll = new JScrollPane(textArea);
                popupScroll.setPreferredSize(new Dimension(350, 250));
                JOptionPane.showMessageDialog(this, popupScroll,
                        "Students in " + selectedCourse.getCourseName(),
                        JOptionPane.PLAIN_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Course index must be a number.");
            }
        });

        btnPanel.add(addStudentBtn);
        btnPanel.add(removeStudentBtn);
        btnPanel.add(viewStudentsBtn);

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.add(title, BorderLayout.NORTH);
        topSection.add(pickCoursePanel, BorderLayout.SOUTH);

        panel.add(topSection, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel bottomSection = new JPanel(new BorderLayout(5, 5));
        bottomSection.add(hint, BorderLayout.NORTH);
        bottomSection.add(btnPanel, BorderLayout.CENTER);
        panel.add(bottomSection, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshAllStudentsTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Student s : SmartCampusRunner.studentRepo.getAllItems()) {
            model.addRow(new Object[] {
                    s.getStudentName(), s.getRegNo(), s.getDepartmentName()
            });
        }
    }
}