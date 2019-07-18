package khairy.com.eventsforme

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import khairy.com.eventsforme.fragments.EventFragment


class MainActivity : AppCompatActivity() {

    val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_CALENDAR),
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        } else {
            startFragment()
        }


    }

    private fun startFragment() {
        supportFragmentManager.beginTransaction().add(R.id.frame_main, EventFragment(), EventFragment.TAG)
            .addToBackStack(EventFragment.TAG).commitAllowingStateLoss()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startFragment()

                } else {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_CALENDAR),
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
                }
                return
            }

        }


    }
}