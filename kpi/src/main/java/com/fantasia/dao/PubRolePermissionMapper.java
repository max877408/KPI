package com.fantasia.dao;

import com.fantasia.bean.PubRolePermission;

public interface PubRolePermissionMapper {

    int delete(String id);

    int insert(PubRolePermission record);

    int update(PubRolePermission record);
}