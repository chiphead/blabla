package chiphead.io.export;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import chiphead.utils.DateUtil;

public class ExportExcel<T> {
	protected HSSFWorkbook workbook;
	protected HSSFFont font;
	HSSFPalette customPalette;
	HSSFCellStyle[] styles;

	public ExportExcel() {
		// 声明一个工作薄
		workbook = new HSSFWorkbook();
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11);
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		customPalette = workbook.getCustomPalette();
		customPalette.setColorAtIndex(HSSFColor.LAVENDER.index, (byte) 218,
				(byte) 238, (byte) 243);	// 表头淡蓝色
		customPalette.setColorAtIndex(HSSFColor.AQUA.index, (byte) 218,
				(byte) 238, (byte) 243);	// 需求
		customPalette.setColorAtIndex(HSSFColor.BLUE.index, (byte) 183,
				(byte) 222, (byte) 232);	// 设计
		customPalette.setColorAtIndex(HSSFColor.WHITE.index, (byte) 146,
				(byte) 205, (byte) 220);	// 开发
		customPalette.setColorAtIndex(HSSFColor.BLACK.index, (byte) 253,
				(byte) 233, (byte) 217);	// UT
		customPalette.setColorAtIndex(HSSFColor.BROWN.index, (byte) 252,
				(byte) 213, (byte) 180);	// ST
		customPalette.setColorAtIndex(HSSFColor.CORAL.index, (byte) 250,
				(byte) 191, (byte) 143);	// UAT
		customPalette.setColorAtIndex(HSSFColor.GOLD.index, (byte) 146,
				(byte) 208, (byte) 80);	// 上线
		customPalette.setColorAtIndex(HSSFColor.INDIGO.index, (byte) 255,
				(byte) 192, (byte) 0);		// 超出7的黄色

		HSSFColor colors[] = new HSSFColor[12];
		colors[0] = customPalette.getColor(HSSFColor.LAVENDER.index); // 表头
		colors[1] = customPalette.getColor(HSSFColor.AQUA.index); // 需求
		colors[2] = customPalette.getColor(HSSFColor.BLUE.index); // 设计
		colors[3] = customPalette.getColor(HSSFColor.WHITE.index); // 开发
		colors[4] = customPalette.getColor(HSSFColor.BLACK.index); // UT
		colors[5] = customPalette.getColor(HSSFColor.BROWN.index); // ST
		colors[6] = customPalette.getColor(HSSFColor.CORAL.index); // UAT
		colors[7] = customPalette.getColor(HSSFColor.GOLD.index); // 上线
		colors[8] = customPalette.getColor(HSSFColor.INDIGO.index); // 黄色
		colors[9] = customPalette.getColor(HSSFColor.GREY_50_PERCENT.index);// 灰色
		colors[10] = customPalette.getColor(HSSFColor.GREEN.index); // 绿色
		colors[11] = customPalette.getColor(HSSFColor.RED.index); // 红色

		styles = new HSSFCellStyle[14];
		// 设字体，居中等
		for (int i = 0; i < styles.length; i++) {
			styles[i] = workbook.createCellStyle();
			styles[i].setFont(font);
			// styles[i].setBorderBottom(HSSFCellStyle.BORDER_DOTTED); //下边框
			// styles[i].setBorderLeft(HSSFCellStyle.BORDER_DOTTED);//左边框
			// styles[i].setBorderTop(HSSFCellStyle.BORDER_DOTTED);//上边框
			// styles[i].setBorderRight(HSSFCellStyle.BORDER_DOTTED);//右边框
			styles[i].setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		}

		styles[0].setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 用于换行
		styles[12].setWrapText(true);

		// 设颜色
		for (int i = 0; i < styles.length - 2; i++) {// 0-11都设成对应颜色, 12、13无色
			setForeground(styles[i], colors[i].getIndex());
		}
	}

	protected void addFromClass(Collection<T> dataset, HSSFSheet sheet,
			int index) {
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		HSSFRow row;

		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();

			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(styles[13]);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);

				String textValue = null;
				Object value = null;
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					if (value instanceof Integer) {
						int intValue = (Integer) value;
						cell.setCellValue(intValue);
					} else if (value instanceof BigInteger) {
						BigInteger intValue = (BigInteger) value;
						cell.setCellValue(intValue.doubleValue());
					} else if (value instanceof Date) {
						Date dateValue = (Date) value;
						cell.setCellValue(DateUtil.getDateStr(dateValue));
					} else if (value instanceof Double) {
						double dValue = (Double) value;
						cell.setCellValue(dValue);
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value == null ? "" : value.toString();
						cell.setCellValue(textValue);
					}
					// 设置单元格样式，由子类实现
					setCellStyleByCondition(cell, i);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
	}

	protected void addFromList(Collection<T> dataset, HSSFSheet sheet, int index) {
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		HSSFRow row;

		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			List<String> list = (List<String>) it.next(); // 一行数据
			String cellValue; // 单元格数据
			for (int i = 0; i < list.size(); i++) {
				HSSFCell cell = row.createCell(i);
				cellValue = list.get(i);
				if (cellValue == null) {
					cell.setCellValue("");
					break;
				}

				// 按需要合并单元格，由子类实现
				HSSFCell mergedCell = mergeRegion(sheet, list, index, i);
				// 设置单元格样式，由子类实现
				setCellStyleByCondition(mergedCell, i);
			}
		}
	}

	protected HSSFCell mergeRegion(HSSFSheet sheet, List<String> list,
			int rowId, int colId) {
		return null;
	}

	protected void setCellStyleByCondition(HSSFCell cell, int colId) {

	}

	protected void setColWidths(HSSFSheet sheet, int[] widths) {
		for (int i = 0; i < widths.length; i++) {
			sheet.setColumnWidth(i, widths[i]);
		}
	}

	protected void setForeground(HSSFCellStyle style, short foreground) {
		if (foreground != 0) {
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(foreground);
		}
	}
}
