package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment


class GameViewModel : ViewModel(){

    companion object{
        //These represents different important times in the game, such as game length.

        //This is when the game is over
        private const val DONE = 0L

        //This is the number of milliseconds in a second
        private val ONE_SECOND = 1000L

        //This is the total time of the game
        private var COUNTDOWN_TIME = 10000L

    }

    private val timer: CountDownTimer



    // The current word
      private val _word = MutableLiveData<String>()
        val word : LiveData<String>
            get() = _word

    // The current score
     private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    //The current state of game
    private var _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
     get() = _eventGameFinish

    private  var _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created!")

        _eventGameFinish.value = false
        resetList()
        nextWord()
        _score.value = 0

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(millisUntilFinished: Long) {
               COUNTDOWN_TIME = COUNTDOWN_TIME - ONE_SECOND
                _currentTime.value = (millisUntilFinished/ ONE_SECOND)
                Log.i("GameViewModel", "One second passed")
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }


        }

        timer.start()


    }
    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
       // wordList.shuffle()
    }



    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
            _word.value = wordList.removeAt(0)

    }


     fun onSkip() {
//when you create livedata the value of score is null so to deal with that we use a null track and
//use a minus method instead of a straight minus operator
         //score.value = score.value - 1
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

     fun onCorrect() {
         _score.value = (score.value)?.plus(1)
        nextWord()
    }



    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel Destroyed!")
        timer.cancel()
    }
}