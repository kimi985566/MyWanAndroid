package cn.yangchengyu.mywanandroid.viewmodels

import androidx.lifecycle.MutableLiveData
import cn.yangchengyu.mywanandroid.base.BaseViewModel
import cn.yangchengyu.mywanandroid.data.model.UserInfo
import cn.yangchengyu.mywanandroid.data.repository.LoginRepository
import cn.yangchengyu.mywanandroid.ext.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Desc  : LoginViewModel
 * Author: Chengyu Yang
 * Date  : 2019-09-10
 */

class LoginViewModel : BaseViewModel() {

    var loginUser: MutableLiveData<UserInfo> = MutableLiveData()
    var registerUser: MutableLiveData<UserInfo> = MutableLiveData()
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
            val loginResponse = withContext(Dispatchers.IO) {
                repository.login(userName, password)
            }

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
            val registerResponse = withContext(Dispatchers.IO) {
                repository.register(userName, passWord)
            }

            //处理数据
            executeResponse(
                registerResponse,
                { registerUser.postValue(registerResponse.data) },
                { errorMsg.postValue(registerResponse.errorMsg) }
            )
        }
    }
}