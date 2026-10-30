package com.smartserve.menu;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FoodServiceImpl implements FoodService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public FoodDto save(FoodDto foodDto) {

        Food food = mapper.map(foodDto, Food.class);

        foodRepository.save(food);

        return mapper.map(food, FoodDto.class);
    }

    @Override
    public List<FoodDto> getAll() {

        List<Food> foods = foodRepository.findAll();

        return foods.stream()
                .map(food -> mapper.map(food, FoodDto.class))
                .toList();
    }

    @Override
    public FoodDto getById(Long id) throws Exception {

        Food food = foodRepository.findById(id)
                .orElseThrow(() ->
                        new FoodNotFoundException("Food not found"));

        return mapper.map(food, FoodDto.class);
    }

    @Override
    public FoodDto update(FoodDto foodDto, Long id) throws Exception {

        Food food = foodRepository.findById(id)
                .orElseThrow(() ->
                        new FoodNotFoundException("Food not found"));

        food.setName(foodDto.getName());
        food.setDescription(foodDto.getDescription());
        food.setPrice(foodDto.getPrice());
        food.setCategory(foodDto.getCategory());
        food.setFoodType(foodDto.getFoodType());
        food.setImageUrl(foodDto.getImageUrl());
        food.setAvailable(foodDto.getAvailable());

        foodRepository.save(food);

        return mapper.map(food, FoodDto.class);
    }

    @Override
    public void delete(Long id) throws Exception {

        if (!foodRepository.findById(id).isPresent()) {
            throw new FoodNotFoundException("Food not found");
        }

        foodRepository.deleteById(id);
    }
}