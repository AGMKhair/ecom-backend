package com.agmkhair.ecom.service;

import com.agmkhair.ecom.entity.Item;
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
    private final FileStorageService storage;

    public Item getById(Long id) {
        return itemRepo.findById(id).orElse(null);
    }

    public List<Item> getAll() {
        return itemRepo.findAll();
    }

    public List<Item> getByCategory(Long categoryId) {
        return itemRepo.findByCategoryIdOrderByPriorityAsc(categoryId);
    }

    public List<Item> getByBrand(Long brandId) {
        return itemRepo.findByBrandIdOrderByPriorityAsc(brandId);
    }

    @Transactional
    public Item createItem(Item item, MultipartFile[] images) {
        item.setCreatedAt(LocalDateTime.now());
        Item saved = itemRepo.save(item);

        if (images != null) {
            List<ItemImage> list = new ArrayList<>();
            for (MultipartFile f : images) {
                String filename = storage.store(f);
                ItemImage img = new ItemImage();
                img.setImage(filename);
                img.setItem(saved);
                img.setCreatedAt(LocalDateTime.now());
                imageRepo.save(img);
                list.add(img);
            }
            saved.setImages(list);
        }
        return saved;
    }

    @Transactional
    public Item updateItem(Long id, Item updated, MultipartFile[] images) {
        return itemRepo.findById(id).map(item -> {
            item.setTitle(updated.getTitle());
            item.setDescription(updated.getDescription());
            item.setSlug(updated.getSlug());
            item.setQuantity(updated.getQuantity());
            item.setUnit(updated.getUnit());
            item.setPrice(updated.getPrice());
            item.setPriceSar(updated.getPriceSar());
            item.setFreeShipment(updated.getFreeShipment());
            item.setStatus(updated.getStatus());
            item.setOfferPrice(updated.getOfferPrice());
            item.setOldPrice(updated.getOldPrice());
            item.setOldPriceSar(updated.getOldPriceSar());
            item.setFeatured(updated.getFeatured());
            item.setPriority(updated.getPriority());
            item.setOfferProduct(updated.getOfferProduct());
            item.setAdmin(updated.getAdmin());
            item.setBrandId(updated.getBrandId());
            item.setCategoryId(updated.getCategoryId());
            item.setUpdatedAt(LocalDateTime.now());

            // Add new images if provided
            if (images != null) {
                for (MultipartFile f : images) {
                    String filename = storage.store(f);
                    ItemImage img = new ItemImage();
                    img.setImage(filename);
                    img.setItem(item);
                    img.setCreatedAt(LocalDateTime.now());
                    imageRepo.save(img);
                }
            }
            return itemRepo.save(item);
        }).orElse(null);
    }

    @Transactional
    public boolean deleteItem(Long id) {
        return itemRepo.findById(id).map(item -> {
            // delete images physically
            List<ItemImage> imgs = imageRepo.findByItemId(id);
            for (ItemImage im : imgs) {
                storage.delete(im.getImage());
            }
            imageRepo.deleteAll(imgs);
            itemRepo.delete(item);
            return true;
        }).orElse(false);
    }

    public List<ItemImage> getImages(Long itemId) {
        return imageRepo.findByItemId(itemId);
    }
}
