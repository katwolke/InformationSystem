package interfaces;

import java.util.Collection;

public interface RecordsList {
	public Collection<Record> getRecords();
	public Record getRecord(String recordTitle);
	public void setRecord(String recordTitle, Record newRecord);
	public void insertRecord(Record newRecord);
	public void removeRecord(Record record);
	public String getRecordsListName();
	public void setRecordsListName(String recordsSetName);
}
