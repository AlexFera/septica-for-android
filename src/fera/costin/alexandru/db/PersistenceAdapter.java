package fera.costin.alexandru.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fera.costin.alexandru.logic.Game;

import android.content.Context;
import android.util.Log;

/**
 * 
 * @author Alexandru Fera
 *
 */
public class PersistenceAdapter
{
	public static final String TAG = "septica";
	public static final String FILE_NAME = "septicaSaveFile";

	private Context appContext;

	public PersistenceAdapter(Context c)
	{
		super();
		appContext = c;
	}

	public Object getSavedGameData()
	{
		try
		{
			FileInputStream fis = appContext.openFileInput(FILE_NAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object o = ois.readObject();
			return o;
		} catch (Exception ex)
		{
			Log.d(TAG, ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	public void setSavedGameData(Game gd)
	{
		try
		{
			FileOutputStream fos = appContext.openFileOutput(FILE_NAME,
					Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(gd);
			oos.flush(); // Ensure all the information was written.
			oos.close();
		} catch (Exception ex)
		{
			Log.d(TAG, ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static boolean isSavedGameDataValid(Context c)
	{
		boolean retVal = true;
		try
		{
			c.openFileInput(FILE_NAME);
		} catch (FileNotFoundException e)
		{
			retVal = false;
		}
		return retVal;
	}

	public static void deleteSavedGameData(Context c)
	{
		c.deleteFile(FILE_NAME);
	}

}
