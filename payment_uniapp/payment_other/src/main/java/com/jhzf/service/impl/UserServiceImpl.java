package com.jhzf.service.impl;

import com.jhzf.mapper.UserMapper;
import com.jhzf.pojo.PaymentStore;
import com.jhzf.pojo.PaymentUser;
import com.jhzf.service.UserService;
import com.jhzf.util.Md5;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.user.LoginVo;
import com.jhzf.vo.user.ModifyPwdVo;
import com.jhzf.vo.user.PayOutVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public ResponseDTO register(LoginVo loginVo) {
        int res = userMapper.selectUser(loginVo.getAccount());
        if(res == 1){
            return ResponseDTO.error(0,"注册的手机号已存在");
        }else{
            String messageCode = redisTemplate.opsForValue().get(loginVo.getAccount());
            if(loginVo.getCode().equals(messageCode)){
                System.out.println(loginVo.getAccount() + " " + loginVo.getPwd() + " " + loginVo.getNickName());
                userMapper.regist(loginVo.getAccount(), Md5.getString(loginVo.getPwd()),loginVo.getNickName());
                return ResponseDTO.success(200,"注册成功",null);
            }else{
                return ResponseDTO.error(0,"验证码错误，请重新输入");
            }
        }
    }

    @Override
    public ResponseDTO accountLogin(String account, String pwd) {

        PaymentUser res = userMapper.accountLogin(account, Md5.getString(pwd));
        if(res != null){
            return ResponseDTO.success(200,"登录成功",res);

        }else{
            return ResponseDTO.error(0,"登录失败，账号或密码错误");
        }
    }

    @Override
    public ResponseDTO messageCodeLogin(String account, String code) {
         PaymentUser user = userMapper.messageCodeLogin(account);
        if(user != null){
            String messageCode = redisTemplate.opsForValue().get(account);
            if(code.equals(messageCode)){
                return ResponseDTO.success(200,"登录成功",user);
            }else{
                return ResponseDTO.error(0,"验证码错误");
            }
        }else{
            return ResponseDTO.error(0,"账号不存在,请先去注册");
        }
    }

    @Override
    public ResponseDTO modifyPwd(ModifyPwdVo vo) {
        ResponseDTO dto = null;
        List<PaymentUser> userMsg = userMapper.selectUserMsg(vo.getUserId(),Md5.getString(vo.getOldPwd()));
        if (userMsg.size() > 0){
            int i = userMapper.modifyPwd(vo.getUserId(), Md5.getString(vo.getNewPwd()));
            if (i > 0){
                dto = new ResponseDTO(200,"修改成功",null);
            }else {
                dto = new ResponseDTO(201,"修改失败",null);
            }
        }else {
            dto = new ResponseDTO(202,"旧密码输入错误，修改失败",null);
        }
        return dto;
    }
    @Override
    public ResponseDTO getCashOutMoney(int userId) {
        LocalDate currenDate = LocalDate.now();
        int year = currenDate.getYear();
        int month = currenDate.getMonthValue();
        int day = currenDate.getDayOfMonth();
        double cashOutMoney = 0;
        double auditMoney = 0;
        List<PaymentStore> stores = userMapper.selectStoreInfo(userId);
        HashMap<String,Double> money = new HashMap<>();
        String startTime = year + "-" + month + "-" + 1 + " 00:00:00";
        String endTime = year + "-" + month +"-" + (day-1) + " 23:59:59";
        List<Integer> storeId = userMapper.getCashOutStoreId(userId);
        double totalMoney = 0;
        double totalAuditMoney = 0;
        for(int i = 0;i < storeId.size();i++){
            // 查询当天当日的订单总金额
            auditMoney = userMapper.getAuditingMoney(userId,year + "-" + month + "-" + day + " 00:00:00",year + "-" + month + "-" + day + " 23:59:59",storeId.get(i)) + stores.get(i).getStoreAuditMoney();
            // 将上里面的金额更新到对应店铺的审核金额里
            userMapper.updateAuditMoney(stores.get(i).getStoreId(),auditMoney);
            // 将当日查询的订单状态更新
            userMapper.updateOrderAuditStatus(year + "-" + month + "-" + day + " 00:00:00",year + "-" + month + "-" + day + " 23:59:59",storeId.get(i));
            // 对审核金额累加并返回给前端
            totalAuditMoney += auditMoney;
            // 查询当前店铺id当日之前的订单金额+可提现金额
            cashOutMoney = userMapper.getCashOutMoney(userId,startTime,endTime,storeId.get(i)) + stores.get(i).getStoreUsableMoney();
            // 更新对应店铺的可提现金额
            userMapper.updateCashMoney(stores.get(i).getStoreId(),cashOutMoney);
            // 将查询完的订单更新状态
            userMapper.updateOrderSelectStatus(startTime,endTime,storeId.get(i));
            // 可提现金额累加
            totalMoney += cashOutMoney;
        }

        money.put("cashOutMoney",totalMoney);
        money.put("auditMoney",totalAuditMoney);
        return ResponseDTO.success(200,"查询成功",money);
    }
    @Override
    public ResponseDTO getCashOutStore(int userId) {
        List<PaymentStore> stores = userMapper.selectStoreInfo(userId);
        return ResponseDTO.success(200,"获取成功",stores);
    }

    @Override
    public ResponseDTO getStoreCashMoney(int storeId,int userId) {
        PaymentUser usableMoney = userMapper.getStoreCastOutMoney(storeId,userId);
        return ResponseDTO.success(200,"获取成功",usableMoney);
    }

    @Override
    public ResponseDTO doCashOut(PayOutVo payOutVo) {
        System.out.println(payOutVo.getPayOutMoney() + " " + payOutVo.getStoreId());
        int doCashOut = userMapper.doCashOut(payOutVo.getPayOutMoney(), payOutVo.getStoreId(), payOutVo.getPayOutCard());
        PaymentStore store = userMapper.cashOutStoreInfo(payOutVo.getStoreId());
        double cashOutMoney = store.getStoreUsableMoney() - payOutVo.getPayOutMoney();
        System.out.println(cashOutMoney + "店铺金额");
        userMapper.updateStoreCashOutMoney(payOutVo.getStoreId(),cashOutMoney);
        return ResponseDTO.success(200, "提现成功", null);
    }
    @Override
    public ResponseDTO resetPwd(ResetPwdVo resetPwdVo) {
        int user = userMapper.selectUser(resetPwdVo.getAccount());
        if(user == 1){
            if(resetPwdVo.getPwd().equals(resetPwdVo.getConfirmPwd())){
                String messageCode = redisTemplate.opsForValue().get(resetPwdVo.getAccount());
                if(resetPwdVo.getCode().equals(messageCode)){
                    userMapper.updateUserPwd(resetPwdVo.getAccount(), Md5.getString(resetPwdVo.getPwd()));
                    return ResponseDTO.success(200,"修改成功");
                }else{
                    return ResponseDTO.error(202,"验证码输入错误");
                }
            }else{
                return ResponseDTO.error(201,"两次输入的密码不一致");
            }
        }else{
            return ResponseDTO.error(204,"用户不存在,请先去注册");
        }
    }
}
