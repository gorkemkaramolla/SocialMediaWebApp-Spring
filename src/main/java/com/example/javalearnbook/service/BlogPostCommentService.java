package com.example.javalearnbook.service;

import com.example.javalearnbook.dto.requests.BlogPostCommentsRequests;
import com.example.javalearnbook.model.BlogPost;
import com.example.javalearnbook.model.BlogPostComment;
import com.example.javalearnbook.model.BlogWriter;
import com.example.javalearnbook.repository.BlogPostCommentRepository;
import com.example.javalearnbook.repository.BlogWriterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class BlogPostCommentService {
    private BlogPostCommentRepository commentRepository;
    private final BlogWriterService writerService;
    private final BlogPostService blogPostService;
    public List<BlogPostComment> getPostsComments(Optional<Long> postId) {
        return postId.map(aLong -> commentRepository.findByPostId(aLong)).orElse(null);

    }

    public BlogPostComment createComment( BlogPostCommentsRequests requestDto) {
        BlogPost blogPost = blogPostService.getPostById(requestDto.getPostId());
        BlogWriter blogWriter = writerService.getOneWriter(requestDto.getWriterId());
        if(blogPost != null && blogWriter != null)
        {

            BlogPostComment commentToSave = new BlogPostComment();
            commentToSave.setComment(requestDto.getComment());
            commentToSave.setPost(blogPost);
            commentToSave.setWriter(blogWriter);
            return commentRepository.save(commentToSave);


        }
        return null;
    }
}
