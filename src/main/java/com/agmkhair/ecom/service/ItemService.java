package com.agmkhair.ecom.service;

import com.agmkhair.ecom.dto.ProductCreateRequest;
import com.agmkhair.ecom.dto.ProductDTO;
import com.agmkhair.ecom.entity.Products;
import com.agmkhair.ecom.entity.ItemImage;
import com.agmkhair.ecom.repository.ItemImageRepository;
import com.agmkhair.ecom.repository.ItemRepository;
import com.agmkhair.ecom.utils.CommonUtils;
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

    public List<ProductDTO> getAll() {

        List<Products> productsList = itemRepo.findAllByOrderByCreatedAtDesc();

        return productsList.stream()
                .map(this::toDTO)   // entity → dto
                .toList();
    }


    public List<Products> getByCategory(Long categoryId) {
        return itemRepo.findByCategoryIdOrderByPriorityAsc(categoryId);
    }

    public List<Products> getByBrand(Long brandId) {
        return itemRepo.findByBrandIdOrderByPriorityAsc(brandId);
    }

    @Transactional
    public Products createItem(
            ProductCreateRequest request,
            MultipartFile[] images
    ) {

        Products product = mapToEntity(request);

        Products saved = itemRepo.save(product);

        if (images != null && images.length > 0) {
            List<ItemImage> list = new ArrayList<>();
            for (MultipartFile f : images) {
                String filename = storage.store(f,request.getTitle());
                ItemImage img = new ItemImage();
                img.setImage(CommonUtils.IMAGE_URL+filename);
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
    public Products updateItem(
            Long id,
            ProductCreateRequest updated,
            MultipartFile[] images
    ) {
        Products product = itemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ================= BASIC FIELD UPDATE =================
        product.setTitle(updated.getTitle());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setOfferPrice(updated.getOfferPrice());
        product.setOldPrice(updated.getOldPrice());
        product.setQuantity(updated.getQuantity());
        product.setStatus(updated.getStatus());
        product.setBrandId(updated.getBrandId());
        product.setCategoryId(updated.getCategoryId());
        product.setSizes(
                updated.getSizes() != null
                        ? String.join(",", updated.getSizes())
                        : null
        );

        product.setColors(
                updated.getColors() != null
                        ? String.join(",", updated.getColors())
                        : null
        );
        product.setUpdatedAt(LocalDateTime.now());

        // ================= IMAGE DELETE (CRITICAL PART) =================
        if (updated.getExistingImages() != null) {

            List<ItemImage> dbImages = imageRepo.findByProductId(id);

            for (ItemImage img : dbImages) {
                // UI থেকে পাঠানো list এ যদি না থাকে → delete
                if (!updated.getExistingImages().contains(img.getImage())) {

                    storage.delete(img.getImage());

                    imageRepo.delete(img);
                }
            }
        }

        // ================= ADD NEW IMAGES =================

        if (images != null && images.length > 0) {
            List<ItemImage> list = new ArrayList<>();
            for (MultipartFile f : images) {
                String filename = storage.store(f,product.getTitle());
                ItemImage img = new ItemImage();
                img.setImage(CommonUtils.IMAGE_URL+filename);
                img.setProduct(product);
                img.setCreatedAt(LocalDateTime.now());
                imageRepo.save(img);
                list.add(img);
            }
        }

        return itemRepo.save(product);
    }

    @Transactional
    public boolean deleteItem(Long id) {
        return itemRepo.findById(id).map(product -> {

            // 🔹 delete physical files ONLY
            for (ItemImage img : product.getImages()) {
                storage.delete(img.getImage());
            }

            // 🔹 let Hibernate handle DB delete (cascade)
            itemRepo.delete(product);

            return true;
        }).orElse(false);
    }


    public List<ItemImage> getImages(Long itemId) {
        return imageRepo.findByProductId(itemId);
    }

    private Products mapToEntity(ProductCreateRequest req) {
        Products p = new Products();

        p.setId(req.getId()); // null হলে create, না হলে update
        p.setBrandId(req.getBrandId());
        p.setCategoryId(req.getCategoryId());

        p.setTitle(req.getTitle());
        p.setDescription(req.getDescription());

        p.setQuantity(req.getQuantity());
        p.setUnit(req.getUnit());


        p.setPrice(req.getPrice());
        p.setOldPrice(req.getOldPrice());
        p.setOfferPrice(req.getOfferPrice());

        p.setStatus(req.getStatus());
        p.setFeatured(req.getFeatured());
        p.setPriority(req.getPriority());
        p.setFreeShipment(req.getFreeShipment());

        p.setSizes(
                req.getSizes() != null
                        ? String.join(",", req.getSizes())
                        : null
        );

        p.setColors(
                req.getColors() != null
                        ? String.join(",", req.getColors())
                        : null
        );


        return p;
    }


    public ProductDTO toDTO(Products p) {

        ProductDTO dto = new ProductDTO();

        dto.setId(p.getId());
        dto.setBrandId(p.getBrandId());
        dto.setCategoryId(p.getCategoryId());

        dto.setTitle(p.getTitle());
        dto.setDescription(p.getDescription());

        dto.setQuantity(p.getQuantity());
        dto.setPrice(p.getPrice());
        dto.setOfferPrice(p.getOfferPrice());
        dto.setOldPrice(p.getOldPrice());
        dto.setCreatedAt(p.getCreatedAt().toString());
        dto.setFeatured(p.getFeatured());

        dto.setStatus(p.getStatus());

        dto.setImages(p.getImages());

        /// 🔥 String → List conversion
        dto.setSizesFromString(p.getSizes());
        dto.setColorsFromString(p.getColors());

        return dto;
    }


}
