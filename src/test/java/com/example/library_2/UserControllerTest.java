package com.example.library_2;

import com.example.controller.UserController;
import com.example.pojo.Result;
import com.example.pojo.UserDTO;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Test
    public void testList() {
        // Given: 初始化用户列表
        List<UserDTO> expectedUsers = userService.findAll();

        // When: 调用用户列表方法
        Result<List<UserDTO>> result = userController.list();

        // Then: 断言结果不为空，且用户列表与预期相等
        assertNotNull(result);
        assertEquals(expectedUsers, result.getData());
    }
}

