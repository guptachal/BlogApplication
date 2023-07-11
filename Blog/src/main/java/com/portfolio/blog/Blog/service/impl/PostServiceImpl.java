package com.portfolio.blog.Blog.service.impl;

import com.portfolio.blog.Blog.entity.Post;
import com.portfolio.blog.Blog.exception.ResourceNotFoundException;
import com.portfolio.blog.Blog.payload.PostDto;
import com.portfolio.blog.Blog.payload.PostResponse;
import com.portfolio.blog.Blog.repository.IPostRepository;
import com.portfolio.blog.Blog.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {
    private IPostRepository postRepository;

    @Autowired
    public PostServiceImpl(IPostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // Converting DTO to Entity
        Post post = mapToEntity(postDto);
        post.setCreatedAt(LocalDateTime.now()); // saving the created time.
        // Saving the Post object in the db
        Post newPost = postRepository.save(post);
        // Converting the Entity object back to the DTO
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }
    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) throws Exception {
        try {
            // Create Pageable Instance
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
            Page<Post> posts = postRepository.findAll(pageable);
            // get content from the page object and mapping those back to the postDto
            List<PostDto> postDtos = posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
            PostResponse postResponse = new PostResponse();
            postResponse.setContent(postDtos);
            postResponse.setPageNo(posts.getNumber());
            postResponse.setPageSize(posts.getSize());
            postResponse.setTotalPages(posts.getTotalPages());
            postResponse.setTotalElements(posts.getTotalElements());
            postResponse.setLast(posts.isLast());
            return postResponse;
        }
        catch (ResourceNotFoundException ex){
            throw new Exception("Resource NOt found");
        }
    }
    @Override
    public List<PostDto> getPostByTitle(String title) {
        List<Post> posts = postRepository.getPostByTitle(title).orElseThrow(()->new ResourceNotFoundException());
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }
    @Override
    public PostDto updatePost(PostDto postDto, String title) {
        // Get Post By title, if not we will throw the error
        List<Post> post = postRepository.getPostByTitle(title).orElseThrow(()-> new ResourceNotFoundException());
        post.get(0).setTitle(postDto.getTitle());
        post.get(0).setDescription(postDto.getDescription());
        post.get(0).setContent(postDto.getContent());
        post.get(0).setUpdatedAt(LocalDateTime.now());
        post.get(0).setCreatedAt(post.get(0).getCreatedAt());
        Post updatedPost = postRepository.save(post.get(0));
        return mapToDTO(updatedPost);
    }
    @Override
    public void deletePostByTitle(String title) {
        List<Post> post = postRepository.getPostByTitle(title).orElseThrow(()-> new ResourceNotFoundException());
        postRepository.delete(post.get(0));
    }
    // Convert Post to PostDto
    private PostDto mapToDTO(Post post){
        PostDto postResponse = new PostDto();
        postResponse.setId(post.getId());
        postResponse.setContent(post.getContent());
        postResponse.setTitle(post.getTitle());
        postResponse.setDescription(post.getDescription());
        postResponse.setUpdatedAt(post.getUpdatedAt());
        postResponse.setCreatedAt(post.getCreatedAt());
        return postResponse;
    }
    // Convert the PostDTO object to the entity
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setUpdatedAt(LocalDateTime.now());
        return post;
    }
}