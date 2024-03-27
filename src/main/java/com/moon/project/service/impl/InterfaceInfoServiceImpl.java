package com.moon.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.project.common.ErrorCode;
import com.moon.project.exception.BusinessException;
import com.moon.project.mapper.InterfaceInfoMapper;
import com.moon.project.model.entity.InterfaceInfo;
import com.moon.project.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author chenliang
* @description 针对表【interface_info】的数据库操作Service实现
* @createDate 2023-07-06 15:48:54
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
         Long id = interfaceInfo.getId();
         String name = interfaceInfo.getName();
         String description = interfaceInfo.getDescription();
         String url = interfaceInfo.getUrl();
         String requestHeader = interfaceInfo.getRequestHeader();
         String responseHeader = interfaceInfo.getResponseHeader();
         Integer status = interfaceInfo.getStatus();
         String method = interfaceInfo.getMethod();
         Long userId = interfaceInfo.getUserId();
         Date createTime = interfaceInfo.getCreateTime();
         Date updateTime = interfaceInfo.getUpdateTime();
         Integer isDelete = interfaceInfo.getIsDelete();

        //修改校验，设置校验规则
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }
}




