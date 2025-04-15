package hkmu.wadd.courseportal002.service;

import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.User;
import hkmu.wadd.courseportal002.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public List<Course> findByTeacher(User teacher) {
        return courseRepository.findByTeacher(teacher);
    }

    public List<Course> findByActiveTrue() {
        return courseRepository.findByActiveTrue();
    }

    public List<Course> findByTitleContainingIgnoreCase(String keyword) {
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void delete(Course course) {
        courseRepository.delete(course);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
} 