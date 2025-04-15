package hkmu.wadd.courseportal002.repository;

import hkmu.wadd.courseportal002.model.Comment;
import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCourseAndDeletedFalseOrderByCreatedAtDesc(Course course);
    List<Comment> findByUserAndDeletedFalseOrderByCreatedAtDesc(User user);
    List<Comment> findByContentContainingIgnoreCaseAndDeletedFalse(String keyword);
} 