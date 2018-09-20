package com.company.project.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.dao.UserMapper;
import com.company.project.model.User;
import com.company.project.service.UserService;


/**
 * Created by CodeGenerator on 2018/09/06.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
	
    
     
	@Override
	public int deleteById(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int save(User record) {
		return userMapper.insert(record);
	}

	@Override
    @Cacheable(value = "user", key = "'user_'+#id")
//	@cacheable(value = "user", key = "'user_page'")
	public User findById(Integer id) {
		
		System.out.println("进入实现类获取数据！" + id);
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	@Cacheable(value = "user", key = "'user_page'")
	public List<User> findAll() {
		// 具体使用
		System.out.println("the methos excuted");
		List<User> list = userMapper.selectAll();
		
		return list;
	}
	
	@Override
	public int update(User record) {
		return userMapper.updateByPrimaryKey(record);
	}
}
