package com.fileparser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CsvFileRead implements CsvFile
{
    public final static char SEPARATOR = ',';

    private File file;
    private List<String> lines;
    private List<String[] > data;

   public CsvFileRead(File file) {
        this.file = file;

        // Init
        init();
    }

    private ArrayList<String[]> init() {
        lines = CsvFileHelper.readFile(file);

        data = new ArrayList<String[]>(lines.size());
        String sep = new Character(SEPARATOR).toString();
        for (String line : lines) {
            String[] oneData = line.split(sep);
            data.add(oneData);
        }
        return (ArrayList<String[]>) data;
    }

	@Override
	public File getFile()
	{
		// TODO Auto-generated method stub
		return this.file;
	}

	@Override
	public List<String[]> getData()
	{
		// TODO Auto-generated method stub
		return this.data;
	}

    // GETTERS ...
    }