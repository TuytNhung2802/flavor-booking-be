package com.flavorbooking.services.restaurant;

import com.flavorbooking.dtos.restaurant.RestaurantRegisterDTO;
import com.flavorbooking.enums.RestaurantEnum;
import com.flavorbooking.models.Restaurant;
import com.flavorbooking.repositories.Restaurant.RestaurantRepository;
import com.flavorbooking.responses.auth.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Page<Restaurant> getAllRestaurants(Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer id, String title, String description){
        try {
            if (sortDir != "None") {
                // Create Sorted instance
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();
                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<Restaurant> restaurantPage = restaurantRepository.getAllRestaurants(id, title, description, pageable);
                return restaurantPage;
            } else {
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Page<Restaurant> restaurantPage = restaurantRepository.getAllRestaurants(id, title, description, pageable);
                return restaurantPage;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Page.empty();
        }
    };

    /**
     * Khi người dùng đăng ký nhà hàng thì mặc định sẽ để status " đang chờ duyệt"
     * Nếu như bên admin duyệt thì sẽ set status lại thành "Duyệt"
     * Ngược lại không duyệt thì status "Từ chối"
     * Và những nhà hàng hiện bên phía người dùng chỉ hiện khi có status là "Duyệt"
     * @param request: thông tin chi tiết của nhà hàng cần đăng ký
     * @return status và message
     */
    public MessageResponse registerRestaurant(RestaurantRegisterDTO request) {
        Restaurant newRestaurant = new Restaurant();

        newRestaurant.setStatus(RestaurantEnum.DANG_CHO);

        restaurantRepository.save(newRestaurant);
        return null;
    }


}
