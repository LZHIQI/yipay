package com.mican.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.mican.myapplication.*

import com.mican.myapplication.util.ActivityUtils
import com.mican.myapplication.util.BarUtils

class LoginActivity : BaseActivity() {
   var userName:String?=null
    var loginPassword:String?=null
    var login:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        BarUtils.setStatusBarLightMode(this, true)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        login= findViewById<TextView>(R.id.login)
        val forgetPwd = findViewById<TextView>(R.id.to_new_password)
        val register = findViewById<TextView>(R.id.to_register)
        val loading = findViewById<ProgressBar>(R.id.loading)
        register.setOnClickListener {
            val intent=Intent(getThis(),RegisterActivity::class.java)
            ActivityUtils.startActivity(intent)
        }
        username.afterTextChanged {
            userName=it.trim().toString()
            enabele()
        }
        password.afterTextChanged {
            loginPassword=it.trim().toString()
            enabele()
        }
        login?.setOnClickListener {
            UserManager.setUser(User().apply {
                id=userName
                mobile=loginPassword
            })
            val intent= Intent(getThis(), MainActivity::class.java)
            ActivityUtils.startActivity(getThis(), intent)
            finish()
       }
     }

    private fun enabele() {
        login?.isEnabled=!(userName.isNullOrEmpty()||loginPassword.isNullOrEmpty())
    }


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