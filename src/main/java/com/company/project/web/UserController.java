package com.company.project.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
* Created by CodeGenerator on 2018/09/06.
*/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

     @Autowired
    RedisTemplate redisTemplate;

//     @Autowired
//    StringRedisTemplate stringRedisTemplate;
    @PostMapping("/add")
    public Result add(User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        redisTemplate.opsForList().leftPush("user:list", JSON.toJSONString(list));
        redisTemplate.opsForValue().set("user:name", "张三1");
        
//        stringRedisTemplate.opsForHash().put("redisHash","name","tom");
//        stringRedisTemplate.opsForHash().put("redisHash","age",26);
//        stringRedisTemplate.opsForHash().put("redisHash","class","6");

		Map<String,Object> testMap = new HashMap();
        testMap.put("name","jack");
        testMap.put("age",27);
        testMap.put("class","1");
        redisTemplate.opsForHash().putAll("redisHash1",testMap);
//        System.out.println(redisTemplate.opsForHash().entries("redisHash"));
        return ResultGenerator.genSuccessResult(pageInfo);	
    }
}
