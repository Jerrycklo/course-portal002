package hkmu.wadd.courseportal002.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "lectures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String filePath;
    
    @Column(nullable = false)
    private String fileType;
    
    @Column(nullable = false)
    private String originalFileName;
    
    @Column(nullable = false)
    private long fileSize;
    
    @Column(nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @ManyToOne
    @JoinColumn(name = "uploader_id", nullable = false)
    private User uploader;
} 