package com.lambdaschool.school.controller;

import com.lambdaschool.school.exception.ResourseNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.StudentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.web.PageableDefault;


import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    private static final Logger logger = LoggerFactory.getLogger(Student.class);

    @Autowired
    private StudentService studentService;

    // Please note there is no way to add students to course yet!

    @ApiOperation(value = "return a list of Students, supports pagination", response = Student.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "specifies the page that you want to access"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "specifies the page size"),
            @ApiImplicitParam(name = "sort", dataType = "string", allowMultiple = true, paramType = "query", value = "Sorts result [name, address, etc]")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "all students listed found", response = Student.class),
            @ApiResponse(code = 404, message = "no students found", response = ErrorDetail.class)
    })
    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents(@PageableDefault(page = 0, size = 2) Pageable pageable) {

        List<Student> myStudents = studentService.findAll(pageable);

        if (myStudents == null) {
            throw new ResourseNotFoundException("no students found");
        } else{
            logger.info("/students accessed");
        }

        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    @ApiOperation(value = "returns a single Student by id", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Student exists", response = Student.class),
            @ApiResponse(code = 404, message = "Student not found", response = ErrorDetail.class)
    })
    @GetMapping(value = "/Student/{StudentId}", produces = {"application/json"})
    public ResponseEntity<?> getStudentById(@ApiParam(value = "restaurant id", required = true, example = "1") @PathVariable Long StudentId) {

        Student r;

        if (studentService.findStudentById(StudentId) == null) {
            throw new ResourseNotFoundException("no student found with the id of " + StudentId);
        } else{
            r = studentService.findStudentById(StudentId);
            logger.info("/Student/{StudentId} accessed");
        }

        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @ApiOperation(value = "returns students with matching and similar names, supports pagination", response = Student.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "specifies the page that you want to access"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "specifies the page size"),
            @ApiImplicitParam(name = "sort", dataType = "string", allowMultiple = true, paramType = "query", value = "Sorts result [name, address, etc]")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "students found", response = Student.class),
            @ApiResponse(code = 404, message = "no student with that name found", response = ErrorDetail.class)
    })
    @GetMapping(value = "/student/namelike/{name}", produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(@PageableDefault(page = 0, size = 2) Pageable pageable, @PathVariable String name) {

        List<Student> myStudents = studentService.findStudentByNameLike(name, pageable);

        if (myStudents == null) {
            throw new ResourseNotFoundException("no student found with the name of " + name);
        } else{
            logger.info("/student/namelike/{name} accessed");
        }

        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    @ApiOperation(value = "adds a new student to the database", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "student successfully added", response = Student.class),
            @ApiResponse(code = 500, message = "failed to add student", response = ErrorDetail.class)
    })
    @PostMapping(value = "/Student", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid @RequestBody Student newStudent) throws URISyntaxException {

        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();

        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Studentid}").buildAndExpand(newStudent.getStudid()).toUri();

        responseHeaders.setLocation(newStudentURI);

        if (newStudentURI == null) {
            throw new ResourseNotFoundException("could not add student");
        } else{
            logger.info("/Student accessed");
        }

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @ApiOperation(value = "updates a student on the database", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "student successfully updated", response = Student.class),
            @ApiResponse(code = 500, message = "failed to update student", response = ErrorDetail.class)
    })
    @PutMapping(value = "/Student/update/{Studentid}")
    public ResponseEntity<?> updateStudent(@RequestBody Student updateStudent, @PathVariable long Studentid) {

        studentService.update(updateStudent, Studentid);

        if (studentService.update(updateStudent, Studentid) == null) {
            throw new ResourseNotFoundException("could not update student");
        } else{
            logger.info("//Student/{Studentid} accessed");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "deletes a student from the database", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "student successfully deleted", response = Student.class),
            @ApiResponse(code = 500, message = "failed to delete student", response = ErrorDetail.class)
    })
    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(@PathVariable long Studentid) {

        if (studentService.findStudentById(Studentid) == null) {
            throw new ResourseNotFoundException("could not find student with id of " + Studentid);
        } else{
            logger.info("/Student/{Studentid} accessed");
            studentService.delete(Studentid);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping(value = "/addStudent/{studentid}/toCourse/{courseid}", consumes = {"application/json"}, produces = {"application/json"})
//    public ResponseEntity<?> addStudentToCorse(@PathVariable long studentid, @PathVariable long courseid) {
//
//        studentService.addStudentToCourse(studentid, courseid);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
