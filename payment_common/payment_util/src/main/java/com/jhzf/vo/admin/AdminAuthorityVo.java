package com.jhzf.vo.admin;

import lombok.Data;

import java.util.List;

/**
 * @author 吴政顺
 * @date 2024/4/25 16:31
 */
@Data
public class AdminAuthorityVo {
    private int adminId;
    private List<Integer> menuId;
}
