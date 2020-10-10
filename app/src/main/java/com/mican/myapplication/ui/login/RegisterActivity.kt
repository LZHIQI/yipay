package com.mican.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.mican.myapplication.BaseActivity
import com.mican.myapplication.api.UserContract
import com.mican.myapplication.api.imp.UserContractImp
import com.mican.myapplication.api.req.Register
import com.mican.myapplication.databinding.ActivityRegistBinding
import com.mican.myapplication.util.BarUtils
import com.mican.myapplication.util.LogUtils
import com.mican.myapplication.util.ToastUtils

class RegisterActivity : BaseActivity<UserContractImp>(), UserContract.View {
    lateinit var inflate: ActivityRegistBinding;
    var name: String? = null
    var password: String? = null
    var passwordTwo: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate = ActivityRegistBinding.inflate(layoutInflater)
        setContentView(inflate.root)
        BarUtils.setStatusBarLightMode(this, true)
        inflate.password.afterTextChanged {
            password = it.trim()
            enabele()
        }
        inflate.passwordTwo.afterTextChanged {
            passwordTwo = it.trim()
            enabele()
        }

        inflate.username.afterTextChanged {
            name = it.trim()
            enabele()
        }
        inflate.login.setOnClickListener {
            if (password != passwordTwo) {
                ToastUtils.showShort("密码不一致，请重写输入")
                return@setOnClickListener
            }
            inflate.loading.visibility = View.VISIBLE
            val register = Register()
            register.username = name
            register.password = password
            register.secPassword = passwordTwo
            mPresenter.register(register, object : UserContract.View {
                override fun getError(message: String?) {
                    inflate.loading.visibility = View.GONE
                    LogUtils.e("message"+message)
                    ToastUtils.showShort(message)

                }

                override fun getSuccess(o: Any?) {
                    inflate.loading.visibility = View.GONE
                    ToastUtils.showSuccess("注册成功")
                    val intent = Intent(`this`, LoginActivity::class.java)
                    startActivity(intent)
                    inflate.loading.postDelayed({
                        finish()
                    },1000)

                }

            })

        }

    }

    private fun enabele() {
        inflate.login.isEnabled = !(name.isNullOrEmpty() || password.isNullOrEmpty() || passwordTwo.isNullOrEmpty())
    }


    override fun setPresenter(): UserContractImp {
        return UserContractImp()
    }

    override fun getError(message: String?) {
    }

    override fun getSuccess(o: Any?) {
    }


    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}