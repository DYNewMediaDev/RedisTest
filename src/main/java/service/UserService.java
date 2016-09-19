package service;

import entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import utils.SpringContextUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author Gude
 * @Date 2015/12/27.
 */
@Service
public class UserService {
    public static final String CACHE_USER_LIST = "CACHE_USER_LIST";//固定的key值

    @Caching(
                put =   {
                            @CachePut(value = "user", key = "#user.id")
                        },
                evict = {
//                            @CacheEvict("defaultCache")
//                            @CacheEvict(value = "defaultCache", allEntries = true)
                              @CacheEvict(value = "defaultCache", key = "#root.target.CACHE_USER_LIST")
                        }
                )
    public User save(User user) {
        System.out.println("save user to db");
        users.add(user);
        return user;
    }

    @Cacheable(value = "user", key = "#id")
    public User findById(int id) {
        System.out.println("find user in db by[" + id + "]");
        return findUsrById(id);
    }

    public User findByIdTestAop(int id) {
        System.out.println("from userService");
        return findById(id);
    }

    @Caching(
            //cacheable = @Cacheable("users"),
            cacheable = {
                            @Cacheable(value = "user", key = "#id")
                    },
            put =   {
                        @CachePut(value = "user", key = "#user.id"),
                        @CachePut(value = "user", key = "#user.username")
                    },
            evict = {
                        @CacheEvict("cache2"),
                        @CacheEvict(value = "cache3", allEntries = true)
                    }
            )
    public void testABC(){

    }

    /**
     * 查询结果不为空 才放进缓存 unless
     *
     * @return
     */
    @Cacheable(value = "defaultCache", key = "#root.target.CACHE_USER_LIST", unless = "#result==null")
    public List<User> listAll() {
        System.out.println("list all users in db");
        return users;
    }

    static List<User> users = new ArrayList<User>();

    static {
        buildExamUsers();
    }

    private User findUsrById(int userId){
        for(User user:users){
            if(user.getId() == userId){
                return user;
            }
        }
        return null;
    }

    private static List<User> buildExamUsers() {
        User user = new User();
        Random random = new Random();
        user.setId(random.nextInt());
        user.setUsername("aaa");
        users.add(user);

        user = new User();
        user.setId(random.nextInt());
        user.setUsername("bbb");
        users.add(user);

        user = new User();
        user.setId(random.nextInt());
        user.setUsername("ccc");
        users.add(user);

        user = new User();
        user.setId(1111);
        user.setUsername("ccc");
        users.add(user);

        return users;
    }
}
