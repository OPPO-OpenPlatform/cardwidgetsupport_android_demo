package com.card.widget.support.user.packer

import com.oplus.cardwidget.domain.pack.BaseDataPackByName
import com.oplus.cardwidget.util.getCardId
import com.oplus.cardwidget.util.getCardType
import com.oplus.smartenginehelper.dsl.DSLCoder

/** 快应用、快捷功能等特殊卡片可选择该方式进行和负一屏界面的通信，
 * 其他业务方卡片可不用关心该方式
 * */
class InstantDataPacker : BaseDataPackByName() {

    override fun onPack(coder: DSLCoder, widgetCode: String): String {

        /**设置你要传的数据*/
        coder.setTextViewText("cardType", widgetCode.getCardType().toString())
        coder.setTextViewText("cardId", widgetCode.getCardId().toString())
        coder.setTextViewText("uri", "www.instant.uri")

        /**返回name字段的值*/
        return "instantName"
    }
}