package sample.android.example.recyclerview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerViewをレイアウトから探す
        val recyclerView = findViewById<RecyclerView>(R.id.timeZoneList)

        // タイムゾーンリスト用のアダプター
        val adapter = SampleAdapter(this) { timeZone ->
            // タップされた位置にあるタイムゾーンをトースト表示する
            Toast.makeText(this, timeZone.displayName, Toast.LENGTH_SHORT).show()
        }

        // RecyclerViewにアダプターをセットする
        recyclerView.adapter = adapter

        // 縦に直線的に表示するレイアウトマネージャ
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false)
    }
}

// RecyclerView用のサンプルアダプタ
class SampleAdapter(context: Context,
                    private val onTimeZoneClicked : (TimeZone) -> Unit)
    : RecyclerView.Adapter<SampleAdapter.SampleViewHolder>() {

    // レイアウトからビューを生成するInflater
    private val inflater = LayoutInflater.from(context)

    // リサイクラービューで表示するタイムゾーンのリスト
    private val timeZones = TimeZone.getAvailableIDs().map { id -> TimeZone.getTimeZone(id) }

    // 表示するべき値をViewにセットする
    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        // 位置に応じたタイムゾーンを得る
        val timeZone = timeZones[position]
        // 表示内容を更新する
        holder.timeZone.text = timeZone.id
    }

    // 新しくViewを作る時に呼ばれる
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        // Viewを生成する
        val view = inflater.inflate(R.layout.list_time_zone_row, parent, false)

        // ViewHolderを作る
        val viewHolder = SampleViewHolder(view)

        // viewをタップしたときの処理
        view.setOnClickListener {
            // アダプター上の位置を得る
            val position = viewHolder.adapterPosition
            // 位置をもとに、タイムゾーンを得る
            val timeZone = timeZones[position]
            // コールバックを呼び出す
            onTimeZoneClicked(timeZone)
        }

        return viewHolder
    }

    override fun getItemCount(): Int = timeZones.size

    // Viewへの参照をもっておくViewHolder
    class SampleViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val timeZone = view.findViewById<TextView>(R.id.timeZone)
    }
}
