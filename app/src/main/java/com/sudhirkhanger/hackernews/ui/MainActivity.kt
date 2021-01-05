package com.sudhirkhanger.hackernews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.sudhirkhanger.hackernews.databinding.ActivityMainBinding

private const val MAIN_FRAGMENT_TAG = "tag_main_fragment"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(binding.container.id,
                        MainFragment.newInstance("", ""),
                        MAIN_FRAGMENT_TAG)
            }
        }
    }
}
