package com.smartserve.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping
    public FoodDto save(@RequestBody FoodDto foodDto) {

        return foodService.save(foodDto);
    }

    @GetMapping
    public List<FoodDto> getAll() {

        return foodService.getAll();
    }

    @GetMapping("/{id}")
    public FoodDto getById(@PathVariable Long id) throws Exception {

        return foodService.getById(id);
    }

    @PutMapping("/{id}")
    public FoodDto update(
            @RequestBody FoodDto foodDto,
            @PathVariable Long id) throws Exception {

        return foodService.update(foodDto, id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) throws Exception {

        foodService.delete(id);

        return "Food deleted successfully";
    }
}