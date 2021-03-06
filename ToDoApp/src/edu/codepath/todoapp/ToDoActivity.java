package edu.codepath.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/*********************************************************************
 * ToDo App: A single, simple activity with basic "to do" list features.<br/>
 * It has a ListView, EditText and an "Add" Button. The basic functional
 * features are.<<br/>
 * Load the to do item list from file (todo.txt) or create a new empty if no
 * such file.<br/>
 * You add new to item via "Add" Button event. <br/>
 * Delete the item from the list by long click on the item.<br/>
 * Save the todo list to "todo.txt" on add and delete.
 * 
 * 
 * @author Eric
 **********************************************************************/

public class ToDoActivity extends Activity
{
	private static String	     TAG	     = "ToDoActivity";
	/**
	 * Constant file name to write.
	 */
	private static String	     FILENAME	= "todo.txt";
	/**
	 * The to do items.
	 */
	private ArrayList<String>	  items;
	/**
	 * The ItemsAdapter for ListView.
	 */
	private ArrayAdapter<String>	itemsAdapter;
	/**
	 * The Activity's ListView to display the todo items.
	 */
	private ListView	           lvItems;
	/**
	 * EditText for entering the new item to add.
	 */
	private EditText	           etNewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		// Review the View Components and intialize the instance variables.
		lvItems = (ListView) findViewById(R.id.lvItems);
		this.etNewItem = (EditText) this.findViewById(R.id.etNewItem);
		// Read the data from file or create the new one.
		this.readItems();

		// this.populateItems(); // no longer needed
		// Create the adapter ListView of to do "items".
		this.itemsAdapter = new ArrayAdapter<String>(this.getBaseContext(),
		      android.R.layout.simple_list_item_1 // simple android build view.
		      // it just a textview template will be used to display.
		      , items);
		// Set the adapter.
		lvItems.setAdapter(this.itemsAdapter);
		// Initialize the listener to delete the item on long click.
		this.setupListViewListener();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
		return true;
	}

	/************
	 * centralize event handlers for various controls for easy maintenance.
	 * 
	 * @param v
	 */
	public void addToDoItem(View v)
	{
		this.itemsAdapter.add(this.etNewItem.getText().toString());
		this.etNewItem.setText(""); // clear text
		saveItems();
	} // handleEvents(..)

	// ===================== private methods ==================
	/**********
	 * NOT USED
	 */
	private void populateItems()
	{
		this.items = new ArrayList<String>();
		for (int i = 0; i < 3; i++)
			items.add("Item " + i);

	}

	private void setupListViewListener()
	{
		this.lvItems.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
			      int position, long id)
			{
				items.remove(position);
				saveItems();
				itemsAdapter.notifyDataSetChanged();
				return true;
			}
		});

	}

	/**
	 * Read the data from the file ("FILENAME") and initialize the items(todo
	 * list) instance variable. If not exist, create the new one.
	 */
	private void readItems()
	{
		File fileDir = this.getFilesDir();
		File file = new File(fileDir, FILENAME);
		try
		{
			this.items = new ArrayList<String>(FileUtils.readLines(file));
			Log.v(TAG, "items has " + items.size());
			Log.v(TAG, "file=" + file.getAbsolutePath());
		} catch (IOException e)
		{
			this.items = new ArrayList<String>();
		}

	}

	/**
	 * Save the data from the file ("FILENAMW").
	 */
	private void saveItems()
	{
		File fileDir = this.getFilesDir();
		File file = new File(fileDir, FILENAME);
		try
		{
			FileUtils.writeLines(file, items);
			Log.v(TAG, "items has " + items.size());
			Log.v(TAG, "file=" + file.getAbsolutePath());
		} catch (IOException e)
		{
			Log.e(TAG, "writeItems() failed", e);
		}
		// FileUtils.writeLines(file, this.items);

	}
} // ToDoActivity
