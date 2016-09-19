package springCacheRedis;

import entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.OtherService;
import service.UserService;

import java.util.List;

/**
 * User: herry
 * Date: 16-7-25 15:53
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-cache-anno.xml");// 加载spring配置文件

        UserService s = (UserService) context.getBean("userServiceBean");
        OtherService otherService = (OtherService) context.getBean("otherServiceBean");

        //查找不存在的用户，缓存不起作用
        s.findById(1234);
        s.findById(1234);

        // 第一次查询，应该走数据库
        s.findById(1111);
        //应该走缓存
        s.findById(1111);

        User user = new User();
        user.setId(2222);
        user.setUsername("GGGG");
        s.save(user);
        //会从缓存中获取
        s.findById(2222);

        user = new User();
        user.setId(3333);
        user.setUsername("TTTT");
        s.save(user);

        //缓存起作用
        otherService.findByIdTestAop(3333);

        //缓存不起作用
        s.findByIdTestAop(3333);


        //查看list的生效与失效
        List<User> users = s.listAll();
        System.out.println("user size:"+users.size());
        users = s.listAll();
        System.out.println("user size:"+users.size());

        user = new User();
        user.setId(4444);
        user.setUsername("IIII");
        s.save(user);

        users = s.listAll();
        System.out.println("user size:"+users.size());

        users = s.listAll();
        System.out.println("user size:" + users.size());
    }
}
