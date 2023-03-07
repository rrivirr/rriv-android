package org.rriv.android.attachDevice

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.felhr.usbserial.UsbSerialDevice
import com.felhr.usbserial.UsbSerialInterface
import org.rriv.android.R
import org.rriv.android.databinding.FragmentAttachDeviceScreenBinding

class AttachDeviceScreenFragment : Fragment() {

    companion object {
        fun newInstance() = AttachDeviceScreenFragment()
    }

    lateinit var usbManager: UsbManager
    var device: UsbDevice? = null
    var usbSerialDevice: UsbSerialDevice? = null
    var connection: UsbDeviceConnection? = null
    var ACTION_USB_PERMISSION = "permission"
    var usbStatus: String? = null
    private lateinit var viewModel: AttachDeviceScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAttachDeviceScreenBinding = FragmentAttachDeviceScreenBinding.inflate(inflater,container,false)
        usbManager = activity?.getSystemService(Context.USB_SERVICE) as UsbManager
        val filter = IntentFilter()
        filter.addAction(ACTION_USB_PERMISSION)
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED)
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        requireActivity().registerReceiver(broadcastReceiver,filter)
//        val checkUsbStatus: Button = container!!.findViewById(R.id.buttonMonitor)
//        val statusTextView: TextView = container.findViewById(R.id.textViewStaus)
//        checkUsbStatus.setOnClickListener {
//            statusTextView.text = usbStatus.toString()
//        }
        binding.buttonMonitor.setOnClickListener {
            binding.textViewStaus.text = usbStatus.toString()
        }
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AttachDeviceScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private fun startUsbConnection(){
        val usbDevices: HashMap<String, UsbDevice>? = usbManager.deviceList
        if (usbDevices?.isNotEmpty()!!){
            var keep = true
            usbDevices.forEach{ entry ->
                device = entry.value
                val deviceVendorId: Int? = device?.vendorId
                //Replace below with rriv devicevendorId value
                if (deviceVendorId == 1027){
                    val intent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION),
                            PendingIntent.FLAG_MUTABLE
                        )
                    } else {
                        PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION),
                            0)
                    }
                    usbManager.requestPermission(device,intent)
                    keep = false
                } else {
                    connection = null
                    device = null
                }
                if(!keep){
                    return
                }

            }
        }

    }
    private fun disconnect(){
        usbSerialDevice!!.close()
    }
    private fun sendData(message: String){
        usbSerialDevice!!.write(message.toByteArray())

    }
    private val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            usbStatus = intent?.action
            if (intent?.action!! == ACTION_USB_PERMISSION){
                val granted: Boolean = intent.extras!!.getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED)
                if(granted){
                    connection = usbManager.openDevice(device)
                    usbSerialDevice = UsbSerialDevice.createUsbSerialDevice(device, connection)
                    if(usbSerialDevice != null && usbSerialDevice!!.isOpen){
                        usbSerialDevice!!.setBaudRate(9600)
                        usbSerialDevice!!.setDataBits(UsbSerialInterface.DATA_BITS_8)
                        usbSerialDevice!!.setStopBits(UsbSerialInterface.STOP_BITS_1)
                        usbSerialDevice!!.setParity(UsbSerialInterface.PARITY_NONE)
                        usbSerialDevice!!.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF)
//                        usbSerialDevice!!.read(UsbSerialInterface.UsbReadCallback {  })

                    } else {
                        Log.i("Output","serial is null or not open")
                    }
                } else {
                    Log.i("Output","serial is null or not open")
                }
            } else if (intent?.action == UsbManager.ACTION_USB_DEVICE_ATTACHED){
                startUsbConnection()
            } else if (intent?.action == UsbManager.ACTION_USB_DEVICE_DETACHED){
                disconnect()
            }
            usbStatus = intent?.action

        }
    }

}