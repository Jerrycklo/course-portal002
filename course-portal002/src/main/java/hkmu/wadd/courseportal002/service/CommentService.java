package hkmu.wadd.courseportal002.service;

import hkmu.wadd.courseportal002.model.Comment;
import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.User;
import hkmu.wadd.courseportal002.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public List<Comment> findByCourse(Course course) {
        return commentRepository.findByCourseAndDeletedFalseOrderByCreatedAtDesc(course);
    }

    public List<Comment> findByUser(User user) {
        return commentRepository.findByUserAndDeletedFalseOrderByCreatedAtDesc(user);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment addComment(String content, User user, Course course) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setCourse(course);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setDeleted(false);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, String content) {
        Optional<Comment> commentOpt = findById(id);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            comment.setContent(content);
            comment.setUpdatedAt(LocalDateTime.now());
            return commentRepository.save(comment);
        }
        return null;
    }

    public void softDelete(Long id) {
        Optional<Comment> commentOpt = findById(id);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            comment.setDeleted(true);
            commentRepository.save(comment);
        }
    }

    public void hardDelete(Comment comment) {
        commentRepository.delete(comment);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
} 