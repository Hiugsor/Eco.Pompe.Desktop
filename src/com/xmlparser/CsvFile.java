package com.xmlparser;
import java.io.File;
import java.util.List;

public interface CsvFile
{
	 File getFile();

	    List<String[] > getData();
}
