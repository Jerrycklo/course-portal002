package hkmu.wadd.courseportal002.controller;

import hkmu.wadd.courseportal002.model.Comment;
import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.Lecture;
import hkmu.wadd.courseportal002.model.Poll;
import hkmu.wadd.courseportal002.model.User;
import hkmu.wadd.courseportal002.service.CommentService;
import hkmu.wadd.courseportal002.service.CourseService;
import hkmu.wadd.courseportal002.service.LectureService;
import hkmu.wadd.courseportal002.service.PollService;
import hkmu.wadd.courseportal002.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PollService pollService;

    @GetMapping
    public String listCourses(Model model) {
        List<Course> courses = courseService.findByActiveTrue();
        model.addAttribute("courses", courses);
        return "courses/list";
    }

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable("id") Long id, Model model) {
        Course course = courseService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        
        List<Lecture> lectures = lectureService.findByCourse(course);
        List<Comment> comments = commentService.findByCourse(course);
        List<Poll> activePolls = pollService.findActivePollsByCourse(course, LocalDateTime.now());
        
        model.addAttribute("course", course);
        model.addAttribute("lectures", lectures);
        model.addAttribute("comments", comments);
        model.addAttribute("polls", activePolls);
        model.addAttribute("newComment", new Comment());
        
        return "courses/view";
    }

    @PostMapping("/{courseId}/comments")
    public String addComment(@PathVariable("courseId") Long courseId,
                           @RequestParam("content") String content,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        
        Course course = courseService.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + courseId));
        
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        commentService.addComment(content, user, course);
        
        redirectAttributes.addFlashAttribute("message", "Comment added successfully");
        return "redirect:/courses/" + courseId;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "courses/create";
    }

    @PostMapping("/create")
    public String createCourse(@Valid @ModelAttribute("course") Course course,
                             BindingResult result,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "courses/create";
        }
        
        User teacher = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        course.setTeacher(teacher);
        course.setCreatedAt(LocalDateTime.now());
        course.setActive(true);
        
        courseService.save(course);
        
        redirectAttributes.addFlashAttribute("message", "Course created successfully");
        return "redirect:/courses";
    }

    @GetMapping("/{id}/upload")
    public String showUploadForm(@PathVariable("id") Long id, Model model) {
        Course course = courseService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        
        model.addAttribute("course", course);
        return "courses/upload";
    }

    @PostMapping("/{id}/upload")
    public String uploadLecture(@PathVariable("id") Long id,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("title") String title,
                              @RequestParam("description") String description,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        
        try {
            Course course = courseService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
            
            User uploader = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            
            lectureService.uploadLecture(file, title, description, course, uploader);
            
            redirectAttributes.addFlashAttribute("message", "Lecture uploaded successfully");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upload lecture: " + e.getMessage());
        }
        
        return "redirect:/courses/" + id;
    }
} 