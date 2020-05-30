package com.nyrhog.telegramShopping.repository;

import com.nyrhog.telegramShopping.entity.Category;
import com.nyrhog.telegramShopping.entity.Clothes;
import com.nyrhog.telegramShopping.entity.Color;
import com.nyrhog.telegramShopping.entity.Size;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class ClothesFetchRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Clothes getOneClothesById(Long id){

        //Get clothes with category
        Clothes clothes = entityManager
                .createQuery(
                        "select distinct clothes " +
                                "from Clothes clothes " +
                                "left join fetch clothes.categories " +
                                "where clothes.id = :id", Clothes.class)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .setParameter("id", id)
                .getResultList().stream().findFirst().orElse(null);

        //Getting clothes with Color
        clothes = entityManager
                .createQuery(
                        "select distinct clothes " +
                                "from Clothes clothes " +
                                "left join fetch clothes.colors color " +
                                "where clothes = :clothes", Clothes.class)
                .setParameter("clothes", clothes)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList().stream().findFirst().orElse(null);

        //Getting clothes with Size
        clothes = entityManager
                .createQuery(
                        "select distinct clothes " +
                                "from Clothes clothes " +
                                "left join fetch clothes.sizes size " +
                                "where clothes = :clothes", Clothes.class)
                .setParameter("clothes", clothes)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList().stream().findFirst().orElse(null);

        return clothes;
    }


    @Transactional(readOnly = true)
    public List<Clothes> getAllClothes(){

        //Get clothes with category
        List<Clothes> clothes = entityManager
                .createQuery(
                        "select distinct clothes " +
                                "from Clothes clothes " +
                                "left join fetch clothes.categories", Clothes.class)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        //Getting clothes with Color
        clothes = entityManager
                .createQuery(
                        "select distinct clothes " +
                                "from Clothes clothes " +
                                "left join fetch clothes.colors color " +
                                "where clothes in :clothes", Clothes.class)
                .setParameter("clothes", clothes)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        //Getting clothes with Size
        clothes = entityManager
                .createQuery(
                        "select distinct clothes " +
                                "from Clothes clothes " +
                                "left join fetch clothes.sizes size " +
                                "where clothes in :clothes", Clothes.class)
                .setParameter("clothes", clothes)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        return clothes;
    }




    //TODO: доделать данный метод. Присутствуют ошибки

    @Transactional
    public List<Clothes> getClothesByFilter(List<String> categories, List<String> sizes, List<String> colors){

        //Get clothes with category
        List<Clothes> clothesCategory = new ArrayList<>();
        if(categories == null || categories.size() > 0){
            clothesCategory = entityManager
                    .createQuery(
                            "select distinct clothes " +
                                    "from Clothes clothes " +
                                    "left join fetch clothes.categories categories " +
                                    "where categories.name in :category", Clothes.class)
                    .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                    .setParameter("category", categories)
                    .getResultList();
        }

        //Getting clothes with Color
        List<Clothes> clothesColor = new ArrayList<>();
        if(colors == null || colors.size() > 0){
            clothesColor = entityManager
                    .createQuery(
                            "select distinct clothes " +
                                    "from Clothes clothes " +
                                    "left join fetch clothes.colors colors " +
                                    "where colors.name in :color", Clothes.class)
                    .setParameter("color", colors)
                    .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                    .getResultList();
        }

        //Getting clothes with Size
        List<Clothes> clothesSizes = new ArrayList<>();
        if(sizes == null || sizes.size() > 0){
            clothesSizes = entityManager
                    .createQuery(
                            "select distinct clothes " +
                                    "from Clothes clothes " +
                                    "left join fetch clothes.sizes sizes " +
                                    "where sizes.name in :sizes", Clothes.class)
                    .setParameter("sizes", sizes)
                    .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                    .getResultList();
        }

        // 1 - объединить  все arrays
        List<Clothes> clothesList = new ArrayList<>();
        clothesList.addAll(clothesCategory);
        clothesList.addAll(clothesColor);
        clothesList.addAll(clothesSizes);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(clothesList);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        clothesList = new ArrayList<>(new HashSet<>(clothesList));

        return clothesList.stream().map(clothes -> {
            boolean catBool = false;
            boolean colBool = false;
            boolean sizeBool = false;

            System.out.println(clothes);
            System.out.println();
            System.out.println();
            if (categories != null) {
                if (categories.size() > 0)
                    catBool = clothes.getCategories().stream().anyMatch(category -> categories.contains(category.getName()));
            } else {
                catBool = true;
            }

            if (colors != null){
                if (colors.size() > 0)
                    colBool = clothes.getColors().stream().anyMatch(color -> colors.contains(color.getName()));
            } else {
                colBool = true;
            }

            if (sizes != null){
                if (sizes.size() > 0)
                    sizeBool = clothes.getSizes().stream().anyMatch(size -> sizes.contains(size.getName()));
            } else {
                sizeBool = true;
            }

            System.out.println("cat:" + catBool);
            System.out.println("col:" + colBool);
            System.out.println("size:" + sizeBool);

            if(catBool && colBool && sizeBool){
                return clothes;
            }
            return null;
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    }

}