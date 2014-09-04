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

import chiphead.config.Constants;
import chiphead.model.ComputeWeekNum;

public class ExportProjSchdTable extends ExportExcel<List<String>> implements ExportBase<List<String>> {

	public void exportExcel(String title, Collection<List<String>> dataset,OutputStream out) {
		int widthBase = 256;
		int weekNum = ComputeWeekNum.getWeekNumOfCurYear();
		int[] widths = new int[6 + weekNum];
		// 定义列宽
		widths[0] = 10 * widthBase;
		widths[1] = 42 * widthBase;
		widths[2] = 12 * widthBase;
		widths[3] = 10 * widthBase;
		widths[4] = 10 * widthBase;
		for (int i = 5; i < widths.length; i++) {
			widths[i] = 18 * widthBase;
		}

		// 生成一个Sheet
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth(10);

		HSSFRow row = sheet.createRow(0);
		String headerString = "项目编号|项目名称|业务负责人|开发负责人|当前阶段";
		String headerString2 = ComputeWeekNum
				.getWholeYearTableHead(headerString + "|");
		String[] headers = headerString2.split("\\|");
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			row.createCell(i);
			cell.setCellStyle(styles[0]);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		super.addFromList(dataset, sheet, 0);
		super.setColWidths(sheet, widths);
	}

	
	public void exportExcel(Collection<List<String>> jichuDataset,Collection<List<String>> yewuDataset,
							Collection<List<String>> yingyongDataset,
							OutputStream out) {
		exportExcel(Constants.jichuSheetName, jichuDataset,out);
		exportExcel(Constants.yewuSheetName, yewuDataset,out);
		exportExcel(Constants.yingyongSheetName, yingyongDataset,out);
		//exportExcel(Constants.otherSheetName, otherDataset,out);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HSSFCell mergeRegion(HSSFSheet sheet, List<String> list, int rowId,
			int colId) {
		// 合并单元格
		int start = colId;
		String cellValue = list.get(colId);
		HSSFRow row = sheet.getRow(rowId);
		HSSFCell mergedCell = row.getCell(start);
		if (!cellValue.equals("")) {
			while (colId < (list.size() - 1)
					&& cellValue.equals(list.get(colId + 1))) {
				row.createCell(colId);
				colId++;
			}
			if (start != colId && colId > 5) {
				Region region = new Region(rowId, (short) start, rowId,
						(short) colId);
				sheet.addMergedRegion(region);
			} else if (start == colId) {

			}
			mergedCell.setCellValue(cellValue);
		}

		return mergedCell;
	}

	public void setCellStyleByCondition(HSSFCell cell, int colId) {
		String cellValue = cell.getStringCellValue();
		if (colId <= 5) { 						// 非排期项不设置颜色
			cell.setCellStyle(styles[12]);
		} else if (cellValue.contains("需求")) {
			cell.setCellStyle(styles[1]);
		} else if (cellValue.contains("设计")) {
			cell.setCellStyle(styles[2]);
		} else if (cellValue.contains("开发")) {
			cell.setCellStyle(styles[3]);
		} else if (cellValue.contains("UT")) {
			cell.setCellStyle(styles[4]);
		} else if (cellValue.contains("ST")) {
			cell.setCellStyle(styles[5]);
		} else if (cellValue.contains("UAT")) {
			cell.setCellStyle(styles[6]);
		} else if (cellValue.contains("上线")) {
			cell.setCellStyle(styles[7]);
		} else {
			cell.setCellStyle(styles[13]); // transparent
		}
	}

}
