package hkmu.wadd.courseportal002.service;

import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.Poll;
import hkmu.wadd.courseportal002.model.User;
import hkmu.wadd.courseportal002.model.Vote;
import hkmu.wadd.courseportal002.repository.PollRepository;
import hkmu.wadd.courseportal002.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    public List<Poll> findActivePolls(LocalDateTime now) {
        return pollRepository.findAll().stream()
                .filter(poll -> poll.isActive() && poll.getExpiresAt().isAfter(now))
                .collect(Collectors.toList());
    }

    public List<Poll> findActivePollsByCourse(Course course, LocalDateTime now) {
        return pollRepository.findByCourseAndActiveTrueAndExpiresAtAfter(course, now);
    }

    public Optional<Poll> findById(Long id) {
        return pollRepository.findById(id);
    }

    public Poll save(Poll poll) {
        return pollRepository.save(poll);
    }

    public void delete(Poll poll) {
        pollRepository.delete(poll);
    }

    public void deleteById(Long id) {
        pollRepository.deleteById(id);
    }

    public boolean hasUserVoted(Poll poll, User user) {
        return voteRepository.existsByPollAndUser(poll, user);
    }

    public Vote vote(Poll poll, User user, String selectedOption) {
        // Check if user has already voted
        Optional<Vote> existingVote = voteRepository.findByPollAndUser(poll, user);
        if (existingVote.isPresent()) {
            return existingVote.get();
        }

        // Create and save new vote
        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setUser(user);
        vote.setSelectedOption(selectedOption);
        vote.setVotedAt(LocalDateTime.now());
        return voteRepository.save(vote);
    }

    public Map<String, Long> getPollResults(Poll poll) {
        List<Vote> votes = voteRepository.findByPoll(poll);
        return votes.stream()
                .collect(Collectors.groupingBy(Vote::getSelectedOption, Collectors.counting()));
    }
} 