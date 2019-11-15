package cn.yangchengyu.mywanandroid.viewmodels

import androidx.lifecycle.MutableLiveData
import cn.yangchengyu.mywanandroid.base.BaseViewModel
import cn.yangchengyu.mywanandroid.data.model.UserInfo
import cn.yangchengyu.mywanandroid.data.repository.LoginRepository
import cn.yangchengyu.mywanandroid.ext.executeResponse

/**
 * Desc  : LoginViewModel
 * Author: Chengyu Yang
 * Date  : 2019-09-10
 */

class LoginViewModel : BaseViewModel() {

    val loginUser: MutableLiveData<UserInfo> by lazy { MutableLiveData<UserInfo>() }
    val registerUser: MutableLiveData<UserInfo> by lazy { MutableLiveData<UserInfo>() }
    val errorMsg: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    private val repository by lazy { LoginRepository() }

    /**
     * 登陆
     *
     * @param userName 用户名
     * @param password 密码
     * */
    fun login(userName: String, password: String) {
        viewModelLaunch {
            //获取数据
            val loginResponse = repository.login(userName, password)

            //处理数据
            executeResponse(
                loginResponse,
                { loginUser.postValue(loginResponse.data) },
                { errorMsg.postValue(loginResponse.errorMsg) }
            )
        }
    }

    /**
     * 注册
     *
     * @param userName 用户名
     * @param passWord 密码
     * */
    fun register(userName: String, passWord: String) {
        viewModelLaunch {
            //获取数据
            val registerResponse = repository.register(userName, passWord)

            //处理数据
            executeResponse(
                registerResponse,
                { registerUser.postValue(registerResponse.data) },
                { errorMsg.postValue(registerResponse.errorMsg) }
            )
        }
    }
}