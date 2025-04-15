package hkmu.wadd.courseportal002.repository;

import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findByCourseAndActiveTrueAndExpiresAtAfter(Course course, LocalDateTime now);
    List<Poll> findByCourseAndActiveTrue(Course course);
    List<Poll> findByCreatorId(Long creatorId);
} 