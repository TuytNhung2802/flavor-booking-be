package com.flavorbooking.services.impl;

import com.flavorbooking.models.RestaurantTable;
import com.flavorbooking.repositories.Restaurant.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;

    public void addTable(RestaurantTable restaurantTable){
        tableRepository.save(restaurantTable);
    }
    public List<RestaurantTable> getAllTableOfRestaurant(Integer rid){
        return tableRepository.getAllTableOfRes(rid);
    }

    public RestaurantTable findTableById(Integer tid){
        return tableRepository.findById(tid).orElse(null);
    }

    public void updateTable(RestaurantTable restaurantTable){
        tableRepository.updateTable(restaurantTable.getTitle(), restaurantTable.getNumberSeat(), restaurantTable.getId());
    }

    public void deleteTable(Integer id){
        tableRepository.deleteById(id);
    }
}
