package com.moon.project.service;

import com.moon.project.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenliang
* @description 针对表【interface_info】的数据库操作Service
* @createDate 2023-07-06 15:48:54
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
