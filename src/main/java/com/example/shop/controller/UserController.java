package com.example.shop.controller;

import com.example.shop.common.result.Result;
import com.example.shop.entity.User;
import com.example.shop.query.UserloginQuery;
import com.example.shop.service.UserService;
import com.example.shop.vo.LoginResultVO;
import com.example.shop.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.shop.common.utils.ObtainUserIdUtils.getUserId;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
@RestController
@Tag(name="用户模块")
@AllArgsConstructor
@RequestMapping("user")
public class UserController {


    private final UserService userService;
    @Operation(summary = "微信登录")
    @PostMapping("login/wxMin")
    public Result<LoginResultVO> wxLogin(@RequestBody @Validated UserloginQuery query){
        LoginResultVO userVO=userService.login(query);
        return Result.ok(userVO);
    }
    @SneakyThrows
    @Operation(summary = "用户详情")
    @GetMapping("/profile")
    private Result<User> getUserInfo(HttpServletRequest request) {
        Integer userId = getUserId(request);
        User userInfo = userService.getUserInfo(userId);
        return Result.ok(userInfo);
    }
    @Operation(summary = "修改用户信息")
    @PutMapping("/profile")
    private Result<UserVO> editUserInfo(HttpServletRequest request, @RequestBody @Validated UserVO userVO) {
        Integer userId = getUserId(request);
        userVO.setId(userId);
        UserVO userInfo = userService.editUserInfo(userVO);
        return Result.ok(userInfo);
    }
}