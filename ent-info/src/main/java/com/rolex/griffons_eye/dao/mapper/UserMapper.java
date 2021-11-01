package com.rolex.griffons_eye.dao.mapper;

import com.rolex.griffons_eye.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author rolex
 * @since 2021
 */
public interface UserMapper {

    void save(@Param("user") User user);

    User findById(@Param("id") Long id);
}
