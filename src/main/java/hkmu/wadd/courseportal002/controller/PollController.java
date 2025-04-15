package hkmu.wadd.courseportal002.controller;

import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.Poll;
import hkmu.wadd.courseportal002.model.User;
import hkmu.wadd.courseportal002.service.CourseService;
import hkmu.wadd.courseportal002.service.PollService;
import hkmu.wadd.courseportal002.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listPolls(Model model) {
        List<Poll> activePolls = pollService.findActivePolls(LocalDateTime.now());
        model.addAttribute("polls", activePolls);
        return "polls/list";
    }

    @GetMapping("/{id}")
    public String viewPoll(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Poll poll = pollService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid poll Id:" + id));
        
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        boolean hasVoted = pollService.hasUserVoted(poll, user);
        Map<String, Long> results = pollService.getPollResults(poll);
        
        model.addAttribute("poll", poll);
        model.addAttribute("hasVoted", hasVoted);
        model.addAttribute("results", results);
        
        return "polls/view";
    }

    @PostMapping("/{id}/vote")
    public String vote(@PathVariable("id") Long id,
                     @RequestParam("option") String option,
                     Authentication authentication,
                     RedirectAttributes redirectAttributes) {
        
        Poll poll = pollService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid poll Id:" + id));
        
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        if (pollService.hasUserVoted(poll, user)) {
            redirectAttributes.addFlashAttribute("error", "You have already voted in this poll");
        } else {
            pollService.vote(poll, user, option);
            redirectAttributes.addFlashAttribute("message", "Your vote has been recorded");
        }
        
        return "redirect:/polls/" + id;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("poll", new Poll());
        model.addAttribute("courses", courseService.findAll());
        return "polls/create";
    }

    @PostMapping("/create")
    public String createPoll(@Valid @ModelAttribute("poll") Poll poll,
                           @RequestParam("courseId") Long courseId,
                           @RequestParam("options") String optionsString,
                           BindingResult result,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "polls/create";
        }
        
        Course course = courseService.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + courseId));
        
        User creator = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        // Parse options
        Set<String> options = Set.of(optionsString.split("\\n"))
                .stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
        
        if (options.size() < 2) {
            result.rejectValue("options", "error.poll", "At least 2 options are required");
            return "polls/create";
        }
        
        poll.setCourse(course);
        poll.setCreator(creator);
        poll.setCreatedAt(LocalDateTime.now());
        poll.setActive(true);
        poll.setOptions(options);
        
        pollService.save(poll);
        
        redirectAttributes.addFlashAttribute("message", "Poll created successfully");
        return "redirect:/polls";
    }
} 