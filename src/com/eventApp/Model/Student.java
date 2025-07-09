package com.eventApp.Model;

import java.util.List;

public class Student extends User{

    private String department;
    private int semester;
    private List<String> interest;

    public Student(String userId, String name, String email, String password, String role, String department, int semester, List<String> interest) {
        super(userId, name, email, password, role);
        this.department = department;
        this.semester = semester;
        this.interest = interest;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public List<String> getInterest() {
        return interest;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }
}
