package cn.com.jeeweb.common.util.poi;

import cn.com.jeeweb.common.util.CollectionUtil;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Word 工具类
 */
public class WordUtil {
	/**
	 * @描述：是否是2003的 word，返回true是2003
	 * @param filePath
	 * @return
	 */
	public static boolean isWord2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(doc)$");
	}

	/**
	 * @描述：是否是2007的 word，返回true是2007
	 * @param filePath
	 * @return
	 */
	public static boolean isWord2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(docx)$");
	}

	/**
	 * 验证是否是 word 文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean validateWord(String filePath) {
		if (filePath == null || !(isWord2003(filePath) || isWord2007(filePath))) {
			return false;
		}
		return true;
	}

	/**
	 * 根据模板生成新word文档
	 * 
	 * @param inputUrl  模板存放地址
	 * @param outputUrl 新文档存放地址
	 * @param textMap   需要替换的信息集合
	 * @return 成功返回true,失败返回false
	 */
	public static boolean replaceWord(String inputUrl, String outputUrl, Map<String, String> textMap) {
		// 模板转换默认成功
		boolean changeFlag = true;
		try {
			// 获取docx解析对象
			XWPFDocument document = replaceWord(inputUrl, textMap);

			// 生成新的word
			File file = new File(outputUrl);
			FileOutputStream stream = new FileOutputStream(file);
			document.write(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
			changeFlag = false;
		}

		return changeFlag;
	}

	public static XWPFDocument replaceWord(String inputUrl, Map<String, String> textMap) {
		try {
			// 获取docx解析对象
			XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));

			// 解析替换文本段落对象
			WordUtil.changeParagraphText(document, textMap);
			// 解析替换表格对象
			WordUtil.changeTableText(document, textMap);

			return document;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 替换段落文本
	 * 
	 * @param document docx 解析对象
	 * @param textMap  需要替换的信息集合
	 */
	public static void changeParagraphText(XWPFDocument document, Map<String, String> textMap) {
		if (textMap == null || textMap.isEmpty()) {
			return;
		}

		// 获取段落集合
		List<XWPFParagraph> paragraphs = document.getParagraphs();

		for (XWPFParagraph paragraph : paragraphs) {
			// 判断此段落时候需要进行替换
			String text = paragraph.getText();
			if (checkText(text)) {
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					// 替换模板原来位置
					run.setText(changeValue(run.toString(), textMap), 0);
				}
			}
		}

	}

	/**
	 * 替换表格文本
	 * 
	 * @param document docx 解析对象
	 * @param textMap  需要替换的信息集合
	 */
	public static void changeTableText(XWPFDocument document, Map<String, String> textMap) {
		if (textMap == null || textMap.isEmpty()) {
			return;
		}

		// 获取表格对象集合
		List<XWPFTable> tables = document.getTables();

		for (int i = 0; i < tables.size(); i++) {
			// 只处理行数大于等于2的表格，且不循环表头
			XWPFTable table = tables.get(i);
			if (table.getRows().size() > 1) {
				// 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
				if (checkText(table.getText())) {
					List<XWPFTableRow> rows = table.getRows();
					// 遍历表格,并替换模板
					eachTable(rows, textMap);
				}
			}
		}
	}

	/**
	 * 替换表格对象方法
	 * 
	 * @param document  docx解析对象
	 * @param textMap   需要替换的信息集合
	 * @param tableList 需要插入的表格信息集合
	 */
	public static void changeTable(XWPFDocument document, Map<String, String> textMap, List<String[]> tableList) {
		if (tableList == null || tableList.isEmpty()) {
			return;
		}
		// 获取表格对象集合
		List<XWPFTable> tables = document.getTables();

		for (int i = 0; i < tables.size(); i++) {
			// 只处理行数大于等于2的表格，且不循环表头
			XWPFTable table = tables.get(i);
			if (table.getRows().size() > 1) {
				// 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
				if (checkText(table.getText())) {
					List<XWPFTableRow> rows = table.getRows();
					// 遍历表格,并替换模板
					eachTable(rows, textMap);
				} else {
//                  System.out.println("插入"+table.getText());
					insertTable(table, tableList);
				}
			}
		}
	}

	/**
	 * 遍历表格
	 * 
	 * @param rows    表格行对象
	 * @param textMap 需要替换的信息集合
	 */
	public static void eachTable(List<XWPFTableRow> rows, Map<String, String> textMap) {
		for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			for (XWPFTableCell cell : cells) {
				// 判断单元格是否需要替换
				if (checkText(cell.getText())) {
					List<XWPFParagraph> paragraphs = cell.getParagraphs();
					for (XWPFParagraph paragraph : paragraphs) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun run : runs) {
							run.setText(changeValue(run.toString(), textMap), 0);
						}
					}
				}
			}
		}
	}

	/**
	 * 为表格插入数据，行数不够添加新行
	 * 
	 * @param table     需要插入数据的表格
	 * @param tableList 插入数据集合
	 */
	public static void insertTable(XWPFTable table, List<String[]> tableList) {
		if (CollectionUtil.isEmpty(tableList)) {
			return;
		}

		// 创建行,根据需要插入的数据添加新行，不处理表头
		for (int i = 1; i < tableList.size(); i++) {
			XWPFTableRow row = table.createRow();
			System.out.println(row);
		}
		// 遍历表格插入数据
		List<XWPFTableRow> rows = table.getRows();
		for (int i = 1; i < rows.size(); i++) {
			XWPFTableRow newRow = table.getRow(i);
			List<XWPFTableCell> cells = newRow.getTableCells();
			for (int j = 0; j < cells.size(); j++) {
				XWPFTableCell cell = cells.get(j);
				cell.setText(tableList.get(i - 1)[j]);
			}
		}

	}

	/**
	 * 判断文本中时候包含$
	 * 
	 * @param text 文本
	 * @return 包含返回true,不包含返回false
	 */
	public static boolean checkText(String text) {
		boolean check = false;
		if (text != null && text.indexOf("$") != -1) {
			check = true;
		}
		return check;

	}

	/**
	 * 匹配传入信息集合与模板
	 * 
	 * @param value   模板需要替换的区域
	 * @param textMap 传入信息集合
	 * @return 模板需要替换区域信息集合对应值
	 */
	public static String changeValue(String value, Map<String, String> textMap) {
		Set<Entry<String, String>> textSets = textMap.entrySet();
		for (Entry<String, String> textSet : textSets) {
			// 匹配模板与替换值 格式${key}
			String key = "${" + textSet.getKey() + "}";
			if (value != null && value.indexOf(key) != -1) {
				value = textSet.getValue();
			}
		}
		// 模板未匹配到区域替换为空
		if (checkText(value)) {
			value = "";
		}
		return value;
	}

	/**
	 * 根据模板生成新word文档 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
	 * 
	 * @param inputUrl  模板存放地址
	 * @param outputUrl 新文档存放地址
	 * @param textMap   需要替换的信息集合
	 * @param tableList 需要插入的表格信息集合
	 * @return 成功返回true,失败返回false
	 */
	@Deprecated
	public static boolean changWord(String inputUrl, String outputUrl, Map<String, String> textMap,
			List<String[]> tableList) {

		// 模板转换默认成功
		boolean changeFlag = true;
		try {
			// 获取docx解析对象
			XWPFDocument document = changWord(inputUrl, textMap, tableList);

			// 生成新的word
			File file = new File(outputUrl);
			FileOutputStream stream = new FileOutputStream(file);
			document.write(stream);
			stream.close();

		} catch (IOException e) {
			e.printStackTrace();
			changeFlag = false;
		}

		return changeFlag;

	}

	@Deprecated
	public static XWPFDocument changWord(String inputUrl, Map<String, String> textMap, List<String[]> tableList) {
		try {
			// 获取docx解析对象
			XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));

			// 解析替换文本段落对象
			WordUtil.changeParagraphText(document, textMap);
			// 解析替换表格对象
			WordUtil.changeTable(document, textMap, tableList);

			return document;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	@Deprecated
	public static XWPFDocument changWord(String inputUrl, Map<String, String> textMap) {
		return changWord(inputUrl, textMap, null);
	}

	public static void main(String[] args) {
		System.out.println(validateWord("aa.doca"));

//		// 模板文件地址
//		String inputUrl = "E:\\Temp\\jc.docx";
//		// 新生产的模板文件
//		String outputUrl = "E:\\Temp\\jcnew.docx";
//
//		Map<String, String> testMap = new HashMap<String, String>();
//		testMap.put("projectName", "测试项目");
//		testMap.put("projectNo", "A001");
//
//		List<String[]> testList = new ArrayList<String[]>();
//		testList.add(new String[] { "1", "1AA", "1BB", "1CC" });
//		testList.add(new String[] { "2", "2AA", "2BB", "2CC" });
//		testList.add(new String[] { "3", "3AA", "3BB", "3CC" });
//		testList.add(new String[] { "4", "4AA", "4BB", "4CC" });
//
//		WordUtil.changWord(inputUrl, outputUrl, testMap, testList);
	}
}
