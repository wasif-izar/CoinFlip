package com.example.coinflip

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    //other text , image views referencing using lateinit
    private lateinit var coinimage : ImageView

    private lateinit var totalCount : TextView
    private lateinit var headsCount : TextView
    private lateinit var tailsCount : TextView

    private lateinit var headsPercent : TextView
    private lateinit var tailsPercent : TextView

    private lateinit var headsProgress : ProgressBar
    private lateinit var tailsProgress : ProgressBar

    private lateinit var simNumber : EditText
    //count variables to keep track of heads, tails and flips
    private var heads = 0
    private var tails = 0
    private var total = 0

    private lateinit var simButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //referencing switch and button
        val simSwitch: SwitchCompat = findViewById(R.id.main_act_sw_simulate)
        val flipButton: Button = findViewById(R.id.main_act_bt_flip)
        val resetButton: Button = findViewById(R.id.main_act_bt_reset)

        simButton = findViewById(R.id.main_act_bt_simulate)

        //listeners for buttons
        simSwitch.setOnCheckedChangeListener { compoundButton, b -> enableSim(b) }
        flipButton.setOnClickListener { flip() }
        resetButton.setOnClickListener { reset() }
        simButton.setOnClickListener { sim() }

        //set values for other views
        coinimage = findViewById(R.id.main_act_iv_coin)

        totalCount = findViewById(R.id.main_act_tv_totalflip)
        headsCount = findViewById(R.id.main_act_tv_total_heads)
        tailsCount = findViewById(R.id.main_act_tv_total_tails)

        headsPercent = findViewById(R.id.main_act_tv_heads_per)
        tailsPercent = findViewById(R.id.main_act_tv_tailss_per)

        headsProgress = findViewById(R.id.main_act_pb_heads)
        tailsProgress = findViewById(R.id.main_act_pb_tails)

        simNumber = findViewById(R.id.main_act_et_sim_no)

    }
    //turn on or off simulation mode sim view
    private fun enableSim(onState : Boolean){
        //get state of state
        if (onState){
//            Log.i("test","on")
            simNumber.visibility = View.VISIBLE
            simButton.visibility = View.VISIBLE


        }else{
//            Log.i("test","off")
            simNumber.visibility = View.INVISIBLE
            simButton.visibility = View.INVISIBLE
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
        if(coinValue == "heads"){
            heads++
            coinimage.setImageResource(R.drawable.ic_heads)
        }
        else{
            tails++
            coinimage.setImageResource(R.drawable.ic_tails)
        }
        //increment total flips
        total++
        //update text views to show results
        totalCount.text = "Total Flips: $total"
        tailsCount.text = "Total Tails: $tails"
        headsCount.text = "Total Heads: $heads"

        updateStatistics()
    }
    private fun updateStatistics(){
        var headsPercentResult = 0.0
        var tailsPercentResult = 0.0
        if(total!=0) {
            headsPercentResult = round((heads.toDouble() / total) * 10000)/100
            tailsPercentResult = round((tails.toDouble() / total) * 10000)/100
        }

        headsPercent.text = "Heads: $headsPercentResult %"
        tailsPercent.text = "Tails: $tailsPercentResult %"

        headsProgress.progress = headsPercentResult.toInt()
        tailsProgress.progress = tailsPercentResult.toInt()
    }

    //reset all the data
    private fun reset(){
        coinimage.setImageResource(R.drawable.ic_thumbs)
        //set all totals to zero
        total = 0
        heads = 0
        tails = 0
        totalCount.text = "Total Flips: $total"
        tailsCount.text = "Total Tails: $tails"
        headsCount.text = "Total Heads: $heads"

        //update all stats to zero
        updateStatistics()

    }

    //run coin simulation for number of input
    private fun sim(){
        var numberToSim = 0
        if(simNumber.text.toString().toInt() <= 100000) {
            //get number and clear edit text
            if (simNumber.text.toString() != "") {

                numberToSim = simNumber.text.toString().toInt()


            }
            simNumber.setText("")

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
        imm.hideSoftInputFromWindow(coinimage.windowToken,0)
    }
}