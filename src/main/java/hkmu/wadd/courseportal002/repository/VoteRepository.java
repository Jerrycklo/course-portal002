package hkmu.wadd.courseportal002.repository;

import hkmu.wadd.courseportal002.model.Poll;
import hkmu.wadd.courseportal002.model.User;
import hkmu.wadd.courseportal002.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByPoll(Poll poll);
    List<Vote> findByUser(User user);
    Optional<Vote> findByPollAndUser(Poll poll, User user);
    boolean existsByPollAndUser(Poll poll, User user);
} 