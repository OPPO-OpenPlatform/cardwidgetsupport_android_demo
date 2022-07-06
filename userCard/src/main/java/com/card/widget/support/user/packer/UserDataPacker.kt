package com.card.widget.support.user.packer

import com.card.widget.support.user.data.CardData
import com.oplus.cardwidget.domain.pack.BaseDataPack
import com.oplus.cardwidget.util.Logger
import com.oplus.smartenginehelper.dsl.DSLCoder

class UserDataPacker(private val cardData: CardData) : BaseDataPack() {

    override fun onPack(coder: DSLCoder): Boolean {
        Logger.d(TAG, "onPack")

        /*setTextViewText textID, textValue*/
        coder.setTextViewText("text1", cardData.textValue.toString())
        /*setImageViewResource imageViewID, imageDrawable*/
        coder.setImageViewResource("icon2", cardData.imgId.toString())

        return true
    }
}