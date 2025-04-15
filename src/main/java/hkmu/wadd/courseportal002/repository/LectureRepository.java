package hkmu.wadd.courseportal002.repository;

import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCourse(Course course);
    List<Lecture> findByCourseTitleContainingIgnoreCase(String keyword);
} 