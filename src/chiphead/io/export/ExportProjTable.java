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
import chiphead.model.DisplayProject;

public class ExportProjTable extends ExportExcel<DisplayProject> implements ExportBase<DisplayProject> {
	
	public void exportExcel(String title, Collection<DisplayProject> dataset,
			OutputStream out) {

		int widthBase = 256;
		int[] widths = {
			12 * widthBase, 20 * widthBase, 12 * widthBase, 12 * widthBase, 42 * widthBase, 
			10 * widthBase, 10 * widthBase, 10 * widthBase, 10 * widthBase, 18 * widthBase, 
			18 * widthBase, 18 * widthBase, 12 * widthBase, 14 * widthBase, 14 * widthBase, 
			14 * widthBase, 14 * widthBase, 10 * widthBase, 10 * widthBase, 20 * widthBase, 
			12 * widthBase, 12 * widthBase, 14 * widthBase, 10 * widthBase, 24 * widthBase, 
			18 * widthBase, 14 * widthBase, 14 * widthBase, 12 * widthBase, 12 * widthBase, 
			12 * widthBase, 20 * widthBase, 20 * widthBase, 16 * widthBase
		};
		
		// 生成一个Sheet
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth(10);

		int index = 0;
		// 添加两行表头
		HSSFRow row = sheet.createRow(index);
		String[] headers = { "审批状态", "更新时间", "需求编号", "项目编号", "项目名称", "项目类型", "主辅办", "开发负责人", "当前阶段",
				"当前状态", "风险和问题", "项目成员", "", "", "开发周期", "", "", "", "", "", "",
				"产能数据", "", "", "", "质量数据", "", "", "QA过程审计", "QC过程审计", "",
				"", "项目后评估（组长填报、集中审批）", "", "备注" };
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(styles[0]);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		index++;

		row = sheet.createRow(index);
		String[] headers2 = {"", "", "", "", "", "", "", "", "", "", "", "设计", "开发", "评审",
				"立项日期", "实际上线日期", "计划结项日期", "实际结项日期", "实际天数", "是否超期", "超期原因", "计划工作量", "实际工作量",
				"结项功能点数", "生产率", "冒烟测试不通过次数", "ST测试类型", "ST缺陷密度", "NC",
				"设计审计", "代码审计", "上线审计", "项目经验教训", "P级事件及分析", "" };
		for (short i = 0; i < headers2.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(styles[0]);
			HSSFRichTextString text = new HSSFRichTextString(headers2[i]);
			cell.setCellValue(text);
		}
		
		// 从index行以下开始添加数据
		super.addFromClass(dataset, sheet, index);

		// 合并表头单元格
		for (int i = 0; i < 11; i++) {
			sheet.addMergedRegion(new Region(0, (short) i, 1, (short) i));
		}
		sheet.addMergedRegion(new Region(0, (short) 11, 0, (short) 13));
		sheet.addMergedRegion(new Region(0, (short) 14, 0, (short) 20));
		sheet.addMergedRegion(new Region(0, (short) 21, 0, (short) 24));
		sheet.addMergedRegion(new Region(0, (short) 25, 0, (short) 27));
		sheet.addMergedRegion(new Region(0, (short) 29, 0, (short) 31));
		sheet.addMergedRegion(new Region(0, (short) 32, 0, (short) 33));
		sheet.addMergedRegion(new Region(0, (short) 34, 1, (short) 34));
		
		super.setColWidths(sheet, widths);
	}

	public HSSFCell mergeRegion(HSSFSheet sheet, List<String> list, int rowId,
			int colId) {
		HSSFCell cell = sheet.getRow(rowId).getCell(colId);
		cell.setCellValue(list.get(colId));
		return cell;
	}
	
	public void exportExcel(Collection<DisplayProject> jichuDataset,Collection<DisplayProject> yewuDataset,
							Collection<DisplayProject> yingyongDataset,
							OutputStream out) {
		exportExcel(Constants.jichuSheetName, jichuDataset, out);
		exportExcel(Constants.yewuSheetName, yewuDataset, out);
		exportExcel(Constants.yingyongSheetName, yingyongDataset, out);
		//exportExcel(Constants.otherSheetName, otherDataset);
		
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setCellStyleByCondition(HSSFCell cell, int colId) {
		cell.setCellStyle(styles[12]);
	}
}
