package com.wxn.mybatis.model.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体基类
 * @author wuxiangnan3
 * @date 2021-03-04
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * 主键
     * Column:id; Nullable:false
     */
    private Long id;

    /**
     * 创建人
     * Column:create_user; Nullable:true
     */
    private String createUser;

    /**
     * 创建时间
     * Column:create_time; Nullable:true
     */
    private Date createTime;

    /**
     * 更新人
     * Column:update_user; Nullable:true
     */
    private String updateUser;

    /**
     * 更新时间
     * Column:update_time; Nullable:true
     */
    private Date updateTime;

    /**
     * 删除标识：0未删除；1已删除
     * Column:is_delete; Nullable:false
     */
    private Integer isDelete;

    /**
     * 时间戳
     * Column:ts; Nullable:false
     */
    private Date ts;

    /**
     * 版本号
     * Column:version; Nullable:false
     */
    private Integer version;

    /**
     * 压测标志位
     * Column:is_test; Nullable:false
     */
    private Integer isTest = 0;

}
