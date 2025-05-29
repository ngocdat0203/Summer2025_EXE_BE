package com.example.lovenhavestopsystem.service.imple;

import com.example.lovenhavestopsystem.core.base.BaseMessage;
import com.example.lovenhavestopsystem.core.exception.BadRequestException;
import com.example.lovenhavestopsystem.core.exception.NotFoundException;
import com.example.lovenhavestopsystem.dto.request.BlogRequestDTO;
import com.example.lovenhavestopsystem.dto.response.BlogResponseDTO;
import com.example.lovenhavestopsystem.mapper.BlogMapper;
import com.example.lovenhavestopsystem.model.entity.Blog;
import com.example.lovenhavestopsystem.repository.IBlogRepository;
import com.example.lovenhavestopsystem.service.inter.IBlogService;
import com.example.lovenhavestopsystem.user.crud.entity.Account;
import com.example.lovenhavestopsystem.user.crud.reposotory.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogService implements IBlogService {
    private final IBlogRepository blogRepo;
    private final IAccountRepository accountRepo;
    //private final FirebaseStorageService firebase;
    private final BlogMapper blogMapper;

    /*@Autowired
    public BlogService(IBlogRepository blogRepo, IAccountRepository accountRepo, FirebaseStorageService firebase, BlogMapper blogMapper) {
        this.blogRepo = blogRepo;
        this.accountRepo = accountRepo;
        this.firebase = firebase;
        this.blogMapper = blogMapper;
    }*/

    @Autowired
    public BlogService(IBlogRepository blogRepo, IAccountRepository accountRepo, BlogMapper blogMapper) {
        this.blogRepo = blogRepo;
        this.accountRepo = accountRepo;
        this.blogMapper = blogMapper;
    }

    @Override
    public void createBlog(BlogRequestDTO blogRequestDTO, MultipartFile image) throws IOException {
        Account account = accountRepo.findById(blogRequestDTO.getAccountId())
                .orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND));

        Blog blog = blogMapper.toEntity(blogRequestDTO);
        blog.setAccount(account);

        /*if (image != null) {
            validateImage(image);
            String imageUrl = firebase.uploadFile(image);
            blog.setThumbnailUrl(imageUrl);
        }*/

        blogRepo.save(blog);
    }

    @Override
    public void updateBlog(int blogId, BlogRequestDTO blogRequestDTO, MultipartFile image) throws IOException {
        Blog blog = blogRepo.findById(blogId)
                .orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND));

        blog.setTitle(blogRequestDTO.getTitle());
        blog.setHeadline(blogRequestDTO.getHeadline());
        blog.setSummary(blogRequestDTO.getSummary());
        blog.setContent(blogRequestDTO.getContent());

        /*if (image != null) {
            validateImage(image);
            String imageUrl = firebase.uploadFile(image);
            blog.setThumbnailUrl(imageUrl);
        }*/

        blogRepo.save(blog);
    }

    private void validateImage(MultipartFile image) {
        if (image.getSize() > 1024 * 1024 * 10) {
            throw new BadRequestException(BaseMessage.FILE_SIZE_TOO_LARGE);
        }
    }

    @Override
    public void deleteBlog(int blogId) {
        Blog blog = blogRepo.findById(blogId).orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND));
        blog.setDeletedTime(LocalDateTime.now());
        blogRepo.save(blog);
    }

    @Override
    public BlogResponseDTO getBlogById(int blogId) {
        Blog blog = blogRepo.findById(blogId).orElseThrow(() -> new NotFoundException(BaseMessage.NOT_FOUND));
        return blogMapper.toResponseDTO(blog);
    }

    @Override
    public List<BlogResponseDTO> getAll() {
        List<Blog> blogs = blogRepo.findByDeletedTimeNull();
        return blogMapper.toListResponseDTO(blogs);
    }

    @Override
    public List<BlogResponseDTO> getBlogByAccountId(int accountId) {
        List<Blog> blogs = blogRepo.findByAccountIdAndDeletedTimeNull(accountId);
        return blogMapper.toListResponseDTO(blogs);
    }
}
