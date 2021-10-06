package InTechs.InTechs.comment.controller;

import InTechs.InTechs.comment.payload.CommentInsertRequest;
import InTechs.InTechs.comment.service.CommentService;
import InTechs.InTechs.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/project/issue/{issueId}")
@RestController
@RequiredArgsConstructor
public class IssueCommentController {
    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/comment")
    public void issueComment(@RequestHeader("Authorization") String token, @PathVariable String issueId, @RequestBody CommentInsertRequest request){
        commentService.commentInsert(jwtTokenProvider.getEmail(token), issueId, request.getContent());
    }
}
