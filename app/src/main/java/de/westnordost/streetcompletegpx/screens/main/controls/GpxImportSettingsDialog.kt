package de.westnordost.streetcompletegpx.screens.main.controls

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.slider.LabelFormatter
import de.westnordost.streetcompletegpx.R
import de.westnordost.streetcompletegpx.data.import.GpxImporter
import de.westnordost.streetcompletegpx.data.meta.CountryInfos
import de.westnordost.streetcompletegpx.data.meta.LengthUnit
import de.westnordost.streetcompletegpx.databinding.DialogGpxImportSettingsBinding
import de.westnordost.streetcompletegpx.util.getSelectedLocale
import de.westnordost.streetcompletegpx.util.ktx.viewLifecycleScope
import de.westnordost.streetcompletegpx.util.viewBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.io.InputStream

/** A dialog to specify GPX import settings */
class GpxImportSettingsDialog(
    private val inputStream: InputStream,
    private val callback: (result: Result<GpxImporter.GpxImportData>) -> Unit,
) : DialogFragment(R.layout.dialog_gpx_import_settings) {
    private val gpxImporter: GpxImporter by inject()
    private val binding by viewBinding(DialogGpxImportSettingsBinding::bind)
    private var worker: Deferred<Result<GpxImporter.GpxImportData>>? = null

    private val countryInfos: CountryInfos by inject()
    private var lengthUnit = LengthUnit.METER

    private val minDownloadDistanceOptions: List<Double> = listOf(10.0, 100.0, 250.0, 500.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Theme_DialogFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { getSelectedLocale(it) }?.country?.let { countryInfos.get(listOf(it)) }?.lengthUnits?.first()
            ?.let { lengthUnit = it }

        binding.minDownloadDistanceSlider.setLabelFormatter {
            formatMinDownloadDistance(it.toInt())
        }
        binding.minDownloadDistanceSlider.addOnChangeListener { _, value, _ ->
            updateDownloadCheckboxLabel(value.toInt())
        }
        binding.minDownloadDistanceSlider.value = INITIAL_MIN_DOWNLOAD_DISTANCE_INDEX.toFloat()
        updateDownloadCheckboxLabel(INITIAL_MIN_DOWNLOAD_DISTANCE_INDEX)

        binding.downloadCheckBox.setOnClickListener {
            if (binding.downloadCheckBox.isChecked) {
                binding.minDownloadDistanceSlider.visibility = View.VISIBLE
                binding.minDownloadDistanceSlider.labelBehavior = LabelFormatter.LABEL_VISIBLE
            } else {
                binding.minDownloadDistanceSlider.visibility = View.GONE
                binding.minDownloadDistanceSlider.labelBehavior = LabelFormatter.LABEL_GONE
            }
            updateOkButtonState()
        }

        binding.displayTrackCheckBox.setOnClickListener {
            updateOkButtonState()
        }
        binding.okButton.setOnClickListener {
            viewLifecycleScope.launch {
                callback(processGpxFile())
            }
        }
        binding.cancelButton.setOnClickListener {
            worker?.cancel()
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        worker?.cancel()
    }

    private fun updateDownloadCheckboxLabel(index: Int) {
        binding.downloadCheckBox.text =
            getString(
                R.string.gpx_import_download_along_track,
                formatMinDownloadDistance(index)
            )
    }

    private fun updateOkButtonState() {
        binding.okButton.isEnabled =
            binding.displayTrackCheckBox.isChecked || binding.downloadCheckBox.isChecked
    }

    private fun formatMinDownloadDistance(index: Int): String {
        val minDownloadDistance = minDownloadDistanceOptions[index].toInt()
        return when (lengthUnit) {
            LengthUnit.FOOT_AND_INCH -> "${minDownloadDistance}yd"
            else -> "${minDownloadDistance}m"
        }
    }

    private fun minDownloadDistanceInMeters(): Double {
        val minDownloadDistance = minDownloadDistanceOptions[binding.minDownloadDistanceSlider.value.toInt()]
        return when (lengthUnit) {
            LengthUnit.FOOT_AND_INCH -> minDownloadDistance * YARDS_IN_METER
            else -> minDownloadDistance
        }
    }

    private suspend fun processGpxFile(): Result<GpxImporter.GpxImportData> {
        binding.okButton.isEnabled = false

        worker = viewLifecycleScope.async {
            return@async gpxImporter.processGpxFile(
                inputStream,
                binding.displayTrackCheckBox.isChecked,
                binding.downloadCheckBox.isChecked,
                minDownloadDistanceInMeters()
            ) { p -> withContext(Dispatchers.Main) { binding.importProgress.progress = p } }
        }
        val importData = worker!!.await()

        worker = null

        dismiss()
        return importData
    }

    companion object {
        private const val INITIAL_MIN_DOWNLOAD_DISTANCE_INDEX = 1
        private const val YARDS_IN_METER = 0.9144
    }
}
