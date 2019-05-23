package com.lambdaschool.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student
{
    private static final Logger logger = LoggerFactory.getLogger(Course.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long studid;

    private String studname;

    @ManyToMany
    @JoinTable(name = "studcourses",
               joinColumns = {@JoinColumn(name = "studid")},
               inverseJoinColumns = {@JoinColumn(name = "courseid")})
    @JsonIgnoreProperties("students")
    private List<Course> courses = new ArrayList<>();

    public Student()
    {
    }

    public Student(String studname)
    {
        this.studname = studname;

        logger.info("Country accessed");
        logger.debug("created student with name of: " + this.studname);
    }

    public long getStudid()
    {
        return studid;
    }

    public void setStudid(long studid)
    {
        this.studid = studid;
    }

    public String getStudname()
    {
        return studname;
    }

    public void setStudname(String studname)
    {
        this.studname = studname;
    }

    public List<Course> getCourses()
    {
        return courses;
    }

    public void setCourses(List<Course> courses)
    {
        this.courses = courses;
    }
}
