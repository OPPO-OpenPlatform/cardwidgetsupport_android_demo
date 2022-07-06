package com.card.widget.support.user.provider

import android.content.Context
import com.card.widget.support.user.data.CardData
import com.card.widget.support.user.data.DataChangeListener
import com.card.widget.support.user.data.USER_DATA_INSTANCE
import com.card.widget.support.user.packer.UserDataPacker
import com.oplus.cardwidget.domain.action.CardWidgetAction
import com.oplus.cardwidget.serviceLayer.AppCardWidgetProvider
import com.oplus.cardwidget.util.Logger
import com.oplus.cardwidget.util.getCardId
import com.oplus.cardwidget.util.getCardType


class UserCardWidgetProvider : AppCardWidgetProvider(), DataChangeListener<UserDataPacker> {
    private val TAG = "UserCardWidgetProvider"

    override fun onCreate(): Boolean {
        val state = super.onCreate()
        USER_DATA_INSTANCE.addNotice(this)
        return state
    }

    override fun getCardLayoutName(widgetCode: String): String {
        Logger.d(
            TAG,
            "getCardLayoutName cardTye: ${widgetCode.getCardType()} cardId is: ${widgetCode.getCardId()}"
        )

        /**这里可以根据业务的实际需求，在这里区分cardType、cardId 设置不同的布局表现 */
        return when (widgetCode.getCardType()) {
            0 -> "button.json"
            1 -> "button.json"
            else -> "button.json"
        }
    }

    override fun onCardsObserve(context: Context, widgetCodes: List<String>) {
        super.onCardsObserve(context, widgetCodes)

        /* 当业务对应的卡片订阅数量有变更时，这里会回调*/
        Logger.d(TAG, "onCardsObserve widgetCodes list size is: ${widgetCodes.size}}")
    }

    override fun onResume(context: Context, widgetCode: String) {
        Logger.d(TAG, "onResume cardTye: ${widgetCode.getCardType()} cardId is: ${widgetCode.getCardId()}")

        /**
         **需要注意的是，onResume 方法仅在订阅卡片那一次会回调，后续数据更新的话，业务需要在自己进程起来后post数据进行更新
         **/

        /* 准备好数据后，自己在将数据post到远程进行刷新*/
        CardWidgetAction.postUpdateCommand(context, UserDataPacker(CardData()), widgetCode)
    }

    override fun onDataChange(data: UserDataPacker) {
        Logger.d(TAG, "onDataChange data is: ${data}}")

        /* getShowedCardList() 这个接口能拿到当前业务已订阅的卡片 **/
        getShowedCardList().forEach { widgetCode ->

            /*将你准备好的数据，post到远程负一屏/桌面进行刷新*/
            CardWidgetAction.postUpdateCommand(context, data, widgetCode)

            /** 如果你有切换卡片布局的需求，可以看下这里*/
            /* 调用switchLayoutCommand方法进行配置数据切换，然后数据刷新处理和原来的形式一样，调用postUpdateCommand即可
            **
            * CardWidgetAction.switchLayoutCommand(widgetCode, "step.json")
            * CardWidgetAction.postUpdateCommand(context, StepDataPacker(CardData()), widgetCode)
            * *
            */
        }
    }
}