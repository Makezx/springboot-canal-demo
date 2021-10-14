package springboot.canal.demo.entity;

import lombok.Data;

/**
 * @description:
 * @author: dada
 * @date: 2020/11/4 20:13
 */
@Data
public class User {

    private Integer userId;
    private String nickname;
    private String name;
    private String phone;
    private String loginName;
    private String loginPwd;

}
