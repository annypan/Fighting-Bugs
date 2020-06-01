import java.io.*;
import java.util.*;

public class RecordIterator implements Iterator<String> {
	
	BufferedReader reader;
	String curr = null;
	
	public RecordIterator(String filePath) {
		if (filePath == null) throw new IllegalArgumentException();
		try {
			reader = new BufferedReader(new FileReader(filePath));
			curr = reader.readLine();
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean hasNext() {
		if (curr != null && !curr.equals("")) return true;
		else {
			try {
				reader.close();
			} catch (IOException e) {
				throw new IllegalArgumentException();
			}
		}
		return false;
	}

	@Override
	public String next() {
		if (hasNext() == false) throw new NoSuchElementException();
		else {
			try {
				String res = curr;
				curr = reader.readLine();
				return res;
			} catch (IOException e) {
				throw new IllegalArgumentException();
			}
		}
	}
}
