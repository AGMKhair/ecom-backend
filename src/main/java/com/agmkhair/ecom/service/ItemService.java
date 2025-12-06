package com.agmkhair.ecom.service;

import com.agmkhair.ecom.entity.Products;
import com.agmkhair.ecom.entity.ItemImage;
import com.agmkhair.ecom.repository.ItemImageRepository;
import com.agmkhair.ecom.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepo;
    private final ItemImageRepository imageRepo;
    private final StorageService storage;

    public Products getById(Long id) {
        return itemRepo.findById(id).orElse(null);
    }

    public List<Products> getAll() {
        return itemRepo.findAll();
    }

    public List<Products> getByCategory(Long categoryId) {
        return itemRepo.findByCategoryIdOrderByPriorityAsc(categoryId);
    }

    public List<Products> getByBrand(Long brandId) {
        return itemRepo.findByBrandIdOrderByPriorityAsc(brandId);
    }

    @Transactional
    public Products createItem(Products products, MultipartFile[] images) {
        products.setCreatedAt(LocalDateTime.now());
        Products saved = itemRepo.save(products);

        if (images != null) {
            List<ItemImage> list = new ArrayList<>();
            for (MultipartFile f : images) {
                String filename = storage.store(f);
                ItemImage img = new ItemImage();
                img.setImage(filename);
                img.setProduct(saved);
                img.setCreatedAt(LocalDateTime.now());
                imageRepo.save(img);
                list.add(img);
            }
            saved.setImages(list);
        }
        return saved;
    }

    @Transactional
    public Products updateItem(Long id, Products updated, MultipartFile[] images) {
        return itemRepo.findById(id).map(products -> {
            products.setTitle(updated.getTitle());
            products.setDescription(updated.getDescription());
            products.setSlug(updated.getSlug());
            products.setQuantity(updated.getQuantity());
            products.setUnit(updated.getUnit());
            products.setPrice(updated.getPrice());
            products.setPriceSar(updated.getPriceSar());
            products.setFreeShipment(updated.getFreeShipment());
            products.setStatus(updated.getStatus());
            products.setOfferPrice(updated.getOfferPrice());
            products.setOldPrice(updated.getOldPrice());
            products.setOldPriceSar(updated.getOldPriceSar());
            products.setFeatured(updated.getFeatured());
            products.setPriority(updated.getPriority());
            products.setOfferProduct(updated.getOfferProduct());
            products.setAdmin(updated.getAdmin());
            products.setBrandId(updated.getBrandId());
            products.setCategoryId(updated.getCategoryId());
            products.setUpdatedAt(LocalDateTime.now());

            // Add new images if provided
            if (images != null) {
                for (MultipartFile f : images) {
                    String filename = storage.store(f);
                    ItemImage img = new ItemImage();
                    img.setImage(filename);
                    img.setProduct(products);
                    img.setCreatedAt(LocalDateTime.now());
                    imageRepo.save(img);
                }
            }
            return itemRepo.save(products);
        }).orElse(null);
    }

    @Transactional
    public boolean deleteItem(Long id) {
        return itemRepo.findById(id).map(products -> {
            List<ItemImage> imgs = imageRepo.findByProductId(id);
            for (ItemImage im : imgs) {
                storage.delete(im.getImage());
            }
            imageRepo.deleteAll(imgs);
            itemRepo.delete(products);
            return true;
        }).orElse(false);
    }

    public List<ItemImage> getImages(Long itemId) {
        return imageRepo.findByProductId(itemId);
    }
}
