package hkmu.wadd.courseportal002.config;

import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.Poll;
import hkmu.wadd.courseportal002.model.User;
import hkmu.wadd.courseportal002.service.CourseService;
import hkmu.wadd.courseportal002.service.PollService;
import hkmu.wadd.courseportal002.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private PollService pollService;

    @Override
    public void run(String... args) throws Exception {
        // Create users
        User teacher = new User();
        teacher.setUsername("teacher");
        teacher.setEmail("teacher@example.com");
        teacher.setPassword("password");
        teacher.setRole(User.Role.TEACHER);
        teacher.setEnabled(true);
        userService.save(teacher);

        User student = new User();
        student.setUsername("student");
        student.setEmail("student@example.com");
        student.setPassword("password");
        student.setRole(User.Role.STUDENT);
        student.setEnabled(true);
        userService.save(student);

        // Create courses
        Course javaCourse = new Course();
        javaCourse.setTitle("Java Programming");
        javaCourse.setDescription("Learn Java programming from scratch. This course covers basic syntax, OOP concepts, and advanced Java features.");
        javaCourse.setTeacher(teacher);
        javaCourse.setCreatedAt(LocalDateTime.now());
        javaCourse.setActive(true);
        courseService.save(javaCourse);

        Course webCourse = new Course();
        webCourse.setTitle("Web Development");
        webCourse.setDescription("Introduction to web development with HTML, CSS, and JavaScript.");
        webCourse.setTeacher(teacher);
        webCourse.setCreatedAt(LocalDateTime.now());
        webCourse.setActive(true);
        courseService.save(webCourse);

        // Create polls
        Poll javaPoll = new Poll();
        javaPoll.setQuestion("What Java topic would you like to cover next?");
        javaPoll.setCreatedAt(LocalDateTime.now());
        javaPoll.setExpiresAt(LocalDateTime.now().plusDays(7));
        javaPoll.setActive(true);
        javaPoll.setCourse(javaCourse);
        javaPoll.setCreator(teacher);
        
        Set<String> javaOptions = new HashSet<>();
        javaOptions.add("Multithreading");
        javaOptions.add("Collections Framework");
        javaOptions.add("Stream API");
        javaOptions.add("Design Patterns");
        javaPoll.setOptions(javaOptions);
        
        pollService.save(javaPoll);

        Poll webPoll = new Poll();
        webPoll.setQuestion("Which frontend framework should we learn?");
        webPoll.setCreatedAt(LocalDateTime.now());
        webPoll.setExpiresAt(LocalDateTime.now().plusDays(7));
        webPoll.setActive(true);
        webPoll.setCourse(webCourse);
        webPoll.setCreator(teacher);
        
        Set<String> webOptions = new HashSet<>();
        webOptions.add("React");
        webOptions.add("Angular");
        webOptions.add("Vue.js");
        webOptions.add("Svelte");
        webPoll.setOptions(webOptions);
        
        pollService.save(webPoll);
    }
} 