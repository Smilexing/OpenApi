package com.moon.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.project.model.entity.UserInterfaceInfo;

/**
* @author chenliang
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2023-07-14 17:02:26
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    boolean invokeInterfaceCount(long userId, long interfaceInfoId);
}
