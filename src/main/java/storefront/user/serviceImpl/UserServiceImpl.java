package storefront.user.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import storefront.exception.PageNotFoundException;
import storefront.product.serviceImpl.ProductServiceImpl;
import storefront.user.dto.User;
import storefront.user.service.UserService;

import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Override
    public Page<User> getUsers(Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        //do findAll from repository
        return findAll(pageRequest);
    }

    @Override
    public Integer createUser(User user) {
        return save(user);
    }

    @SneakyThrows
    public int save(User user){
        Type listType = new TypeToken<List<User>>() {}.getType();
        Reader reader =new InputStreamReader(Objects.requireNonNull(UserServiceImpl.class
                .getResourceAsStream("/users.json")));
        List<User> users = new Gson().fromJson(reader, listType);

        user.setId(users.isEmpty() ? 1 : users.size()+1);
        users.add(user);

        URL resource = UserServiceImpl.class.getResource("/users.json");
        Writer writer = new FileWriter(resource.getPath());
        Gson gson = new GsonBuilder().create();
        gson.toJson(users, writer);
        writer.flush();
        writer.close();

        log.info("New user added successfully. {}", user);
        return user.getId();
    }

    @SneakyThrows
    public  Page<User> findAll(PageRequest pageRequest) {
        Type listType = new TypeToken<List<User>>() {}.getType();
        Reader reader =new InputStreamReader(Objects.requireNonNull(UserServiceImpl.class
                .getResourceAsStream("/users.json")));
        List<User> users = new Gson().fromJson(reader, listType);

        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(),pageRequest.getPageSize());
        int lowerBound = pageable.getPageNumber() * pageable.getPageSize();
        int upperBound = Math.min(lowerBound + pageable.getPageSize() , users.size());
        if(lowerBound>upperBound){
            throw new PageNotFoundException("Page not found");
        }
        List<User> subList = users.subList(lowerBound, upperBound);

        log.info("Successfully returning user list");
        return new PageImpl<>(subList,pageable,users.size());
    }
}
