// for composition in course

import java.io.Serializable;

public class Assignment implements Serializable{
    private String assignmentId;
    private String title;
    private int totalMarks;
    private String dueDate;

    //Constructor
    public Assignment(){
        this("", "", 0,"");
    }

     public Assignment(String assignmentId, String title, int totalMarks, String dueDate) {
        setAssignmentId(assignmentId);
        setTitle(title);
        setTotalMarks(totalMarks);
        setDueDate(dueDate);
    }
    
    

    //Setters, Getters, toString()
    public void setAssignmentId(String assignmentId) {
        if (assignmentId != null && !assignmentId.trim().equals("")) {
            this.assignmentId = assignmentId;
        } else {
            System.out.println("Invalid Assignment ID!");
            this.assignmentId = "A0";
        }
    }
   
    public void setTitle(String title) {
    if (title != null && !title.trim().equals("")) {
        this.title = title;
    } else {
        System.out.println("Invalid Title!");
        this.title = "Untitled";
    }
}
    public void setTotalMarks(int totalMarks) {
        if (totalMarks >= 0 ){
            this.totalMarks = totalMarks;
        }else{
            System.out.println("Invalid Total Marks! Setting it to 0.");
            this.totalMarks = 0;
        } 
    }
    //public void setDueDate(String dueDate) {
     //   this.dueDate = dueDate;
    //}
    public void setDueDate(String dueDate) {
    // expected format of datee dd-mm-yyyy
        try {
            String[] parts = dueDate.split("-");
            if (parts.length != 3) {
                System.out.println("Invalid date format!");
                this.dueDate = "01-06-2026";
                return;
            }
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            if (isValidDate(day, month)) {
                this.dueDate = dueDate;
            } else {
                System.out.println("Invalid date!");
                this.dueDate = "01-06-2026";
            } 
        }catch (Exception e) {   // to catch exception due to inavlid date such as ab-01-2026
        System.out.println("Invalid date format!");
        this.dueDate = "01-06-2026";
        }
    }

    //helper method for simple date logic 
    private static boolean isValidDate(int day, int month) {
        if (month < 1 || month > 12) {
            return false;
        }
        switch (month) {
            case 1: case 3: case 5: case 7:case 8: case 10: case 12:
                return (day >= 1 && day <= 31);

            case 4: case 6: case 9: case 11:
                return (day >= 1 && day <= 30);

            case 2:
                return (day >= 1 && day <= 28); //no leap yearr

            default:
                return false;
        }
    }
    public String getAssignmentId() {
        return assignmentId;
    }
    public String getTitle() {
        return title;
    }
    public int getTotalMarks() {
        return totalMarks;
    }
    public String getDueDate() {
        return dueDate;
    }
    @Override
    public String toString() {
        return "Assignment ID : " + assignmentId + "\nTitle : " + title + "\nTotal Marks : " + totalMarks+ "\nDue Date : " + dueDate;
    } 
}
