package hkmu.wadd.courseportal002.repository;

import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacher(User teacher);
    List<Course> findByActiveTrue();
    List<Course> findByTitleContainingIgnoreCase(String keyword);
} 