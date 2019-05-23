package com.lambdaschool.school.controller;

import com.lambdaschool.school.exception.ResourseNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    private static final Logger logger = LoggerFactory.getLogger(Course.class);

    @Autowired
    private CourseService courseService;


    @ApiOperation(value = "return a list of courses, supports pagination", response = Course.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "specifies the page that you want to access"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "specifies the page size"),
            @ApiImplicitParam(name = "sort", dataType = "string", allowMultiple = true, paramType = "query", value = "Sorts result [name, address, etc]")
    })
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses(@PageableDefault(page = 0, size = 2) Pageable pageable)
    {

        ArrayList<Course> myCourses = courseService.findAll(pageable);

        if (myCourses == null) {
            throw new ResourseNotFoundException("no students found");
        } else{
            logger.info("/courses accessed");
        }

        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value = "return a count of all students in each course", response = Course.class, responseContainer = "List")
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {


        if (courseService.getCountStudentsInCourse() == null) {
            throw new ResourseNotFoundException("no student count found");
        } else{
            logger.info("/studcount accessed");
        }

        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }

    @ApiOperation(value = "deletes a student from the database", response = Course.class)
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@PathVariable long courseid)
    {

        logger.info("/courses/{courseid} accessed");

        courseService.delete(courseid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
