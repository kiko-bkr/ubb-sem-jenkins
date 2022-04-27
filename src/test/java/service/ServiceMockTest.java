package service;

import domain.Homework;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceMockTest {
    private Service service;

    @Mock
    private StudentXMLRepository studentXMLRepository;

    @Mock
    private HomeworkXMLRepository homeworkXMLRepository;

    @Mock
    private GradeXMLRepository gradeXMLRepository;

    @Captor
    ArgumentCaptor<Homework> homeworkArgumentCaptor;

    @BeforeEach
    void init() {
        studentXMLRepository = Mockito.mock(StudentXMLRepository.class);
        homeworkXMLRepository = Mockito.mock(HomeworkXMLRepository.class);
        gradeXMLRepository = Mockito.mock(GradeXMLRepository.class);

        homeworkArgumentCaptor = ArgumentCaptor.forClass(Homework.class);

        this.service = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);
    }

    @Test
    void updateHomeworkResultCode() {
        Homework dummyHomework = new Homework("1", "default", 1, 2);
        Mockito.when(homeworkXMLRepository.update(dummyHomework)).thenReturn(dummyHomework);
        assertEquals(1, this.service.updateHomework("1", "default", 1, 2));
    }

    @Test
    void updateHomeworkEntityValue() {
        Homework dummyHomework = new Homework("1", "default", 1, 2);
        Mockito.when(homeworkXMLRepository.update(dummyHomework)).thenReturn(dummyHomework);
        this.service.updateHomework("1", "default", 1, 2);
        Mockito.verify(homeworkXMLRepository).update(homeworkArgumentCaptor.capture());
        assertEquals(new Homework("1", "default", 1, 2), homeworkArgumentCaptor.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1", "default"})
    void deleteHomeworks(String id) {
        Homework dummyHomework = new Homework("1", "default", 1, 2);
        Mockito.when(homeworkXMLRepository.delete(Mockito.anyString())).thenReturn(dummyHomework);
        assertNotEquals(0, this.service.deleteHomework(id));
    }
}
