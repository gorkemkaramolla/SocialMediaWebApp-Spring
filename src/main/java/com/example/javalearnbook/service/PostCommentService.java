package com.example.javalearnbook.service;

import com.example.javalearnbook.dto.requests.CommentsRequests;
import com.example.javalearnbook.model.Post;
import com.example.javalearnbook.model.PostComment;
import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.repository.CommentRepository;

import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;
@Service

public class PostCommentService {
    public PostCommentService(CommentRepository commentRepository, WriterService writerService,
                              PostService postService) {
        this.commentRepository = commentRepository;
        this.writerService = writerService;
        this.postService = postService;
    }

    private final CommentRepository commentRepository;
    private final WriterService writerService;
    private final PostService postService;
    public List<PostComment> getComments(Optional<Long> writerId, Optional<Long> postId) {
        //post first writer second
        if(postId.isPresent() && writerId.isPresent())
        {
            return commentRepository.findByWriterIdAndPostId(writerId.get(),postId.get());
        }
        else if(postId.isPresent())
        {
            return postId.map(commentRepository::findByPostId).orElse(null).stream().toList();

        }
        else if(writerId.isPresent()){
            return writerId.map(commentRepository::findByWriterId).orElse(null).stream().toList();

        }
        else {
            return commentRepository.findAll();
        }


    }

    public PostComment createComment(CommentsRequests requestDto) {
        Post post = postService.getPostById(requestDto.getPostId());
        Writer writer = writerService.getWriterById(requestDto.getWriterId());
        if(post != null && writer != null)
        {

            PostComment commentToSave = new PostComment();
            commentToSave.setId(requestDto.getId());
            commentToSave.setComment(requestDto.getComment());
            commentToSave.setPost(post);
            commentToSave.setWriter(writer);
            return commentRepository.save(commentToSave);
        }
        else
            return null;
    }

    public PostComment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }



    public PostComment changeCommentById(Long commentId, CommentsRequests commentsRequests) {
        PostComment postComment = commentRepository.findById(commentId).orElse(null);
        if(postComment!=null)
        {
            postComment.setComment(commentsRequests.getComment());
            return commentRepository.save(postComment);
        }
        return null;
    }

    public String deleteComment(Long commentId) {
        Optional<PostComment> comment= commentRepository.findById(commentId);
        if(comment.isPresent())
        {
            commentRepository.deleteById(commentId);
            return "Deleted";
        }
        return "Couldn't delete";
    }
}
