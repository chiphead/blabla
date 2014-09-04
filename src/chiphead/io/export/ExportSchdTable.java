package chiphead.io.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import chiphead.config.Constants;
import chiphead.model.ComputeWeekNum;

public class ExportSchdTable extends ExportExcel<List<String>> implements
		ExportBase<List<String>> {

	public void exportExcel(String title, Collection<List<String>> dataset,
			OutputStream out) {

		int weekNum = ComputeWeekNum.getWeekNumOfCurYear();
		int[] widths = new int[5 + weekNum];
		int widthBase = 256;

		widths[0] = 8 * widthBase;
		widths[1] = 8 * widthBase;
		widths[2] = 8 * widthBase;
		widths[3] = 10 * widthBase;
		widths[4] = 32 * widthBase;
		for (int i = 5; i < widths.length; i++) {
			widths[i] = 12 * widthBase;
		}

		// 生成一个Sheet
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth(10);

		HSSFRow row = sheet.createRow(0);
		String headerString = "人员|状态|类型|项目编号|项目名称";
		String headerString2 = ComputeWeekNum
				.getWholeYearTableHead(headerString + "|");
		String[] headers = headerString2.split("\\|");
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
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
		exportExcel(Constants.jichuSheetName, jichuDataset, out);
		exportExcel(Constants.yewuSheetName, yewuDataset, out);
		exportExcel(Constants.yingyongSheetName, yingyongDataset, out);
		//exportExcel(Constants.otherSheetName, otherDataset,out);
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
		String cellValue = cell.getStringCellValue();

		cell.setCellStyle(styles[13]);// 无色
		if (colId <= 5) {
			cell.setCellStyle(styles[12]);// 换行
			return;
		}
		// 解析颜色码
		else if (cellValue.contains("/") && cellValue.contains("[")) {// 阶段和时间数据
			if (cellValue.startsWith("[BGCOLOR=#999999]")) {// 灰色
				cell.setCellStyle(styles[9]);
			} else if (cellValue.startsWith("[BGCOLOR=#b94a48]")) {// 红色
				cell.setCellStyle(styles[11]);
			} else if (cellValue.startsWith("[BGCOLOR=#FFC500]")) {// 黄色
				cell.setCellStyle(styles[8]);
			} else if (cellValue.startsWith("[BGCOLOR=#22B14C]")) {// 绿色
				cell.setCellStyle(styles[10]);
			}
			cell.setCellValue(cellValue.substring(17));// 去除颜色码
		}
	}
}
