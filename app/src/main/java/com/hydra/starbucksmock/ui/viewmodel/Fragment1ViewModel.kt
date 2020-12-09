package com.hydra.starbucksmock.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hydra.starbucksmock.ui.view.DetailListModel
import com.hydra.starbucksmock.ui.view.TitleValueListView

class Fragment1ViewModel : ViewModel() {
    private val _detailListModel = DetailListModel().apply {
        this.putData(this.getStandardMap().apply {
            this[DetailListModel.Key_Type] = TitleValueListView.TYPE_TWO_DESCRIPTION
            this[DetailListModel.Key_Title] = "Interaction Rewards"
            this[DetailListModel.Key_Value] = "1.849"
        })
        this.putData(this.getStandardMap().apply {
            this[DetailListModel.Key_Type] = TitleValueListView.TYPE_TWO_DESCRIPTION
            this[DetailListModel.Key_Title] = "Social Reward"
            this[DetailListModel.Key_Value] = "1.849"
        })
        this.putData(this.getStandardMap().apply {
            this[DetailListModel.Key_Type] = TitleValueListView.TYPE_TWO_DESCRIPTION
            this[DetailListModel.Key_Title] = "Revenue"
            this[DetailListModel.Key_Value] = "1.849"
        })
    }
    val detailListModel: MutableLiveData<DetailListModel> = MutableLiveData()

    private fun notifyDataChanged() {
        detailListModel.postValue(_detailListModel)
    }

    init {
        notifyDataChanged()
    }

}