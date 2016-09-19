package service;

import org.springframework.stereotype.Service;

import entity.User;
import utils.SpringContextUtil;

/**
 * User: herry Date: 16-7-27 17:36
 */
@Service
public class OtherService {

	public static UserService userService = (UserService) SpringContextUtil.getBean("userService");

	public User findByIdTestAop(int id) {
		System.out.println("from OtherService");
		return userService.findById(id);
	}
}
