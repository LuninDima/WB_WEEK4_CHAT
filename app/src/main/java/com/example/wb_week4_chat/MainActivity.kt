package com.example.wb_week4_chat


import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wb_week4_chat.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnTouchListener,
    ViewTreeObserver.OnScrollChangedListener {
    private lateinit var scrollView: ScrollView
    var count = 0
    var countView = 0
    private lateinit var layout: LinearLayout
    var arrayView = arrayListOf<View>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layout = binding.linerLayout
        scrollView = binding.scrollview
        scrollView.setOnTouchListener(this)
        scrollView.viewTreeObserver.addOnScrollChangedListener(this)
        binding.swipeToRefresh.isRefreshing = false
        initView()
        swipeToRefresh()
        binding.swipeToRefresh.post {
            binding.swipeToRefresh.isRefreshing = false
        }



    }

    private fun swipeToRefresh() {
        binding.swipeToRefresh.post {
           binding.swipeToRefresh.isRefreshing = false
        }
        binding.swipeToRefresh.setOnRefreshListener {

            layout.removeAllViews()
            countView = 0
            for (i in 1..10) {
                layout.addView(arrayView[i - 1])
                countView += 1
            }

            Toast.makeText(this, "Обновлено", Toast.LENGTH_SHORT).show()
            binding.swipeToRefresh.isRefreshing = false
        }

    }

    private fun initView() {
// cоздание массива списка чатов
        for (i in 1..100) {
            count += 1
            var dinamicLayoutView = layoutInflater.inflate(R.layout.chat_layout, null)
            dinamicLayoutView.findViewById<TextView>(R.id.textViewChat).text = count.toString()  + " чат"
            arrayView.add(dinamicLayoutView)
        }

        for (i in 1..10) {
            layout.addView(arrayView[i - 1])
            countView += 1
        }

    }


    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }

    override fun onScrollChanged() {
        val view = scrollView.getChildAt(scrollView.childCount - 1)
        val topDetector = scrollView.scrollY
        val bottomDetector: Int = view.bottom - (scrollView.height + scrollView.scrollY)
            // Если пользователь доскроллил до нижнего края, то добавляем еще один элемент из массива списка чатов arrayView
        if (bottomDetector == 0) {
                if (countView + 1 <= arrayView.size) {

                    layout.addView(arrayView[countView])

                    countView += 1
                }
        }
        // Если пользователь доскроллил до верхнего края, то обновляем весь список

        if (topDetector <= 0) {

                swipeToRefresh()


        }
    }
}