package com.example.aura.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.aura.fragment.CommunityFragment
import com.example.aura.fragment.DashboardFragment
import com.example.aura.fragment.EmergencyFragment
import com.example.aura.fragment.HelpFragment
import com.example.aura.fragment.LegalFragment
import com.example.aura.fragment.ProtectionFragment
import com.example.aura.R
import com.example.aura.fragment.SafetyFragment
import com.example.aura.fragment.SettingsFragment
import com.google.android.material.navigation.NavigationView

class NewActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var etName:EditText

    var previousMenuItem:MenuItem?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        drawerLayout=findViewById(R.id.drawer_layout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.frameLayout)
        navigationView=findViewById(R.id.navigationView)
        setUpToolbar()

        openDashboard()

        val actionbarDrawerToggle=ActionBarDrawerToggle(this@NewActivity,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionbarDrawerToggle)
        actionbarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId){
                R.id.dashboard ->{
                    openDashboard()
                    drawerLayout.closeDrawers()
                }
                R.id.emergency ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, EmergencyFragment())
                        .commit()
                    supportActionBar?.title="Emergency Features"
                    drawerLayout.closeDrawers()
                }
                R.id.safety ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, SafetyFragment())
                        .commit()
                    supportActionBar?.title="Live Safety & Monitoring"
                    drawerLayout.closeDrawers()
                }
                R.id.protection ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProtectionFragment())
                        .commit()
                    supportActionBar?.title="AI & Smart Protection"
                    drawerLayout.closeDrawers()
                }
                R.id.legal ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, LegalFragment())
                        .commit()
                    supportActionBar?.title="Legal Assistance"
                    drawerLayout.closeDrawers()
                }
                R.id.community ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, CommunityFragment())
                        .commit()
                    supportActionBar?.title="Community & Engagement"
                    drawerLayout.closeDrawers()
                }
                R.id.settings ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, SettingsFragment())
                        .commit()
                    supportActionBar?.title="Settings & Customization"
                    drawerLayout.closeDrawers()
                }
                R.id.help ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HelpFragment())
                        .commit()
                    supportActionBar?.title="Help & Support"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openDashboard(){
        val fragment= DashboardFragment()
        val mFragmentManager=supportFragmentManager
        val mFragmentTransaction=mFragmentManager.beginTransaction()
        mFragmentTransaction.replace(R.id.frameLayout,fragment)
        mFragmentTransaction.commit()
        supportActionBar?.title="Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frameLayout)
        when(frag){
            !is DashboardFragment ->openDashboard()
            else->super.onBackPressed()
        }
    }
}