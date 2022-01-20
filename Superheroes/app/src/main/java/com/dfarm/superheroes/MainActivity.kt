package com.dfarm.superheroes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.dfarm.superheroes.Repository.localRepository.LocalImageLoadingRepository
import com.dfarm.superheroes.Repository.localRepository.LocalVersionRepository
import com.dfarm.superheroes.Sqlite.Entities.UpdateVersion
import com.dfarm.superheroes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private lateinit var  appUpdateManager : AppUpdateManager
//    private val MY_REQUEST_CODE = 5
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val scp : CoroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var localVersionRepository : LocalVersionRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentVersion = BuildConfig.VERSION_NAME

        scp.launch {
            var previousVersionList = localVersionRepository.getVersionDetails()

            if (localVersionRepository.getVersionDetails().isEmpty() ) {
                val vers = UpdateVersion(0, "1.0")
                localVersionRepository.addVersionDetails(vers)
                Log.i("Version", "$currentVersion, $previousVersionList")
            }
            if(currentVersion !in previousVersionList){
                val vers = UpdateVersion(0, currentVersion)
                localVersionRepository.addVersionDetails(vers)
                Log.i("Version", "$currentVersion, $previousVersionList")
                withContext(Dispatchers.Main) {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("Update Dialogue")
                            .setMessage("App has been updated")
                            .setPositiveButton("Okay") { _, _ -> }
                            .create().show()

                }
                Log.i("Version", "$currentVersion, $previousVersionList")
            }
            else{
                Log.i("Version", "$currentVersion, $previousVersionList")

            }
//            else {
//
//                localVersionRepository.updateDetailsVersion(0, BuildConfig.VERSION_NAME)
//                Log.i("Version", "$currentVersion, $previousVersionList")
//
//
//                withContext(Dispatchers.Main) {
//                    if (currentVersion != previousVersion) {
//                        AlertDialog.Builder(this@MainActivity)
//                            .setTitle("Update Dialogue")
//                            .setMessage("App has been updated")
//                            .setPositiveButton("Okay") { _, _ -> }
//                            .create().show()
//
//                        val vers = UpdateVersion(0, currentVersion)
//
//                    }
//                }
//            }
        }

//        appUpdateManager = AppUpdateManagerFactory.create(this)
//        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
//
//        appUpdateInfoTask.addOnSuccessListener {
//
//            val listener: (InstallState) -> Unit = { state: InstallState ->
//                if(state.installStatus() == InstallStatus.DOWNLOADING) {
//                    val bytesDownloaded = state.bytesDownloaded()
//                    val totalBytesDownloaded = state.totalBytesToDownload()
//
//
//                    val dialog = AlertDialog.Builder(this)
//                        .setMessage("Creating Workspace")
//                        .setView(R.layout.progress_dialog_layout)
//                        .create()
//                    dialog.show()
//
//                }
//            }
//
//            appUpdateManager.registerListener(listener)
//            if(it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
////                appUpdateManager.startUpdateFlowForResult(
//    //                it, AppUpdateType.IMMEDIATE, this, MY_REQUEST_CODE)
//
//
//
//                appUpdateManager.startUpdateFlowForResult(
//                    it, this,
//                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
//                    .setAllowAssetPackDeletion(false)
//                    .build(),
//                    MY_REQUEST_CODE)
//            }
//            appUpdateManager.unregisterListener(listener)
//
//            popupSnackBarForCompleteUpdate()
//        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

    }

//    override fun onResume() {
//        super.onResume()
//
//        appUpdateManager
//            .appUpdateInfo
//            .addOnSuccessListener {
//                if(it.installStatus() == InstallStatus.DOWNLOADED) {
//                    popupSnackBarForCompleteUpdate()
//                }
//
//                if(it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
//                    appUpdateManager.startUpdateFlowForResult(
//                        it, AppUpdateType.IMMEDIATE, this, MY_REQUEST_CODE
//                    )
//                }
//            }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == MY_REQUEST_CODE) {
//            if(resultCode != RESULT_OK) {
//                Log.i("MY_APP","Update flow failed! Result code: $resultCode")
//
//            }
//        }
//    }


//    private fun popupSnackBarForCompleteUpdate() {
//        Snackbar.make(
//            binding.coordinatorLayout,
//            "An update has just been downloaded",
//            Snackbar.LENGTH_INDEFINITE
//        ).apply {
//            setAction("RESTART") {
//                appUpdateManager.completeUpdate()
//            }
//            setActionTextColor(resources.getColor(R.color.black))
//            show()
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}