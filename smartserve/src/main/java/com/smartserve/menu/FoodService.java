package com.smartserve.menu;

import java.util.List;

public interface FoodService {

    FoodDto save(FoodDto foodDto);

    List<FoodDto> getAll();

    FoodDto getById(Long id) throws Exception;

    FoodDto update(FoodDto foodDto, Long id) throws Exception;

    void delete(Long id) throws Exception;
}