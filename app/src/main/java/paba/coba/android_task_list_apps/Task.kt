package paba.coba.android_task_list_apps

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task (
    var nama : String,
    var tanggal : String,
    var deskripsi : String,
    var isStart : Boolean,
    var isDone : Boolean
) : Parcelable