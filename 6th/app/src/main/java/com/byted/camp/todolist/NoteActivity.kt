package com.byted.camp.todolist

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.byted.camp.todolist.db.TodoDbHelper
import com.byted.camp.todolist.beans.Note
import com.byted.camp.todolist.db.TodoContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class NoteActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var addBtn: Button? = null
    private var todoDbHelper: TodoDbHelper? = null
    private var radioGroup: RadioGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        todoDbHelper = TodoDbHelper(this)
        setTitle(R.string.take_a_note)
        editText = findViewById(R.id.edit_text)
        editText?.isFocusable = true
        editText?.requestFocus()
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager?.showSoftInput(editText, 0)
        addBtn = findViewById(R.id.btn_add)
        addBtn?.setOnClickListener(View.OnClickListener {
            val content: CharSequence = editText!!.text
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(this@NoteActivity,
                        "No content to add", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            GlobalScope.launch(Dispatchers.Main) {
                val succeed = withContext(Dispatchers.IO) { saveNote2Database(content.toString().trim { it <= ' ' }) }
                if (succeed) {
                    Toast.makeText(this@NoteActivity,
                            "Note added", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                } else {
                    Toast.makeText(this@NoteActivity,
                            "Error", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        })
        radioGroup = findViewById(R.id.radioGroup)
        radioGroup?.setOnCheckedChangeListener { _, _ ->
            addBtn?.isEnabled = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun saveNote2Database(content: String): Boolean {
        // TODO 插入一条新数据，返回是否插入成功
        val toInsert = ContentValues()
        toInsert.put(TodoContract.TodoEntry.COLUMN_NAME_CONTENT, content)
        val priority = when (radioGroup?.checkedRadioButtonId) {
            R.id.radioHighButton -> Note.Priority.HIGHER
            R.id.radioLowButton -> Note.Priority.LOWER
            R.id.radioNormalButton -> Note.Priority.NORMAL
            else -> Note.Priority.NORMAL
        }
        toInsert.put(TodoContract.TodoEntry.COLUMN_NAME_PRIORITY, priority.ordinal)
        return try {
            val rowId: Long = todoDbHelper?.writableDatabase?.insertOrThrow(TodoContract.TodoEntry.TABLE_NAME, null, toInsert)!!
            rowId > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}