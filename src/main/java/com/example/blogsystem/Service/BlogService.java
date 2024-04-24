package com.example.blogsystem.Service;

import com.example.blogsystem.Api.ApiException;
import com.example.blogsystem.Model.Blog;
import com.example.blogsystem.Model.User;
import com.example.blogsystem.Repository.BlogRepository;
import com.example.blogsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public void addBlog(Integer userId, Blog blog){
        User user = userRepository.findUserById(userId);
        if (user == null){
            throw new ApiException("user not found!");
        }
        user.getBlogs().add(blog);
        blog.setUser(user);
        userRepository.save(user);
    }

    public void updateBlog(Integer userId, Integer blogId, Blog newBlog){
        User user = userRepository.findUserById(userId);
        Blog blog = blogRepository.findBlogById(blogId);
        if (user == null){
            throw new ApiException("user not found!");
        } else if (blog == null){
            throw new ApiException("blog not found!");
        }
        user.getBlogs().add(newBlog);
        blog.setUser(user);
        userRepository.save(user);
        blogRepository.save(newBlog);
    }


    //get blog by id
    public Blog getBlogById(Integer blogId){
        Blog blog = blogRepository.findBlogById(blogId);
        if (blog == null){
            throw new ApiException("blog not found!");
        }
        return blog;
    }

    public void deleteBlog(Integer userId, Integer blogId){
        User user = userRepository.findUserById(userId);
        Blog blog = blogRepository.findBlogById(blogId);
        if (user == null){
            throw new ApiException("user not found!");
        } else if (blog == null) {
            throw new ApiException("blog not found!");
        }
        blogRepository.delete(blog);
    }


    //method to ge all user blogs
    public List<Blog> getAllBlogs(String username){
        List<Blog> blog = blogRepository.findBlogsByUserUsername(username);
        if (blog.isEmpty()){
            throw new ApiException("this user have no blogs!");
        }
        return blog;
    }

    //method to get blog by title!
    public Blog getBlogByTitle(String title){
        Blog blog = blogRepository.findBlogByTitle(title);
        if (blog == null){
            throw new ApiException("blog not found!");
        }
        return blog;
    }




}
