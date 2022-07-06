package com.oplus.support.view

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.card.widget.support.R
import com.card.widget.support.databinding.ActivityCardBinding
import com.oplus.support.inflater
import com.oplus.support.isAssertFileExists
import java.io.File

class MainActivity : AppCompatActivity() {

    private var mSmartView: View? = null
    private var loadingView: View? = null
    private val mBindings by inflater<ActivityCardBinding>()

    private var layoutName = "card.json"
    private var layoutNameSd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions(
            arrayOf(
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
            ), 100
        )

        mBindings.btnUnionTest.setOnClickListener {
            val intent = Intent(this@MainActivity, DebugActivity::class.java)
            this@MainActivity.startActivity(intent)
        }

        initPreViewListener()

        initTextChangeListener()
    }

    private fun showLoading(show: Boolean) {
        mBindings.loadingProgress.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun isPreViewFileNameEffect(name: String): String? {
        if (name.isEmpty() || (!name.endsWith(".json"))) {
            return getString(R.string.tips_files_name_effect)
        }
        return null
    }

    private fun initPreViewListener() {
        mBindings.btnPreView.setOnClickListener {
            /**这里的view布局需要注意的是需要放到assets目录下的*/

            isPreViewFileNameEffect(layoutName)?.let {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!layoutName.isAssertFileExists(this@MainActivity)) {
                Toast.makeText(this@MainActivity, getString(R.string.tips_assert_files_effect), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }

        mBindings.btnPreViewSd.setOnClickListener {
            /**这里的view布局需要注意的是需要push到SD卡的根目录下*/
            isPreViewFileNameEffect(layoutNameSd)?.let {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val file = File(Environment.getExternalStorageDirectory(), layoutNameSd)
            if (!file.exists()) {
                Toast.makeText(this@MainActivity, getString(R.string.tips_sd_files_effect), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }
    }

    private fun initTextChangeListener() {
        mBindings.editLayoutName.setText(this.layoutName)

        mBindings.editLayoutName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                layoutName = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        mBindings.editLayoutNameSd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                layoutNameSd = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }
}