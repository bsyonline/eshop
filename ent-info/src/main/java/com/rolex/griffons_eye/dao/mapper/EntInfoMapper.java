package com.rolex.griffons_eye.dao.mapper;

import com.rolex.griffons_eye.model.EntInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author rolex
 * @since 2021
 */
public interface EntInfoMapper {

    void updateEntInfo(@Param("entInfo") EntInfo entInfo);

    EntInfo findById(@Param("entId") String entId);
}
