package miu.edu.product.service;

import miu.edu.product.domain.Content;

import java.util.List;

public interface ContentService {
    public List<Content> getAllContents();
    public Content save(Content content);
    public Content find(String slug);
    //Content editContentBySlug(String slug);
    public void delete(String slug);
}
