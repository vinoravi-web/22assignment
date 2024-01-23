package com.stackroute.test.service;

import com.stackroute.domain.Blog;
import com.stackroute.exception.BlogAlreadyExistsException;
import com.stackroute.exception.BlogNotFoundException;
import com.stackroute.repository.BlogRepository;
import com.stackroute.service.BlogServiceImpl;

import jakarta.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {
    // Mock BlogRepository
	
	@Mock
	private BlogRepository blogRepository;

    // Inject Mocks to BlogServiceImpl
	
	@InjectMocks
	private BlogServiceImpl blogServiceImpl;

    private Blog blog, blog1;
    private List<Blog> blogList=new ArrayList<>();
    private Optional optional;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        blog = new Blog(1, "SampleBlog", "Imneet", "SampleBlogforTesting");
        blog1 = new Blog(2, "Blog 1", "John", "Sample Blog 1 for Testing");
        optional = Optional.of(blog);;
    }

    @AfterEach
    public void tearDown() {
        blog = null;
    }

    /*
    * write test case for saveBlog() method, by mocking the repository
    * */
    @Test
    public void givenBlogToSaveThenShouldReturnSavedBlog() {
    	when(blogRepository.save(blog)).thenReturn(blog);
    	Blog blog2=blogServiceImpl.saveBlog(blog);
    	assertNotNull(blog2);
    	assertEquals("SampleBlog",blog2.getBlogTitle());
    	
    }

    /*
    * write test case for asserting RuntimeException when saveBlog() method is called, by mocking the repository
     */
    @Test
    public void givenBlogToSaveThenShouldNotReturnSavedBlog() {
    	when(blogRepository.existsById(blog.getBlogId())).thenReturn(true);
    	assertThrows(BlogAlreadyExistsException.class,
				() -> blogServiceImpl.saveBlog(blog));
    }

    /*
    * write test case for getAllBlogs() method, by mocking the repository
     */
    @Test
    public void givenGetAllBlogsThenShouldReturnListOfAllBlogs() {
    	blogList.add(blog);
    	when(blogRepository.findAll()).thenReturn(blogList);
    	List<Blog> blog2=blogServiceImpl.getAllBlogs();
    	assertNotNull(blog2);
    	assertEquals(1,blog2.size());
    }
    

    /*
    * write test case for getBlogById() method, by mocking the repository
     */
    @Test
    public void givenBlogIdThenShouldReturnRespectiveBlog() {
    	when(blogRepository.findById(1)).thenReturn(optional.of(blog));
    	Blog blog2=blogServiceImpl.getBlogById(1);
    	assertNotNull(blog2);
    	assertEquals("Imneet",blog2.getAuthorName());

    }

    /*
    * write test case for deleteBlog() method, by mocking the repository
     */
    @Test
    void givenBlogIdToDeleteThenShouldReturnDeletedBlog() {
    	when(blogRepository.findById(1)).thenReturn(optional.of(blog));
    	Blog blog2=blogServiceImpl.deleteBlog(1);
    	assertEquals("Imneet",blog2.getAuthorName());
    }

    /*
    * write test case for asserting BlogNotFoundException when deleteBlog() method is called, by mocking the repository
     */
    @Test
    void givenBlogIdToDeleteThenShouldNotReturnDeletedBlog() {
    	when(blogRepository.findById(1)).thenReturn(optional.ofNullable(null));
    	assertThrows(BlogNotFoundException.class,
				() ->blogServiceImpl.deleteBlog(1));
    }

    /*
    * write test case for updateBlog() method, by mocking the repository
     */
    @Test
    public void givenBlogToUpdateThenShouldReturnUpdatedBlog() {
    	when(blogRepository.findById(1)).thenReturn(optional.of(blog));
    	when(blogRepository.save(blog)).thenReturn((blog));
    	Blog blog2=blogServiceImpl.updateBlog(blog);
    	assertNotNull(blog2);
    	assertEquals("Imneet",blog2.getAuthorName());
    	
    }

    /*
    * write test case for asserting BlogNotFoundException when updateBlog() method is called, by mocking the repository
     */
    @Test
    public void givenBlogToUpdateThenShouldNotReturnUpdatedBlog() {
    	when(blogRepository.findById(1)).thenReturn(optional.ofNullable(null));
    	assertThrows(BlogNotFoundException.class,
				() ->blogServiceImpl.updateBlog(blog));
    }


    /*
    * write test case for asserting BlogNotFoundException when getBlogById() method is called, by mocking the repository
     */
    @Test
    public void givenNonExistentBlogIdThenShouldThrowBlogNotFoundException() {
    	when(blogRepository.findById(1)).thenReturn(optional.ofNullable(null));
    	assertThrows(BlogNotFoundException.class,
				() ->blogServiceImpl.getBlogById(1));
    }

    /*
    * write test case for asserting BlogNotFoundException when updateBlog() method is called, by mocking the repository
     */
    @Test
    public void givenUpdateNonExistentBlogThenShouldThrowBlogNotFoundException() {
    	when(blogRepository.findById(1)).thenReturn(optional.ofNullable(null));
    	assertThrows(BlogNotFoundException.class,
				() ->blogServiceImpl.updateBlog(blog));
    }
}