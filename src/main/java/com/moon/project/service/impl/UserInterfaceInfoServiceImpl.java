package com.moon.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.project.common.ErrorCode;
import com.moon.project.exception.BusinessException;
import com.moon.project.mapper.UserInterfaceInfoMapper;
import com.moon.project.model.entity.UserInterfaceInfo;
import com.moon.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
* @author chenliang
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-07-14 17:02:26
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{
    /**
     * 校验
     * @param userinterfaceInfo
     * @param add
     */
    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userinterfaceInfo, boolean add) {
        if(userinterfaceInfo==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //修改校验，设置校验规则
        if (userinterfaceInfo.getInterfaceInfoId()<=0||userinterfaceInfo.getUserId()<=0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或用户不存在");
        }
        if (userinterfaceInfo.getLeftNum()<0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }
    }

    @Override
    public boolean invokeInterfaceCount(long userId, long interfaceInfoId) {
        if (userId <= 0 || interfaceInfoId <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        LambdaUpdateWrapper<UserInterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInterfaceInfo::getUserId,userId)
                .eq(UserInterfaceInfo ::getLeftNum,0)
                .setSql("leftNum = leftNum-1,totalNum = totalNum +1");
        return update(updateWrapper);

    }

}




