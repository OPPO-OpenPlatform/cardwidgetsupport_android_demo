package com.card.widget.support.user.data

import com.card.widget.support.user.packer.UserDataPacker
import com.oplus.cardwidget.domain.pack.BaseDataPack
import kotlin.properties.Delegates

interface DataChangeListener<T : BaseDataPack> {
    fun onDataChange(data: T)
}

class StateNotice<T : BaseDataPack>(private val initData: T) {
    private val listeners = mutableListOf<DataChangeListener<T>>()

    private var data: T by Delegates.observable(initData) { _, data1, _ ->
        listeners.forEach { it.onDataChange(data1) }
    }

    fun addNotice(listener: DataChangeListener<T>) {
        listeners.add(listener)
    }

    fun updateData(newData: T) {
        data = newData
    }
}

val USER_DATA_INSTANCE by lazy {
    StateNotice(UserDataPacker(CardData()))
}