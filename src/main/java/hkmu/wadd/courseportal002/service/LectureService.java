package hkmu.wadd.courseportal002.service;

import hkmu.wadd.courseportal002.model.Course;
import hkmu.wadd.courseportal002.model.Lecture;
import hkmu.wadd.courseportal002.model.User;
import hkmu.wadd.courseportal002.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    private final Path rootLocation = Paths.get("uploads");

    public LectureService() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public List<Lecture> findAll() {
        return lectureRepository.findAll();
    }

    public List<Lecture> findByCourse(Course course) {
        return lectureRepository.findByCourse(course);
    }

    public Optional<Lecture> findById(Long id) {
        return lectureRepository.findById(id);
    }

    public Lecture save(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public Lecture uploadLecture(MultipartFile file, String title, String description, Course course, User uploader) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path targetLocation = this.rootLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation);

        Lecture lecture = new Lecture();
        lecture.setTitle(title);
        lecture.setDescription(description);
        lecture.setFilePath(fileName);
        lecture.setFileType(file.getContentType());
        lecture.setOriginalFileName(file.getOriginalFilename());
        lecture.setFileSize(file.getSize());
        lecture.setUploadedAt(LocalDateTime.now());
        lecture.setCourse(course);
        lecture.setUploader(uploader);
        
        return lectureRepository.save(lecture);
    }

    public void delete(Lecture lecture) {
        try {
            Files.deleteIfExists(this.rootLocation.resolve(lecture.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
        lectureRepository.delete(lecture);
    }

    public void deleteById(Long id) {
        Optional<Lecture> lectureOpt = findById(id);
        if (lectureOpt.isPresent()) {
            delete(lectureOpt.get());
        }
    }
} 