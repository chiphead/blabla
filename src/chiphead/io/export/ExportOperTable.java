package chiphead.io.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.Region;

import chiphead.model.DisplayOperation;
import chiphead.model.DisplayProject;
import chiphead.model.ExportOperation;

public class ExportOperTable extends ExportExcel<ExportOperation> implements ExportBase<ExportOperation> {
	
	public void exportExcel(String title,Collection<ExportOperation> dataset, OutputStream out) {

		int widthBase = 256;
		int[] widths = {
			10 * widthBase, 14 * widthBase, 16 * widthBase, 42 * widthBase, 42 * widthBase, 10 * widthBase,
			16 * widthBase, 16 * widthBase,/* 16 * widthBase,*/ 16 * widthBase, 24 * widthBase, 32 * widthBase, 
			42 * widthBase, 42 * widthBase
		};
		
		// 生成一个Sheet
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth(10);

		int index = 0;
		// 添加一行表头
										

		HSSFRow row = sheet.createRow(index);
		String[] headers = { "审批状态","登记时间", "类型", "事件时间", "事件内容", "事件等级", "运维人员", "所属系统",
				/*"所属模块",*/ "所属人员", "影响业务", "问题分析", "改进建议", "备注"};
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(styles[0]);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		// 从index行以下开始添加数据
		super.addFromClass(dataset, sheet, index);		
		super.setColWidths(sheet, widths);
		
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HSSFCell mergeRegion(HSSFSheet sheet, List<String> list, int rowId,
			int colId) {
		HSSFCell cell = sheet.getRow(rowId).getCell(colId);
		cell.setCellValue(list.get(colId));
		return cell;
	}
	
	public void setCellStyleByCondition(HSSFCell cell, int colId) {
		cell.setCellStyle(styles[12]);
	}
}
