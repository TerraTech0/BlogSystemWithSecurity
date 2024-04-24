package com.example.blogsystem.Controller;

import com.example.blogsystem.Api.ApiResponse;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Service.BlogService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {

    Logger logger = LoggerFactory.getLogger(BlogController.class);
    private final BlogService blogService;


    //user
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addBlog(@AuthenticationPrincipal User uesr, @RequestBody Blog blog){
        logger.info("inside add blog");
        blogService.addBlog(uesr.getId(), blog);
        return ResponseEntity.ok().body(new ApiResponse("blog added successfully!"));
    }


    //user
    @PutMapping("/updade/{blogId}")//done
    public ResponseEntity<ApiResponse> updateBlog(@AuthenticationPrincipal User uesr,@PathVariable Integer blogId,  @RequestBody Blog blog){
        logger.info("inside update blog");
        blogService.updateBlog(uesr.getId(), blogId, blog);
        return ResponseEntity.ok().body(new ApiResponse("blog added successfully!"));
    }


    //user
    @DeleteMapping("/delete/{blogId}")//done
    public ResponseEntity<ApiResponse> deleteBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId){
        logger.info("inside delete blog");
        blogService.deleteBlog(user.getId(), blogId);
        return ResponseEntity.ok().body(new ApiResponse("blog deleted successfully!"));
    }

    //admin
    @GetMapping("/get-blog-by-id/{blogId}")//done
    public ResponseEntity<Blog> getBlogById(@PathVariable Integer blogId){
        logger.info("inside get blog by id");
        return ResponseEntity.ok().body(blogService.getBlogById(blogId));
    }


    //admin
    @GetMapping("/get-blog-by-title/{title}")//done
    public ResponseEntity<Blog> getBlogByTitle(@PathVariable String title){
        logger.info("inside get blog by title");
        return ResponseEntity.ok().body(blogService.getBlogByTitle(title));
    }


    //admin
    @GetMapping("/get-all-blogs-by-username/{username}")//done
    public ResponseEntity<List<Blog>> getAllBlogs(@PathVariable String username){
        logger.info("inside get all blogs");
        return ResponseEntity.ok().body(blogService.getAllBlogs(username));
    }



}
