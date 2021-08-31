package com.example.coinflip

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.coinflip.databinding.ActivityMainBinding
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    var coinStatus = "Waiting coin to Flip"

    //other text , image views referencing using lateinit
//    private lateinit var coinimage : ImageView

//    private lateinit var totalCount : TextView
//    private lateinit var headsCount : TextView
//    private lateinit var tailsCount : TextView

//    private lateinit var headsPercent : TextView
//    private lateinit var tailsPercent : TextView

//    private lateinit var headsProgress : ProgressBar
//    private lateinit var tailsProgress : ProgressBar

//    private lateinit var simNumber : EditText
    //count variables to keep track of heads, tails and flips
    private var heads = 0
    private var tails = 0
    private var total = 0

//    private lateinit var simButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.myActivity = this

        //referencing switch and button
//        val simSwitch: SwitchCompat = findViewById(R.id.main_act_sw_simulate)
//        val flipButton: Button = findViewById(R.id.main_act_bt_flip)
//        val resetButton: Button = findViewById(R.id.main_act_bt_reset)

//        simButton = findViewById(R.id.main_act_bt_simulate)

        //listeners for buttons
        binding.mainActSwSimulate.setOnCheckedChangeListener { compoundButton, b -> enableSim(b) }
        binding.mainActBtFlip.setOnClickListener { flip() }
        binding.mainActBtReset.setOnClickListener { reset() }
        binding.mainActBtSimulate.setOnClickListener { sim() }

        //set values for other views
//        coinimage = findViewById(R.id.main_act_iv_coin)

//        totalCount = findViewById(R.id.main_act_tv_totalflip)
//        headsCount = findViewById(R.id.main_act_tv_total_heads)
//        tailsCount = findViewById(R.id.main_act_tv_total_tails)

//        headsPercent = findViewById(R.id.main_act_tv_heads_per)
//        tailsPercent = findViewById(R.id.main_act_tv_tailss_per)

//        headsProgress = findViewById(R.id.main_act_pb_heads)
//        tailsProgress = findViewById(R.id.main_act_pb_tails)
//
//        simNumber = findViewById(R.id.main_act_et_sim_no)

    }
    //turn on or off simulation mode sim view
    private fun enableSim(onState : Boolean){
        //get state of state
        if (onState){
//            Log.i("test","on")
            binding.mainActEtSimNo.visibility = View.VISIBLE
            binding.mainActBtSimulate.visibility = View.VISIBLE


        }else{
//            Log.i("test","off")
            binding.mainActEtSimNo.visibility = View.INVISIBLE
            binding.mainActBtSimulate.visibility = View.INVISIBLE
        }
    }

    //simulate a coin flip
    private fun flip(){
        val randomNumber = (0..1).random()
        //update based on value
        if (randomNumber == 0){
            update("heads")
        }else{
            update("tails")
        }
    }
    //update function
    private fun update(coinValue : String){
        //setting the correct image for coin flip
        coinStatus = if(coinValue == "heads"){
            heads++
            binding.mainActIvCoin.setImageResource(R.drawable.ic_heads)
            "You got Heads"
        } else{
            tails++
            binding.mainActIvCoin.setImageResource(R.drawable.ic_tails)
            "You got Tails"
        }
        binding.apply { invalidateAll() }
        //increment total flips
        total++
        //update text views to show results
        val totFlips = "Total Flips: $total"
        val totHead = "Total Heads: $heads"
        val totTail = "Total Tails: $tails"
        binding.mainActTvTotalflip.text = totFlips
        binding.mainActTvTotalTails.text = totTail
        binding.mainActTvTotalHeads.text = totHead

        updateStatistics()
    }
    private fun updateStatistics(){
        var headsPercentResult = 0.0
        var tailsPercentResult = 0.0
        if(total!=0) {
            headsPercentResult = round((heads.toDouble() / total) * 10000)/100
            tailsPercentResult = round((tails.toDouble() / total) * 10000)/100
        }
        val headP = "Heads: $headsPercentResult %"
        val tailP = "Tails: $tailsPercentResult %"
        binding.mainActTvHeadsPer.text = headP
        binding.mainActTvTailssPer.text = tailP

        binding.mainActPbHeads.progress = headsPercentResult.toInt()
        binding.mainActPbTails.progress = tailsPercentResult.toInt()
    }

    //reset all the data
    private fun reset(){
        binding.mainActIvCoin.setImageResource(R.drawable.ic_thumbs)
        coinStatus = "Waiting coin to Flip"
        binding.apply { invalidateAll() }
        //set all totals to zero
        total = 0
        heads = 0
        tails = 0
        val tF = "Total Flips: $total"
        val tT = "Total Tails: $tails"
        val tH = "Total Heads: $heads"
        binding.mainActTvTotalflip.text = tF
        binding.mainActTvTotalTails.text = tT
        binding.mainActTvTotalHeads.text = tH

        //update all stats to zero
        updateStatistics()

    }

    //run coin simulation for number of input
    private fun sim(){
        var numberToSim = 0
        if(binding.mainActEtSimNo.text.toString().toInt() <= 100000) {
            //get number and clear edit text
            if (binding.mainActEtSimNo.text.toString() != "") {

                numberToSim = binding.mainActEtSimNo.text.toString().toInt()


            }
            binding.mainActEtSimNo.setText("")

            //run the given number of flips
            for (i in 1..numberToSim) {
                flip()
            }

        }
        else {
            Toast.makeText(applicationContext, "Number is toooo big", Toast.LENGTH_SHORT).show()
        }
        hideKeyboard()
    }
    private fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.mainActIvCoin.windowToken,0)
    }
}