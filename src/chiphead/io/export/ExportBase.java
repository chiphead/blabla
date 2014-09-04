package chiphead.io.export;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface ExportBase<T> {
	public abstract void exportExcel(String title, Collection<T> dataset, OutputStream out);
	public abstract HSSFCell mergeRegion(HSSFSheet sheet, List<String> list, int rowId, int colId);
	public abstract void setCellStyleByCondition(HSSFCell cell, int colId);
}
