package InTechs.InTechs.comment.service;

import InTechs.InTechs.comment.entity.Comment;
import InTechs.InTechs.comment.repository.CommentRepository;
import InTechs.InTechs.exception.exceptions.IssueNotFoundException;
import InTechs.InTechs.exception.exceptions.UserNotFoundException;
import InTechs.InTechs.issue.entity.Issue;
import InTechs.InTechs.issue.repository.IssueRepository;
import InTechs.InTechs.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public void commentInsert(String email, String issueId, String content) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(IssueNotFoundException::new);
        Comment comment = commentRepository.save(
                Comment.builder()
                        .content(content)
                        .createAt(LocalDateTime.now())
                        .issue(issue)
                        .user(userRepository.findById(email).orElseThrow(UserNotFoundException::new))
                        .build()
        );
        issue.addComment(comment);
        issueRepository.save(issue);
    }
}
