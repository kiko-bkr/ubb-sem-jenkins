package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.*;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private Service service;

    @BeforeEach
    void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        this.service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @AfterEach
    void tearDown() {
        for (Student student : this.service.findAllStudents()) {
            this.service.deleteStudent(student.getID());
        }
    }

    /**
     * NOTE: I replaced the return code of student methods to return 0 on success and 1 on failure.
     * NOTE: I only replaced this for the student entity.
     */

    @Test
    void findAllStudents() {
        assertNotNull(this.service.findAllStudents());
    }

    @Test
    void saveStudent() {
        Integer resultCode = this.service.saveStudent("1", "Steve", 531);
        assertEquals(0, resultCode);
    }

    @Test
    void saveStudentWithEmptyId() {
        Integer resultCode = this.service.saveStudent("", "Steve", 531);
        assertNotEquals(0, resultCode);
    }

    @Test
    void saveStudentWithEmptyName() {
        Integer resultCode = this.service.saveStudent("1", "", 531);
        assertNotEquals(0, resultCode);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 100, 109, 939, 1000})
    void saveStudentWithInvalidGroup(int group) {
        Integer resultCode =  this.service.saveStudent("1", "Steve", group);
        assertNotEquals(0, resultCode);
    }

}