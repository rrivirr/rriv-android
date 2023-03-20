package org.rriv.android.attachDevice

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialProber
import org.rriv.android.R
import org.rriv.android.databinding.FragmentAttachDeviceScreenBinding
import org.rriv.android.results.OutputScreenFragment
import org.rriv.android.utils.CustomProber


class AttachDeviceScreenFragment : Fragment() {
    internal class ListItem(var device: UsbDevice, var port: Int, driver: UsbSerialDriver?) {
        var driver: UsbSerialDriver?


        init {
            this.driver = driver
        }

    }

    private val listItems = ArrayList<ListItem>()
    private val listAdapter: ArrayAdapter<ListItem>? = null
    private val baudRate = 19200

    private lateinit var viewModel: AttachDeviceScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAttachDeviceScreenBinding = FragmentAttachDeviceScreenBinding.inflate(inflater,container,false)
        refresh()


        binding.buttonMonitor.setOnClickListener {
            findNavController().navigate(R.id.action_attachDeviceScreenFragment_to_outputScreenFragment)

          /*  refresh()
            if(listItems.isNotEmpty()){
                binding.textViewStaus.text = "USB accessory detected"
                binding.imageViewUsbStatus.setImageDrawable(
                    ResourcesCompat.getDrawable(requireActivity().resources,R.drawable.usb_connected, null)!!
                )
            } else {
                binding.textViewStaus.text = "No USB accessory detected"
                binding.imageViewUsbStatus.setImageDrawable(
                    ResourcesCompat.getDrawable(requireActivity().resources,R.drawable.usb_not_connected, null)!!
                        )
            }*/
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AttachDeviceScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun onDriverSelected() {
        val item = listItems[1] //TODO(Replace with dynamic selection for when there are multiple drivers attached)
        if (item.driver == null) {
            Toast.makeText(activity, "no driver", Toast.LENGTH_SHORT).show()
        } else {
            val args = Bundle()
            args.putInt("device", item.device.getDeviceId())
            args.putInt("port", item.port)
            args.putInt("baud", baudRate)
            val fragment: Fragment = OutputScreenFragment()
            fragment.arguments = args
        // //TODO(Replace with nav graph)    parentFragmentManager.beginTransaction().replace(R.id.fragment, fragment, "terminal").addToBackStack(null).commit()
        }
    }

    fun refresh() {
        val usbManager = activity?.getSystemService(Context.USB_SERVICE) as UsbManager
        val usbDefaultProber = UsbSerialProber.getDefaultProber()
        val usbCustomProber: UsbSerialProber? = CustomProber().getCustomProber()
        listItems.clear()
        for (device in usbManager.deviceList.values) {
            var driver = usbDefaultProber.probeDevice(device)
            if (driver == null && usbCustomProber != null) {
                    driver = usbCustomProber.probeDevice(device)
            }
            if (driver != null) {
                for (port in driver.ports.indices)
                    listItems.add(
                    ListItem(
                        device,
                        port,
                        driver
                    )
                //TODO()
                )
            } else {
                listItems.add(
                    ListItem(
                        device,
                        0,
                        null
                    )
                )
            }
        }
//        listAdapter!!.notifyDataSetChanged()
    }


}