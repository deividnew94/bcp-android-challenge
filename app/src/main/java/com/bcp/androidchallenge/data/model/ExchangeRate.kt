package com.bcp.androidchallenge.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bcp.androidchallenge.core.ResultType
import com.bcp.androidchallenge.core.model.ExchangeRateModel
import com.bcp.androidchallenge.core.model.ExchangesRateModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by David Hernandez on 03 July 2020
 */

@Parcelize
data class ExchangeRate(
    @SerializedName("id")
    val exchangeRateId: String = "",
    @SerializedName("currency")
    val currency: String = "",
    @SerializedName("purchasevalue")
    val purchasevalue: String = "",
    @SerializedName("salevalue")
    val salevalue: String = "",
) : Parcelable

data class ExchangeRateList(
    @SerializedName("exchangerates")
    val exchangeRateList: List<ExchangeRate> = listOf()
)

@Entity(tableName = "exchangeratesTable")
data class ExchangeRateEntity(
    @PrimaryKey
    val exchangeRateId: String,
    @ColumnInfo(name = "currency")
    val currency: String = "",
    @ColumnInfo(name = "purchasevalue")
    val purchasevalue: String = "",
    @ColumnInfo(name = "salevalue")
    val salevalue: String = ""
)

fun List<ExchangeRateEntity>.asExchangeRateList(): List<ExchangeRate> = this.map {
    ExchangeRate(it.exchangeRateId, it.currency, it.purchasevalue, it.salevalue)
}

fun ExchangeRate.asExchangeRateEntity(): ExchangeRateEntity =
    ExchangeRateEntity(this.exchangeRateId, this.currency, this.purchasevalue, this.salevalue)
