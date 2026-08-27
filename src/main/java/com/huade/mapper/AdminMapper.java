package com.huade.mapper;

import com.huade.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    Admin findByUsername(@Param("username") String username);
}
