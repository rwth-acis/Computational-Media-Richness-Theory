package DataLoader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer {
	/**
	 * Serialize data to JSON string.
	 * 
	 * @return
	 */
	public static String serialize(Object obj) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(obj);
		return jsonOutput;
	}

	public static Object deserialize(String path, Type listType) {
		// "c:/test.txt"
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		Object o = null;

		try {
			// open input stream test.txt for reading purpose.
			is = new FileInputStream(path);

			// create new input stream reader
			isr = new InputStreamReader(is);

			// create new buffered reader
			br = new BufferedReader(isr);

			Gson gson = new Gson();
			o = gson.fromJson(br, listType);

		} catch (Exception e) {
			o = null;
			e.printStackTrace();
		} finally {

			// releases resources associated with the streams
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (isr != null)
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		if (o != null) {
			return o;
		}

		return null;
	}
}
