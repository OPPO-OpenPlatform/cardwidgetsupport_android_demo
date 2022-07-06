package com.oplus.support.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.card.widget.support.R
import com.card.widget.support.databinding.ActivityDebugBinding
import com.card.widget.support.user.data.CardData
import com.card.widget.support.user.data.DataChangeListener
import com.card.widget.support.user.data.StateNotice
import com.card.widget.support.user.data.USER_DATA_INSTANCE
import com.card.widget.support.user.packer.UserDataPacker
import com.oplus.support.inflater
import com.oplus.support.util.DateUtil

class DebugActivity : AppCompatActivity() {


    private val mBindings by inflater<ActivityDebugBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBindings.btnPostData.setOnClickListener {
            // 可在这里根据你的widgetCode来发送你要刷新的数据
            //CardWidgetAction.postUpdateCommand(this@DebugActiviity, UserDataPacker(CardData()), "widgetCode")

            USER_DATA_INSTANCE.updateData(UserDataPacker(CardData())) // 在这里将数据切换到UserCardWidgetProvider，然后推送到远端进行刷新
        }

        /*
        * 其他的可以跟进自己具体情况添加对应的业务逻辑，来和负一屏调试布局显示的效果
        * */
    }
}