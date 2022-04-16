package com.dkkim.anew.Activity

import android.Manifest
import android.R
import android.app.Activity
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED
import android.bluetooth.BluetoothDevice
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.dkkim.anew.Fragment.SettingFragment
import com.dkkim.anew.databinding.ActivitySelectDeviceBinding
import kotlinx.android.synthetic.main.activity_select_device.*


class SelectDeviceActivity : AppCompatActivity() {
    lateinit var binding: ActivitySelectDeviceBinding
    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1

    companion object {
        val EXTRA_ADDRESS: String = "Device_address"
    }

    private var deviceList : ArrayList<BluetoothDevice> = ArrayList()

    var mBluetoothStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action //입력된 action
            Toast.makeText(context, "받은 액션 : $action", Toast.LENGTH_SHORT).show()
            Log.d("Bluetooth action", action!!)
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            var name: String? = null
            if (device != null) {
                name = device.name //broadcast를 보낸 기기의 이름을 가져온다.
            }
            when (action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    val state =
                        intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                    when (state) {
                        BluetoothAdapter.STATE_OFF -> {}
                        BluetoothAdapter.STATE_TURNING_OFF -> {}
                        BluetoothAdapter.STATE_ON -> {}
                        BluetoothAdapter.STATE_TURNING_ON -> {}
                    }
                }
                BluetoothDevice.ACTION_ACL_CONNECTED -> {}
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {}
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {}
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {}
                BluetoothDevice.ACTION_FOUND -> {
                    var device_name = device!!.name
                    var device_Address = device.address
                    if(device_name == null){
                        device_name = "null"
                    }
                    //본 함수는 블루투스 기기 이름의 앞글자가 "GSM"으로 시작하는 기기만을 검색하는 코드이다
                    Log.d("Bluetooth Name: ", device_name)
                    Log.d("Bluetooth Mac Address: ", device_Address)
                    deviceList.add(device)
                }
                ACTION_DISCOVERY_FINISHED -> {
                    Log.d("Bluetooth", "Call Discovery finished")
                    StartBluetoothDeviceConnection() //원하는 기기에 연결
                }
                BluetoothDevice.ACTION_PAIRING_REQUEST -> {}
            }
        }
    }

    fun StartBluetoothDeviceConnection() {
        //Bluetooth connection start
        if (deviceList.size === 0) {
            Toast.makeText(this, "There is no device", Toast.LENGTH_SHORT).show()
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("블루투스 연결 시작 ")

        // 페어링 된 블루투스 장치의 이름 목록 작성
        val listItems: MutableList<String> = ArrayList()
        var count = 0
        for (bt_device in deviceList) {
            if(bt_device.name == null){
                listItems.add("null"+ count)
                count++
            }else{
                listItems.add(bt_device.name)
            }
        }
        listItems.add("Cancel") // 취소 항목 추가
        val items = listItems.toTypedArray<CharSequence>()
        builder.setItems(items, DialogInterface.OnClickListener { dialog, which ->
            val dialog_: Dialog = dialog as Dialog
            if (which == deviceList.size) {
                // 연결할 장치를 선택하지 않고 '취소'를 누른 경우

            } else {
                val address: String = deviceList[which].address
                val intent = Intent(this, ControlActivity::class.java)
                intent.putExtra(EXTRA_ADDRESS, address)
                startActivity(intent)
            }
        })
        builder.setCancelable(false) // 뒤로 가기 버튼 사용 금지
        val alert: AlertDialog = builder.create()
        alert.show()
        Log.d("Bluetooth Connect", "alert start")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setContentView(R.layout.fragment_setting_bluetooth)

        binding.backBtn.setOnClickListener {
            finish()
            //뒤로가기 버튼 클릭시 세팅 화면으로
        }

        val stateFilter = IntentFilter()
        stateFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED) //BluetoothAdapter.ACTION_STATE_CHANGED : 블루투스 상태변화 액션
        stateFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
        stateFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED) //연결 확인
        stateFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED) //연결 끊김 확인
        stateFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        stateFilter.addAction(BluetoothDevice.ACTION_FOUND) //기기 검색됨
        stateFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED) //기기 검색 시작
        stateFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED) //기기 검색 종료
        stateFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
        registerReceiver(mBluetoothStateReceiver, stateFilter)


        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(m_bluetoothAdapter == null) {
            //toast("this device doesn't support bluetooth")
            Toast.makeText(this, "이 기기는 블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show()
            return
        }
        if(!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }
        m_bluetoothAdapter!!.startDiscovery() //블루투스 기기 검색 시작
        select_device_refresh.setOnClickListener{
            deviceList.clear()
            m_bluetoothAdapter!!.startDiscovery()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun pairedDeviceList() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        var list : ArrayList<BluetoothDevice> = ArrayList()

        if(!m_pairedDevices.isEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                list.add(device)
                Log.i("device",""+device)
            }
        } else {
            Toast.makeText(this, "페어링 기기가 없습니다.", Toast.LENGTH_LONG).show()
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        select_device_list.adapter = adapter
        select_device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _,position, _ ->
            val device: BluetoothDevice = list[position]
            val address: String = device.address

            val intent = Intent(this, ControlActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS, address)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (m_bluetoothAdapter!!.isEnabled) {
                    //toast("Bluetooth has been enabled")
                    Toast.makeText(this, "블루투스가 활성화 되었습니다.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Bluetooth has been disabled", Toast.LENGTH_LONG).show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "블루투스 활성화가 거절 되었습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

}
