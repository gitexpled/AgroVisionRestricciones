package lib.data.json;

import java.util.ArrayList;

public class dataTable {
	
	private ArrayList<String[]> Data;
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
	
	public void init()
	{
		this.Data= new ArrayList<String[]>();
		
	}
	
	public void setDatas(ArrayList<String[]> value){
		
		this.Data=value;
	}
	
	public ArrayList<String[]> getData() {
		return Data;
	}

	public void setData(String[] value){
		
		this.Data.add(value);
	}
	
	
	public int getDraw() {
		return draw;
	}

	public void setDraw(int value) {
		this.draw = value;
	}
	
	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int value) {
		this.recordsTotal = value;
	}
	
	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int value) {
		this.recordsFiltered = value;
	}

}
